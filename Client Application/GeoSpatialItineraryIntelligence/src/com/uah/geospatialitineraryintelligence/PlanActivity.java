package com.uah.geospatialitineraryintelligence;
/**
 * @author Binaya Raj
 * @Description  Plan Screen for planning Itineray sector
 *
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uah.geospatialitineraryintelligence.communication.HttpCommunicator;
import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.communication.HttpUrlManager;
import com.uah.geospatialitineraryintelligence.datamodel.GIISearch;
import com.uah.geospatialitineraryintelligence.datamodel.GIISector;
import com.uah.geospatialitineraryintelligence.datamodel.GIISegment;
import com.uah.geospatialitineraryintelligence.util.AlertDialogManager;
import com.uah.geospatialitineraryintelligence.util.GIIJSONReader;
import com.uah.geospatialitineraryintelligence.util.GIIJSONWriter;

public class PlanActivity extends Activity {

	static GIISector newSector = new GIISector();
	public static boolean IsSearchComplete = false;

	EditText startCity;
	EditText endCity;
	EditText travelDateMonth;
	EditText travelDateDay;
	EditText travelDateYear;
	TextView sectorPlan;

	String currentPlan = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan);

		IsSearchComplete = false;

		Button btnOpenPopup = (Button) findViewById(R.id.searchButton);
		Button btnCoTraveller = (Button) findViewById(R.id.coTravelerButton);
		startCity = (EditText) findViewById(R.id.startCityText);
		endCity = (EditText) findViewById(R.id.endCityText);
		travelDateMonth = (EditText) findViewById(R.id.travelDateMM);
		travelDateDay = (EditText) findViewById(R.id.travelDateDD);
		travelDateYear = (EditText) findViewById(R.id.travelDateYYYY);
		sectorPlan = (TextView) findViewById(R.id.sectorPlan);

		btnOpenPopup.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PlanActivity.this, SearchActivity.class);
				startActivity(intent);
			}
		});

		btnCoTraveller.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PlanActivity.this, CoTravelerActivity.class);
				startActivity(intent);
			}
		});		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plan, menu);
		return true;
	}

	@Override
	public void onResume() { // After a pause OR at startup
		super.onResume();
		// Refresh your stuff here
		LoadSearchResults();
	}

	public void btn_Click_AddSegment(View v) {

		String startDateValue = null;
		String startCityName = startCity.getText().toString();
		String endCityName = endCity.getText().toString();
		GIISegment newSegment = new GIISegment();
		if (newSector.GIISegmentList == null)
			newSector.GIISegmentList = new ArrayList<GIISegment>();
		newSegment.OriginCityId = HttpSessionClass.GetKeyFromValue(HttpSessionClass.SessionCities, startCityName);
		newSegment.DestinationCityId = HttpSessionClass.GetKeyFromValue(HttpSessionClass.SessionCities, endCityName);

		if (newSegment.OriginCityId == 0 || newSegment.DestinationCityId == 0) {
			AlertDialogManager alert = new AlertDialogManager();
			alert.showAlertDialog(PlanActivity.this, "Invalid City!!",
					"City you entered is not valid. Please check!", false);
		}
		else {


			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			// get current date time with Date()
			Date date = new Date();
			startDateValue = (travelDateMonth.getText().toString() + "-" + travelDateDay.getText().toString() + "-" + travelDateYear
					.getText().toString());
			newSegment.StartDate = startDateValue;

			AddSegment(newSegment);
		}

	}

	/**
	 * Adds the current GIIsegment to the on-preparing Sector
	 * @param newSegment
	 */
	public void AddSegment(GIISegment newSegment) {
		
		if (newSector.GIISegmentList == null)
			newSector.GIISegmentList = new ArrayList<GIISegment>();

		newSegment.SegmentId = (newSector.GIISegmentList.size() == 0 ? 1 : newSector.GIISegmentList.size() + 1);
		newSegment.NextSegmentId = (newSector.GIISegmentList.size() == 0 ? 2 : newSector.GIISegmentList.size() + 2);
		
		newSector.OriginCityId = newSector.GIISegmentList.size() == 0 ? newSegment.OriginCityId
				: newSector.OriginCityId;

		currentPlan += (currentPlan.isEmpty() ? HttpSessionClass.SessionCities.get(newSegment.OriginCityId) + "-->"
				+ HttpSessionClass.SessionCities.get(newSegment.DestinationCityId) : "-->"
				+ HttpSessionClass.SessionCities.get(newSegment.DestinationCityId));

		sectorPlan.setText(currentPlan);
		startCity.setText(HttpSessionClass.SessionCities.get(newSegment.DestinationCityId));
		endCity.setText("");
		newSector.GIISegmentList.add(newSegment);

	}

	public void LoadSearchResults() {
		TextView result = (TextView) findViewById(R.id.result);
		if (IsSearchComplete) {
			result.setText("Search Results ->>");
			ListView listView = (ListView) findViewById(R.id.segments_listview);
			SearchListAdapter adapter = new SearchListAdapter(this, R.layout.search_listview_layout,
					R.id.segments_listview, HttpSessionClass.searchSegmentArray);
			listView.setAdapter(adapter);
		}
		else {
			
			result.setText("Sorry!! No Results Found!!");
		}
	}

	/**
	 * Obsolete method for search sector. Now handled by SearchActivity
	 * 
	 * @param v
	 */
	public void SearchSector(View v) {

		EditText searchOriginCity = (EditText) v.findViewById(R.id.originCity);
		EditText searchDestinationCity = (EditText) v.findViewById(R.id.destinationCity);
		EditText searchCost = (EditText) v.findViewById(R.id.cost);
		EditText searchDistance = (EditText) v.findViewById(R.id.distance);
		RatingBar popularity = (RatingBar) v.findViewById(R.id.ratingBar);

		ListView listView = (ListView) findViewById(R.id.segments_listview);

		GIISearch searchObj = new GIISearch();
		searchObj.OriginCityId = (searchOriginCity.getText().toString().isEmpty() ? -1 : HttpSessionClass
				.GetKeyFromValue(HttpSessionClass.SessionCities, searchOriginCity.getText().toString().trim()));
		searchObj.DestinationCityId = (searchDestinationCity.getText().toString().isEmpty() ? -1 : HttpSessionClass
				.GetKeyFromValue(HttpSessionClass.SessionCities, searchDestinationCity.getText().toString().trim()));
		searchObj.Cost = (searchCost.getText().toString().isEmpty() ? 0 : Double.parseDouble(searchCost.getText()
				.toString().trim()));
		searchObj.Distance = (searchDistance.getText().toString().isEmpty() ? 0 : Double.parseDouble(searchDistance
				.getText().toString().trim()));
		searchObj.Rating = popularity.getRating();

		// Send the searchObj to the server and get segmentlist in return
		String Url = HttpUrlManager.GetUrl("search"); // Gets URL for Login
		String Payload = new GIIJSONWriter().CreateSearchPayload(searchObj);
		String responseText = new HttpCommunicator().GIIHttpPost(Url + "/" + HttpSessionClass.SessionId, Payload);

		ArrayList<GIISegment> segmentList = new GIIJSONReader().ParseSegments(responseText);

		GIISegment[] segmentArray = new GIISegment[segmentList.size()];
		int segmentCount = 0;
		for (GIISegment segment : segmentList) {
			segmentArray[segmentCount] = segment;
			segmentCount++;
		}

		SearchListAdapter adapter = new SearchListAdapter(this, R.layout.sector_listview_layout,
				R.id.segments_listview, segmentArray);
		listView.setAdapter(adapter);

	}

	public void btn_Click_Submit(View v) {
		newSector.DestinationCityId = HttpSessionClass.GetKeyFromValue(HttpSessionClass.SessionCities, startCity
				.getText().toString());
		newSector.UserId = Integer.parseInt(HttpSessionClass.SessionId);
		newSector.NoOfSegments = (newSector.GIISegmentList != null ? newSector.GIISegmentList.size() : 0);
		if (newSector.NoOfSegments != 0) {
			if (newSector.NoOfSegments == 1) {
				GIISegment segment = null;
				for (GIISegment seg : newSector.GIISegmentList) {
					seg.NextSegmentId = 1000;
					segment = seg;
				}
				newSector.GIISegmentList = new ArrayList<GIISegment>();
				newSector.GIISegmentList.add(segment);

			}
			newSector.completed = 0;

			String Url = HttpUrlManager.GetUrl("sector");
			String Payload = new GIIJSONWriter().CreateSectorPayload(newSector);
			String response = new HttpCommunicator().GIIHttpPost(Url + "/" + HttpSessionClass.SessionId, Payload);
			// newSector = new GIISector();
			newSector.GIISegmentList = null;
			newSector.GIISegmentList = new ArrayList<GIISegment>();

			Intent homeIntent = new Intent(PlanActivity.this, HomeActivity.class);
			startActivity(homeIntent);

		}
	}

}
