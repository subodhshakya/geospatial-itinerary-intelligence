package com.uah.geospatialitineraryintelligence;
/**
 * @author Archana
 * @Description  Home Screen for the application with interface for various features of the application.
 *
 */

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uah.geospatialitineraryintelligence.communication.HttpCommunicator;
import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.communication.HttpUrlManager;
import com.uah.geospatialitineraryintelligence.datamodel.GIISector;
import com.uah.geospatialitineraryintelligence.util.GIIJSONReader;

public class HomeActivity extends Activity {

	public static String[] sectors = null;
	public static int IsCompleteItinerary = 0;
	public static Context global;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		global = getApplicationContext();

		// Get the message from the intent
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		TextView welcomText = (TextView) findViewById(R.id.welcome);
		welcomText.setText("Welcome " + HttpSessionClass.SessionUserName + "!");// +
																				// "("
																				// +
																				// message
																				// +")"
																				// +
																				// "!");

		ListView listView_old = (ListView) findViewById(R.id.itinerary_listview);
		ListView listView_new = (ListView) findViewById(R.id.itinerary_new_listview);

		listView_old.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {

				GIISector currentSector = (GIISector) a.getItemAtPosition(position);
				HttpSessionClass.setCurrentSector(currentSector); // Sets the
																	// current
																	// selected
																	// sector in
																	// the
																	// session
																	// for later
																	// access

				Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);

				startActivity(intent);
			}
		});
		listView_new.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {

				GIISector currentSector = (GIISector) a.getItemAtPosition(position);
				HttpSessionClass.setCurrentSector(currentSector); // Sets the
																	// current
																	// selected
																	// sector in
																	// the
																	// session
																	// for later
																	// access

				Intent intent = new Intent(HomeActivity.this, TravelActivity.class);

				startActivity(intent);
			}
		});

		Button btn_RecItinerary = (Button) findViewById(R.id.btn_RecItinerary);
		btn_RecItinerary.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IsCompleteItinerary = 1; // "1" for old/complete itinerary
				onClick_btn_RecItinerary(IsCompleteItinerary);
			}
		});
		Button btn_Travel = (Button) findViewById(R.id.btn_travelItinerary);
		btn_Travel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//
				// Intent intent = new
				// Intent(HomeActivity.this,EditTravelActivity.class);
				// startActivity(intent);

				IsCompleteItinerary = 0;// "0" for new/incomplete itinerary
				onClick_btn_RecItinerary(IsCompleteItinerary);

			}
		});
		Button search_Place = (Button) findViewById(R.id.searchPlace);
		search_Place.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(HomeActivity.this, PlaceSearchActivity.class);
				startActivity(intent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	/**
	 * 
	 * @param type
	 *            : if "0": itinerary is new/incomplete. if "1": itinerary is
	 *            old/complete
	 * 
	 */
	public void onClick_btn_RecItinerary(int type) {

		String url = HttpUrlManager.GetUrl("sector"); // Gets URL for Login
		String responseText = new HttpCommunicator().GetSectors(url + "/" + HttpSessionClass.SessionId + "%7C" + type);// "%/7C"
																														// is
																														// for
																														// "|"
																														// separator

		ListView listView = null;
		ListView listView_old = (ListView) findViewById(R.id.itinerary_listview);
		ListView listView_new = (ListView) findViewById(R.id.itinerary_new_listview);
		int RIdListView = R.id.itinerary_listview;
		if (type == 0) {
			listView_old.setVisibility(View.INVISIBLE);
			listView_new.setVisibility(View.VISIBLE);
			listView = listView_new;
			RIdListView = R.id.itinerary_new_listview;

		}
		else {
			listView_new.setVisibility(View.INVISIBLE);
			listView_old.setVisibility(View.VISIBLE);
			listView = listView_old;
		}

		try {
			GIIJSONReader giiJReader = new GIIJSONReader();
			ArrayList<GIISector> sectorList = giiJReader.ParseSector(responseText);

			int sectorCount = sectorList.size(); // Gets the length of the
													// ArrayList

			GIISector[] sectorArray = new GIISector[sectorCount];
			sectorCount = 0;
			boolean isSuccess = true;
			for (GIISector sector : sectorList) {
				if (sector.message.equalsIgnoreCase("success"))
					isSuccess = true;
				else
					isSuccess = false;
				sectorArray[sectorCount] = sector;
				sectorCount++;
			}

			if (isSuccess) {
				ItineraryListAdapter adapter = new ItineraryListAdapter(this, R.layout.sector_listview_layout,
						RIdListView, sectorArray);
				// R.id.itinerary_listview, sectorArray);
				listView.setAdapter(adapter);
			}

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void on_Click_PlanIinerary(View v) {
		Intent intent = new Intent(this, PlanActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			// EITHER CALL THE METHOD HERE OR DO THE FUNCTION DIRECTLY
			new HttpSessionClass().LogoutUser();
			Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show();
			Logout();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {

			Toast.makeText(this, "You need to press Logout button to exit!", Toast.LENGTH_SHORT).show();
			return true;
			// Log.d(this.getClass().getName(), "back button pressed");
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * This method called from every activity to logout from the application.
	 */
	public void Logout() {
		Intent intent = new Intent(global, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// intent.putExtra("EXIT", true);
		startActivity(intent);
	}
	
	@Override
	public void onResume() { // After a pause OR at startup
		super.onResume();
		// Refresh your stuff here
		onClick_btn_RecItinerary(IsCompleteItinerary);
	}

}
