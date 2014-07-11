package com.uah.geospatialitineraryintelligence;
/**
 * @author Binaya Raj
 * @Description  Custom ArrayAdapter class extending ArrayAdapter class for dynamically populating ListView items based on array of GIISectors
 *
 */
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.datamodel.GIISector;
import com.uah.geospatialitineraryintelligence.datamodel.GIISegment;

public class ItineraryListAdapter extends ArrayAdapter<GIISector> {
	private final Context context;
	private final GIISector[] values;

	public ItineraryListAdapter(Context context, int layoutid, int listid, GIISector[] values) {

		super(context, layoutid, listid, values);
		this.context = context;
		this.values = values;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.sector_listview_layout, parent, false);
		TextView itinerayName = (TextView) rowView.findViewById(R.id.itineraryName);
		TextView detail1 = (TextView) rowView.findViewById(R.id.detail1);
		TextView detail2 = (TextView) rowView.findViewById(R.id.detail2);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		GIISector sector = values[position];
		String OriginCity = HttpSessionClass.SessionCities.get(values[position].OriginCityId);
		String DestinationCity = HttpSessionClass.SessionCities.get(values[position].DestinationCityId);

		String ItineraryCities = "";
		ArrayList<GIISegment> segmentList = values[position].GIISegmentList;
		GIISegment lastSegment = null;

		for (GIISegment segment : segmentList) {
			ItineraryCities += (ItineraryCities.isEmpty() ? (HttpSessionClass.SessionCities.get(segment.OriginCityId))
					: (" -->" + HttpSessionClass.SessionCities.get(segment.OriginCityId)));
			lastSegment = segment;
		}
		if (lastSegment != null) {
			sector.SectorId = lastSegment.SectorId;
			DestinationCity = HttpSessionClass.SessionCities.get(lastSegment.DestinationCityId);
			ItineraryCities += " -->" + HttpSessionClass.SessionCities.get(lastSegment.DestinationCityId);

			itinerayName.setText(ItineraryCities);
			detail1.setText("Planned Date: " + values[position].PlanDate);
			detail2.setText("Cost: " + "$" + values[position].Cost);

			int drawableId = 0;
			if (HomeActivity.IsCompleteItinerary == 1) {
				drawableId = context.getResources().getIdentifier("success", "drawable", context.getPackageName());

			}
			else
				drawableId = context.getResources().getIdentifier("newsector", "drawable", context.getPackageName());

			imageView.setImageResource(drawableId);

			/*
			 * if (position > 2) { RelativeLayout rLayout = (RelativeLayout)
			 * rowView.findViewById(R.id.sectorList); Resources res =
			 * rowView.getResources(); // resource handle Drawable drawable =
			 * res.getDrawable(R.drawable.huntsville); // new // Image // that
			 * // was // added // to // the // res // folder
			 * rLayout.setBackgroundDrawable(drawable); }
			 */
			
			RelativeLayout rLayout = (RelativeLayout) rowView.findViewById(R.id.sectorList);
			Resources res = rowView.getResources(); // resource handle
			Drawable drawable = res.getDrawable(R.drawable.birmingham);
			
			
			if (OriginCity.equalsIgnoreCase("huntsville")) {
				drawable = res.getDrawable(R.drawable.huntsville);
			}
			else if (OriginCity.equalsIgnoreCase("arab")) {
				drawable = res.getDrawable(R.drawable.arab);
			}
			else if (OriginCity.equalsIgnoreCase("athens")) {
				drawable = res.getDrawable(R.drawable.athens);
			}
			else if (OriginCity.equalsIgnoreCase("birmingham")) {
				drawable = res.getDrawable(R.drawable.birmingham);
			}
			else if (OriginCity.equalsIgnoreCase("tuscaloosa")) {
				drawable = res.getDrawable(R.drawable.tuscaloosa);
			}
			rLayout.setBackgroundDrawable(drawable);
		}

		return rowView;

	}
}
