package com.uah.geospatialitineraryintelligence;
/**
 * @author Binaya Raj
 * @Description  Implementation of Google Places API for searching places from Google and displaying in the listView.
 *
 */
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.uah.geospatialitineraryintelligence.communication.HttpCommunicator;
import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.datamodel.GIIPlace;
import com.uah.geospatialitineraryintelligence.datamodel.GIISector;
import com.uah.geospatialitineraryintelligence.util.AlertDialogManager;
import com.uah.geospatialitineraryintelligence.util.CustomOnItemSelectedListener;
import com.uah.geospatialitineraryintelligence.util.GIIJSONReader;
import com.uah.geospatialitineraryintelligence.util.GPSTracker;

public class PlaceSearchActivity extends Activity {

	public static String[] sectors = null;
	public static String SearchType = "lodging";
	public static String SearchResultStatus = "";
	public static double Latitude;
	public static double Longitude;
	// GPS Location
			public GPSTracker gps;
	// Google API Key
	private static final String API_KEY = "AIzaSyBP3gfPKgc0tZRJvkkwtIPQWcfMfRGNa2Q"; // place
																						// your
																						// API
																						// key
																						// here

	// Google Places search url's
	private static final String PLACES_NEARBYSEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";

	private double _latitude;
	private double _longitude;
	private double _radius;
	
	private AlertDialogManager alert;
	Spinner spinnerPlace;
	ListView placeLV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_search);
		// Alert Dialog Manager
		alert = new AlertDialogManager();
		GetCurrentLocation();

		 spinnerPlace = (Spinner) findViewById(R.id.spinnerPlaceType);
		 placeLV = (ListView) findViewById(R.id.placeListView);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.place_type_arrays,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerPlace.setAdapter(adapter);
		addListenerOnSpinnerItemSelection();

		Button btnPlaceSearch = (Button) findViewById(R.id.btnPlaceSearch);

		btnPlaceSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				SearchPlaces();

			}
		});
		
		
		placeLV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {

				GIIPlace selectedPlace = (GIIPlace) a.getItemAtPosition(position);
				HttpSessionClass.setSelectedPlace(selectedPlace); // Sets the
																	// current
																	// selected
																	// sector in
																	// the
																	// session
																	// for later
																	// access

				Intent intent = new Intent(PlaceSearchActivity.this, MapsActivity.class);
				startActivity(intent);
			}
		});

	}

	public void addListenerOnSpinnerItemSelection() {
		spinnerPlace = (Spinner) findViewById(R.id.spinnerPlaceType);
		spinnerPlace.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public void onResume() { // After a pause OR at startup
		super.onResume();
		// Refresh your stuff here
		GetCurrentLocation();
	}

	/**
	 * Gets the current Location of the user using gps
	 */
	public void GetCurrentLocation() {
		// GPS Location
		//GPSTracker gps;

		// creating GPS Class object
		gps = new GPSTracker(this); 

		// check if GPS location can get
		if (gps.canGetLocation()) {
			this._latitude = gps.getLatitude();					
			this._longitude = gps.getLongitude();
		
			this._radius = 5000;
			Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
			if(this._latitude == 0.0)
				this._latitude = 34.719248;
			if(this._longitude == 0.0)
				this._longitude = -86.6465801;
			Latitude = this._latitude;	
			Longitude = this._longitude;
		}
		else {			
			alert.showAlertDialog(PlaceSearchActivity.this, "GPS Status",
					"Couldn't get location information. Please enable GPS", false);
			// stop executing code by return
			return;
		}
	}

	public void SearchPlaces()// double latitude, double longitude, double
								// radius, String types)

	{
		String Url = PLACES_NEARBYSEARCH_URL + "location=" + _latitude + "," + _longitude + "&radius=" + _radius
				+ "&types=" + SearchType + "&sensor=false&" + "key=" + API_KEY;
		String response = new HttpCommunicator().GIIHttpGet(Url);

		ArrayList<GIIPlace> PlaceList = new ArrayList<GIIPlace>();
		PlaceList = new GIIJSONReader().ParseGooglePlaces(response);

		// Check for all possible status
		if (SearchResultStatus.equals("OK")) {
			// Successfully got places details and display in listview

			int placeCount = PlaceList.size(); // Gets the length of the
												// ArrayList

			GIIPlace[] placeArray = new GIIPlace[placeCount];
			placeCount = 0;
			// boolean isSuccess = true;
			for (GIIPlace place : PlaceList) {

				placeArray[placeCount] = place;
				placeCount++;
			}

			PlaceListAdapter adapter = new PlaceListAdapter(this, R.layout.place_listview_layout, R.id.placeListView,
					placeArray);

			placeLV.setAdapter(adapter);

		}
		else if (SearchResultStatus.equals("ZERO_RESULTS")) {
			// Zero results found
			alert.showAlertDialog(PlaceSearchActivity.this, "Near Places",
					"Sorry no places found. Try to change the types of places", false);
		}
		else if (SearchResultStatus.equals("UNKNOWN_ERROR")) {
			alert.showAlertDialog(PlaceSearchActivity.this, "Places Error", "Sorry unknown error occured.", false);
		}
		else if (SearchResultStatus.equals("OVER_QUERY_LIMIT")) {
			alert.showAlertDialog(PlaceSearchActivity.this, "Places Error",
					"Sorry query limit to google places is reached", false);
		}
		else if (SearchResultStatus.equals("REQUEST_DENIED")) {
			alert.showAlertDialog(PlaceSearchActivity.this, "Places Error", "Sorry error occured. Request is denied",
					false);
		}
		else if (SearchResultStatus.equals("INVALID_REQUEST")) {
			alert.showAlertDialog(PlaceSearchActivity.this, "Places Error", "Sorry error occured. Invalid Request",
					false);
		}
		else {
			alert.showAlertDialog(PlaceSearchActivity.this, "Places Error", "Sorry error occured.", false);
		}

	}

}
