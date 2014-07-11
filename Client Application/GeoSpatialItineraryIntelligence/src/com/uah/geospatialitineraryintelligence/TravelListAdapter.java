package com.uah.geospatialitineraryintelligence;
/**
 * @author Subodh
 * @Description  Custom ArrayAdapter class extending ArrayAdapter class for dynamically populating ListView items based on array of GIISegments
 *
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.uah.geospatialitineraryintelligence.communication.HttpCommunicator;
import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.communication.HttpUrlManager;
import com.uah.geospatialitineraryintelligence.datamodel.GIIReview;
import com.uah.geospatialitineraryintelligence.datamodel.GIISegment;
import com.uah.geospatialitineraryintelligence.util.GIIJSONReader;
import com.uah.geospatialitineraryintelligence.util.GIIJSONWriter;

public class TravelListAdapter extends ArrayAdapter<GIISegment> {
	private final Context context;
	private final GIISegment[] values;

	TextView sectorName;
	TextView segmentName;
	EditText startDate;
	EditText distance;
	EditText endDate;
	EditText cost;
	RatingBar ratingBar;
	EditText review;
	Button editBtn;
	Button doneBtn;
	String ItineraryCities;

	public TravelListAdapter(Context context, int layoutid, int listid, GIISegment[] values) {

		super(context, layoutid, listid, values);
		this.context = context;
		this.values = values;
		ItineraryCities = "";

	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.travel_listview_layout, parent, false);
		segmentName = (TextView) rowView.findViewById(R.id.segmentName);
		sectorName = (TextView) ((TravelActivity) context).findViewById(R.id.sector_Name);

		startDate = (EditText) rowView.findViewById(R.id.travelDateEditText);
		distance = (EditText) rowView.findViewById(R.id.distanceEditText);
		endDate = (EditText) rowView.findViewById(R.id.endDateEditText);
		cost = (EditText) rowView.findViewById(R.id.costEditText);
		ratingBar = (RatingBar) rowView.findViewById(R.id.ratingBar);
		review = (EditText) rowView.findViewById(R.id.ReviewEditText);
		editBtn = (Button) rowView.findViewById(R.id.edit);
		// doneBtn = (Button) rowView.findViewById(R.id.done);
		
		
		HttpSessionClass.currentSector.SectorId = values[position].SectorId; // Set sectorId to the current sector using the value from segment

		String OriginCityValue = HttpSessionClass.SessionCities.get(values[position].OriginCityId);
		String DestinationCityValue = HttpSessionClass.SessionCities.get(values[position].DestinationCityId);

		segmentName.setText(OriginCityValue + "-->" + DestinationCityValue);

		// String ItineraryCities = ((TravelActivity) context).ItineraryCities;
		ItineraryCities += (ItineraryCities.isEmpty() ? (HttpSessionClass.SessionCities
				.get(values[position].OriginCityId)) : "");//("-->" + HttpSessionClass.SessionCities.get(values[position].OriginCityId)));

		if (!ItineraryCities.contains(DestinationCityValue)) {
			ItineraryCities += "\n-->" + HttpSessionClass.SessionCities.get(values[position].DestinationCityId);
		}

		sectorName.setText(ItineraryCities);
		distance.setText("" + values[position].Distance);
		startDate.setText("" + values[position].StartDate);
		endDate.setText("" + values[position].EndDate);
		cost.setText("$ " + values[position].Cost);

		/**
		 * Handling Review Section
		 */
		GIIReview reviewObj = new GIIReview();
		reviewObj.segmentId = values[position].SegmentId;
		// reviewObj.Comment = review.getText().toString();
		// reviewObj.Rating = ratingBar.getRating();
		// reviewObj.PlaceId = 0;
		// reviewObj.SectorId = 0;
		reviewObj.userId = Integer.parseInt(HttpSessionClass.SessionId);

		// url for getting review is like:
		// url/review/userid|sectorId|segmentId|placeId
		String Url = HttpUrlManager.GetReviewUrl("segmentId", reviewObj.segmentId);
		String responseText = new HttpCommunicator().GIIHttpGet(Url);
		// String responseText = new HttpCommunicator().GIIHttpPost(
		// HttpUrlManager.GetReviewUrl("segmentId", reviewObj.SegmentId),
		// new GIIJSONWriter().CreateReviewPayload(reviewObj));

		GIIReview reviewRespose = new GIIJSONReader().ParseReview(responseText);
		/**
		 * End of Review Section Handling
		 */
		review.setText(reviewRespose.comment);
		ratingBar.setRating(Float.parseFloat(reviewRespose.rating + ""));

		editBtn.setOnClickListener(new View.OnClickListener() // thisworks
		{
			@Override
			public void onClick(View v) {
				GIISegment currentSegment = (GIISegment) values[position];
				HttpSessionClass.setCurrentSegment(currentSegment);
				((TravelActivity) context).LaunchEditTravel(currentSegment);

				// Intent intent = new
				// Intent(parent.getContext(),EditTravelActivity.class);
				// parent.getContext().startActivity(intent);
				// Activity parentActivity = (Activity) parent.getContext();
				// parentActivity.startActivityForResult(intent,parentActivity.RESULT_OK);

				// ((PlanActivity) context).AddSegment(values[position]);
			}

		});
		/*
		 * doneBtn.setOnClickListener(new View.OnClickListener() //thisworks {
		 * 
		 * @Override public void onClick(View v) { //((PlanActivity)
		 * context).AddSegment(values[position]); }
		 * 
		 * });
		 */

		return rowView;
	}
}
