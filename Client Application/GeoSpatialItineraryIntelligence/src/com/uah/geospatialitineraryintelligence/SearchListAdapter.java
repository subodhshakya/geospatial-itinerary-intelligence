package com.uah.geospatialitineraryintelligence;
/**
 * @author Binaya Raj
 * @Description  Custom ArrayAdapter class extending ArrayAdapter class for dynamically populating ListView items based on array of GIISegments
 *
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.uah.geospatialitineraryintelligence.communication.HttpCommunicator;
import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.communication.HttpUrlManager;
import com.uah.geospatialitineraryintelligence.datamodel.GIIReview;
import com.uah.geospatialitineraryintelligence.datamodel.GIISegment;
import com.uah.geospatialitineraryintelligence.util.GIIJSONReader;

public class SearchListAdapter extends ArrayAdapter<GIISegment> {
	private final Context context;
	private final GIISegment[] values;

	public SearchListAdapter(Context context, int layoutid, int listid, GIISegment[] values) {

		super(context, layoutid, listid, values);
		this.context = context;
		this.values = values;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.search_listview_layout, parent, false);
		TextView segmentName = (TextView) rowView.findViewById(R.id.segmentName);

		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		TextView originCity = (TextView) rowView.findViewById(R.id.originCity);
		TextView destinationCity = (TextView) rowView.findViewById(R.id.destinationCity);
		TextView startDate = (TextView) rowView.findViewById(R.id.startDate);
		TextView endDate = (TextView) rowView.findViewById(R.id.endDate);
		TextView distance = (TextView) rowView.findViewById(R.id.distance);
		TextView cost = (TextView) rowView.findViewById(R.id.cost);
		TextView review = (TextView) rowView.findViewById(R.id.review);
		Button addSegment = (Button) rowView.findViewById(R.id.AddSegment);
		RatingBar ratingBar = (RatingBar) rowView.findViewById(R.id.rating);

		String OriginCityValue = HttpSessionClass.SessionCities.get(values[position].OriginCityId);
		String DestinationCityValue = HttpSessionClass.SessionCities.get(values[position].DestinationCityId);
		if (OriginCityValue != null) {

			// itinerayName.setText(values[position].OriginCityId + "->" +
			// values[position].DestinationCityId);
			segmentName.setText(OriginCityValue + "-->" + DestinationCityValue);
			originCity.setText("Origin City: " + OriginCityValue);
			destinationCity.setText("Destination City: " + DestinationCityValue);
			startDate.setText("Start Date: " + values[position].StartDate);
			endDate.setText("End Date: " + values[position].EndDate);
			cost.setText("Total Cost: " + "$ " + values[position].Cost);
			distance.setText("Total Distance: " + values[position].Distance);
			imageView.setImageResource(R.drawable.success);

			/**
			 * Handling Review Section
			 */
			GIIReview reviewObj = new GIIReview();
			reviewObj.segmentId = values[position].SegmentId;
			reviewObj.userId = Integer.parseInt(HttpSessionClass.SessionId);

			// url for getting review is like:
			// url/review/userid|sectorId|segmentId|placeId
			String Url = HttpUrlManager.GetReviewUrl("segmentId", reviewObj.segmentId);
			String responseText = new HttpCommunicator().GIIHttpGet(Url);

			GIIReview reviewRespose = new GIIJSONReader().ParseReview(responseText);
			/**
			 * End of Review Section Handling
			 */
			review.setText(reviewRespose.comment);
			ratingBar.setRating(Float.parseFloat(reviewRespose.rating + ""));

			addSegment.setOnClickListener(new View.OnClickListener() 
					{
						@Override
						public void onClick(View v) {
							((PlanActivity) context).AddSegment(values[position]);
						}

					});
		

		return rowView;
		}
		else
			return null;
		
	}
}
