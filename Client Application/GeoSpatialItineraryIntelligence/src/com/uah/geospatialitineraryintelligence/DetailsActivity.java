package com.uah.geospatialitineraryintelligence;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.uah.geospatialitineraryintelligence.communication.HttpCommunicator;
import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.communication.HttpUrlManager;
import com.uah.geospatialitineraryintelligence.datamodel.GIIReview;
import com.uah.geospatialitineraryintelligence.datamodel.GIISector;
import com.uah.geospatialitineraryintelligence.datamodel.GIISegment;
import com.uah.geospatialitineraryintelligence.util.GIIJSONReader;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);		
		
		GIISector sector = HttpSessionClass.getCurrentSector();
		ListView listView = (ListView) findViewById(R.id.segments_listview);
		
		TextView OriginCity = (TextView) findViewById(R.id.originCityVal);
		TextView DestinationCity = (TextView) findViewById(R.id.destinationCityVal);
		TextView PlannedDate = (TextView) findViewById(R.id.plannedDateVal);
		TextView Cost = (TextView) findViewById(R.id.costVal);
		TextView Distance = (TextView) findViewById(R.id.distanceVal);
		TextView Review = (TextView) findViewById(R.id.review);
		RatingBar rating = (RatingBar) findViewById(R.id.ratingBar);
		
		OriginCity.setText("" + HttpSessionClass.SessionCities.get(sector.OriginCityId));
		DestinationCity.setText(" " + HttpSessionClass.SessionCities.get(sector.DestinationCityId));
		PlannedDate.setText(" " + sector.PlanDate);
		Cost.setText("" +sector.Cost); 
		Distance.setText(""+sector.Distance);
		//Review.setText("Review:");		
		
		
		/**
		 * Handling Review Section
		 */
		GIIReview reviewObj = new GIIReview();
		reviewObj.sectorId = sector.SectorId;
		
		reviewObj.userId = Integer.parseInt(HttpSessionClass.SessionId);

		// url for getting review is like:
		// url/review/userid|sectorId|segmentId|placeId
		String Url = HttpUrlManager.GetReviewUrl("sectorId", reviewObj.sectorId);
		String responseText = new HttpCommunicator().GIIHttpGet(Url);
		// String responseText = new HttpCommunicator().GIIHttpPost(
		// HttpUrlManager.GetReviewUrl("segmentId", reviewObj.SegmentId),
		// new GIIJSONWriter().CreateReviewPayload(reviewObj));

		GIIReview reviewRespose = new GIIJSONReader().ParseReview(responseText);
		Review.setText("Review: " + reviewRespose.comment);
		rating.setRating(Float.parseFloat(reviewRespose.rating +""));
		/**
		 * End of Review Section Handling
		 */
		
		
		ArrayList<GIISegment> segmentList = sector.GIISegmentList;
		
		GIISegment[] segmentArray = new GIISegment[segmentList.size()];
		int segmentCount = 0;
		for (GIISegment segment : segmentList) {
			segmentArray[segmentCount] = segment;
			segmentCount++;
		}

		SectorDetailListAdapter adapter = new SectorDetailListAdapter(this, R.layout.sector_listview_layout,
				R.id.segments_listview, segmentArray);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}
	
	public void setValuesToWidgets()
	{
		
	}

}
