package com.uah.geospatialitineraryintelligence;
/**
 * @author Binaya Raj
 * @Description  Activity for managing the travel details to the system. 
 *
 */
import java.util.ArrayList;

import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.datamodel.GIISector;
import com.uah.geospatialitineraryintelligence.datamodel.GIISegment;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TravelActivity extends Activity {

	public static String ItineraryCities;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_travel);

		Button reviewButton = (Button) findViewById(R.id.btnreviewSector);
		reviewButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TravelActivity.this, ReviewSectorActivity.class);
				startActivity(intent);

			}
		});

		RefreshActivity();

	}

	public void RefreshActivity() {
		ItineraryCities = "";
		GIISector sector = HttpSessionClass.getCurrentSector();
		ListView listView = (ListView) findViewById(R.id.travel_segments_listview);
		listView.setItemsCanFocus(true);

		ArrayList<GIISegment> segmentList = sector.GIISegmentList;

		GIISegment[] segmentArray = new GIISegment[segmentList.size()];
		int segmentCount = 0;
		for (GIISegment segment : segmentList) {
			segmentArray[segmentCount] = segment;
			segmentCount++;
		}

		TravelListAdapter adapter = new TravelListAdapter(this, R.layout.travel_listview_layout,
				R.id.travel_segments_listview, segmentArray);
		listView.setAdapter(adapter);
	}

	public void LaunchEditTravel(GIISegment segment) {
		Intent intent = new Intent(this, EditTravelActivity.class);
		// parent.getContext().startActivity(intent);
		startActivityForResult(intent, RESULT_OK);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				// GIISector sector = (GIISector)
				// data.getSerializableExtra("current_sector");
				String ok = data.getStringExtra("current_sector");
				String id = ok;
				TextView tx = (TextView) findViewById(R.id.sector_Name);
				tx.setText(id);

			}
		}
	}

	@Override
	public void onResume() { // After a pause OR at startup
		super.onResume();
		// Refresh your stuff here
		RefreshActivity();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.travel, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			// EITHER CALL THE METHOD HERE OR DO THE FUNCTION DIRECTLY
			new HttpSessionClass().LogoutUser();
			Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show();
			new HomeActivity().Logout();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
