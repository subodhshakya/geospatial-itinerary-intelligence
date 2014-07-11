package com.uah.geospatialitineraryintelligence;
/**
 * @author Binaya Raj
 * @Description  Custom ArrayAdapter class extending ArrayAdapter class for dynamically populating ListView items based on array of Places returned 
 * by Google places
 *
 */
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uah.geospatialitineraryintelligence.datamodel.GIIPlace;

public class PlaceListAdapter extends ArrayAdapter<GIIPlace> {
	private final Context context;
	private final GIIPlace[] values;

	public PlaceListAdapter(Context context, int layoutid, int listid, GIIPlace[] values) {

		super(context, layoutid, listid, values);
		this.context = context;
		this.values = values;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.place_listview_layout, parent, false);
		TextView Name = (TextView) rowView.findViewById(R.id.placeName);
		TextView Vicinity = (TextView) rowView.findViewById(R.id.vicinity);
		TextView Rating = (TextView) rowView.findViewById(R.id.placeRating);
		RatingBar placeRatingBar = (RatingBar) rowView.findViewById(R.id.placeRatingBar);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		GIIPlace place = values[position];
		// String OriginCity =
		// HttpSessionClass.SessionCities.get(values[position].OriginCityId);
		// String DestinationCity =
		// HttpSessionClass.SessionCities.get(values[position].DestinationCityId);

		// values[position].DestinationCityId);
		Name.setText(values[position].name);
		Vicinity.setText("Location: " + values[position].vicinity);
		Rating.setText("Rating: " + "" + values[position].rating);
		placeRatingBar.setRating((float) values[position].rating);
		// imageView.setImageDrawable(Converter.LoadImageFromWebOperations(values[position].iconSrc));
		imageView.setImageResource(R.drawable.lodging);

		/**
		 * This Code sets the image from drawable folder with dynamic value of the image (here type)
		 */
		String type = PlaceSearchActivity.SearchType;
		int drawableId = context.getResources().getIdentifier(type, "drawable", context.getPackageName());
		imageView.setImageResource(drawableId);
		
		/**
		 * End of the above part
		 */
		

		if (position > 2) {
			RelativeLayout rLayout = (RelativeLayout) rowView.findViewById(R.id.placeListView);
			Resources res = rowView.getResources(); // resource handle
			Drawable drawable = res.getDrawable(R.drawable.huntsville); // new
																		// Image
																		// that
																		// was
																		// added
																		// to
																		// the
																		// res
																		// folder
		}

		return rowView;
	}
}
