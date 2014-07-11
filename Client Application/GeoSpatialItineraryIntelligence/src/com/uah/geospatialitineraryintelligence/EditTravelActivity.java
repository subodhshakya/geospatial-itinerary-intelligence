package com.uah.geospatialitineraryintelligence;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uah.geospatialitineraryintelligence.communication.HttpCommunicator;
import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.communication.HttpUrlManager;
import com.uah.geospatialitineraryintelligence.datamodel.GIILocationPlace;
import com.uah.geospatialitineraryintelligence.datamodel.GIIReview;
import com.uah.geospatialitineraryintelligence.datamodel.GIISector;
import com.uah.geospatialitineraryintelligence.datamodel.GIISegment;
import com.uah.geospatialitineraryintelligence.util.GIIJSONReader;
import com.uah.geospatialitineraryintelligence.util.GIIJSONWriter;

public class EditTravelActivity extends Activity {

	TextView segmentName;
	EditText startDate;
	EditText distance;
	EditText endDate;
	EditText cost;
	RatingBar ratingBar;
	EditText review;
	Button submitBtn;
	Button canceBtn;
	CheckBox completeSector;
	GIISegment segment;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_travel);
		//
		segment = HttpSessionClass.getCurrentSegment(); 
		segmentName = (TextView) findViewById(R.id.segmentName);

		startDate = (EditText) findViewById(R.id.travelDateEditText);
		distance = (EditText) findViewById(R.id.distanceEditText);
		endDate = (EditText) findViewById(R.id.endDateEditText);
		cost = (EditText) findViewById(R.id.costEditText);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		review = (EditText) findViewById(R.id.ReviewEditText);
		submitBtn = (Button) findViewById(R.id.submit);
		canceBtn = (Button) findViewById(R.id.cancel);
		completeSector = (CheckBox) findViewById(R.id.CompleteCheckBox);

		String OriginCityValue = HttpSessionClass.SessionCities.get(segment.OriginCityId);
		String DestinationCityValue = HttpSessionClass.SessionCities.get(segment.DestinationCityId);

		segmentName.setText(OriginCityValue + "-->" + DestinationCityValue);

		/**
		 * Handling Review Section
		 */
		String Url = HttpUrlManager.GetReviewUrl("segmentId", segment.SegmentId);
		String responseText = new HttpCommunicator().GIIHttpGet(Url);

		GIIReview reviewRespose = new GIIJSONReader().ParseReview(responseText);
		/**
		 * End of Review Section Handling
		 */
		review.setText(reviewRespose.comment);
		ratingBar.setRating(Float.parseFloat(reviewRespose.rating + ""));

		distance.setText("" + segment.Distance);
		startDate.setText("" + segment.StartDate);
		endDate.setText("" + segment.EndDate);
		cost.setText("$ " + segment.Cost);
		review.setText(reviewRespose.comment);
		ratingBar.setRating(Float.parseFloat(reviewRespose.rating + ""));

		Button btn_CheckInButton = (Button) findViewById(R.id.CheckInButton);

		btn_CheckInButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckInLocation();
			}
		});
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Submit();
				finish(); 
			}
		});
		canceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});

	}

	public void CheckInLocation() {
		double latitude = new PlaceSearchActivity().Latitude;
		double longitude = new PlaceSearchActivity().Longitude;
		GIILocationPlace placeObj = new GIILocationPlace();
		placeObj.Latitude = latitude;
		placeObj.Longitude = longitude;
		placeObj.CityId = segment.OriginCityId;
		placeObj.Name = HttpSessionClass.SessionCities.get(segment.OriginCityId);

		String responseText = new HttpCommunicator().GIIHttpPost(HttpUrlManager.GetUrl("place"),
				new GIIJSONWriter().CreatePlacePayload(placeObj));
		GIILocationPlace placeResponse = new GIIJSONReader().ParseLocationPlace(responseText);
		if (placeResponse.message.equalsIgnoreCase("success")) {

			Toast.makeText(this, "Current Place has been Checked-in", Toast.LENGTH_SHORT).show();
		}
	}

	public void Submit() {
		segment.Distance = Double.parseDouble(distance.getText().toString().trim());

		segment.Cost = Double.parseDouble(cost.getText().toString().replace("$", ""));
		segment.EndDate = endDate.getText().toString();
		segment.StartDate = startDate.getText().toString();

		String responseText = new HttpCommunicator().GIIHttpPost(HttpUrlManager.GetUrl("travel"),
				new GIIJSONWriter().CreateSegmentPayload(segment));
		GIISector giiSector = new GIIJSONReader().ParseSingleSector(responseText);

		/**
		 * To update Sector as Complete, sector with Completed value '1' is
		 * posted to the server.
		 */
		if (completeSector.isChecked()) {
			giiSector.completed = 1;
			giiSector.SectorId = segment.SectorId;
			responseText = new HttpCommunicator().GIIHttpPost(HttpUrlManager.GetUrl("sector"),
					new GIIJSONWriter().CreateSectorPayload(giiSector));
			giiSector = new GIIJSONReader().ParseSingleSector(responseText);

		}
		HttpSessionClass.setCurrentSector(giiSector);

		/**
		 * Section for sending Review of the segment to the server
		 */
		GIIReview reviewObj = new GIIReview();
		reviewObj.segmentId = segment.SegmentId;
		reviewObj.comment = review.getText().toString();
		reviewObj.rating = ratingBar.getRating();
		reviewObj.placeId = 0;
		reviewObj.sectorId = 0;
		reviewObj.userId = Integer.parseInt(HttpSessionClass.SessionId);
		reviewObj.message = "";

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		// // get current date time with Date()
		Date date = new Date();
		reviewObj.reviewDate = dateFormat.format(date);

		responseText = new HttpCommunicator().GIIHttpPost(HttpUrlManager.GetUrl("review"),
				new GIIJSONWriter().CreateReviewPayload(reviewObj));
		// .
		GIIReview reviewRespose = new GIIJSONReader().ParseReview(responseText);
		if (reviewRespose.message.equalsIgnoreCase("success")) {

			Toast.makeText(this, "Current Itinerary Segment Updated!", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.travel, menu);
		return true;
	}
	
}
