package com.uah.geospatialitineraryintelligence;

/**
 * @author Archana
 * @Description  Activity for search interface
 *
 */
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.uah.geospatialitineraryintelligence.communication.HttpCommunicator;
import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.communication.HttpUrlManager;
import com.uah.geospatialitineraryintelligence.datamodel.GIISearch;
import com.uah.geospatialitineraryintelligence.datamodel.GIISegment;
import com.uah.geospatialitineraryintelligence.util.GIIJSONReader;
import com.uah.geospatialitineraryintelligence.util.GIIJSONWriter;

public class SearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		Button btnSearch = (Button) findViewById(R.id.btnSearch);

		btnSearch.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				SearchSector();
				finish();

			}
		});

		Button btnDismiss = (Button) findViewById(R.id.dismiss);
		btnDismiss.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plan, menu);
		return true;
	}

	/**
	 * Function for getting the search parameters to search segments from the database.
	 */
	public void SearchSector() {

		EditText searchOriginCity = (EditText) findViewById(R.id.originCity);
		EditText searchDestinationCity = (EditText) findViewById(R.id.destinationCity);
		EditText searchCost = (EditText) findViewById(R.id.cost);
		EditText searchDistance = (EditText) findViewById(R.id.distance);
		RatingBar popularity = (RatingBar) findViewById(R.id.ratingBar);

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
			PlanActivity.IsSearchComplete = (segment.OriginCityId == 0) ? false : true;
			segmentCount++;
		}
		if (segmentArray.length >= 0)
			HttpSessionClass.searchSegmentArray = segmentArray;

		finish(); // To close the activity

	}

}
