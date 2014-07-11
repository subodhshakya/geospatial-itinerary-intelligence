package com.uah.geospatialitineraryintelligence.util;
/**
 * @author Archana
 * @Description  Class that helps in managing Item selection in spinner
 *
 */

import com.uah.geospatialitineraryintelligence.PlaceSearchActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		
		PlaceSearchActivity.SearchType = parent.getItemAtPosition(pos).toString();
		
		Toast.makeText(parent.getContext(), "Selected Place Type : " + parent.getItemAtPosition(pos).toString(),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

}