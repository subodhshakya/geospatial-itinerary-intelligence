package com.uah.geospatialitineraryintelligence;

/**
 * @author Binaya Raj
 * @Description  Implementation of Google Maps API v2
 *
 */
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.datamodel.GIIPlace;

public class MapsActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;
	// latitude and longitude
	double latitude;
	double longitude;
	GIIPlace selectedPlace;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		latitude = new PlaceSearchActivity().Latitude;
		longitude = new PlaceSearchActivity().Longitude;
		selectedPlace = HttpSessionClass.getSelectedPlace();
		try {
			// Loading map
			initilizeMap();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.travel, menu);
		return true;
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	
		private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
			}
			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setCompassEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

			// create marker
			MarkerOptions markerUser = new MarkerOptions().position(new LatLng(latitude, longitude)).title(
					"You are HERE. ");

			// adding marker
			googleMap.addMarker(markerUser);
			
	       
			
			// create marker
			MarkerOptions markerPlace = new MarkerOptions().position(
					new LatLng(selectedPlace.latitute, selectedPlace.longitude)).title(selectedPlace.name);
			markerPlace.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

			// adding marker
			googleMap.addMarker(markerPlace);
			googleMap.addMarker(markerUser);	

			 CameraPosition cameraPosition = CameraPosition.builder()
		                .target(new LatLng(selectedPlace.latitute, selectedPlace.longitude))
		                .zoom(12)
		                .bearing(90)
		                .build();
		        
		        // Animate the change in camera view over 2 seconds
		        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
		                2000, null);
			
			// Polylines are useful for marking paths and routes on the map.
			googleMap.addPolyline(new PolylineOptions().geodesic(true).add(new LatLng(latitude, longitude)) // Current
																											// Location
					.add(new LatLng(selectedPlace.latitute, selectedPlace.longitude)) // Selected
																						// location					
					);
			
			// Instantiates a new CircleOptions object and defines the center and radius
			CircleOptions circleOptions = new CircleOptions()
			    .center(new LatLng(latitude, longitude))
			    .radius(5000).strokeColor(Color.CYAN); // In meters

			// Get back the mutable Circle
			Circle circle = googleMap.addCircle(circleOptions);

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}
	

}
