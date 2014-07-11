package com.uah.geospatialitineraryintelligence;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uah.geospatialitineraryintelligence.communication.HttpCommunicator;
import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.communication.HttpUrlManager;
import com.uah.geospatialitineraryintelligence.datamodel.GIICoTraveler;
import com.uah.geospatialitineraryintelligence.util.GIIJSONReader;
import com.uah.geospatialitineraryintelligence.util.GIIJSONWriter;

public class CoTravelerActivity extends Activity {

	Button btnAdd;
	Button btnAddMore;
	Button btnCancel;
	EditText Name;
	EditText Relationship;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cotraveler);
		

		 btnAdd = (Button) findViewById(R.id.btnAdd);
		 btnAddMore = (Button) findViewById(R.id.btnAddMore);
		 btnCancel = (Button) findViewById(R.id.btnDismiss);
		 Name = (EditText) findViewById(R.id.name);
		 Relationship = (EditText) findViewById(R.id.relationship);		
		

		btnAdd.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AddCoTraveler();
				finish();

			}
		});
		
		btnCancel.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btnAddMore.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddCoTraveler();
				Name.setText("");
				Relationship.setText("");
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plan, menu);
		return true;
	}

	public void AddCoTraveler() {		

		/**
		 * Section for sending Review of the segment to the server
		 */
		GIICoTraveler coTraveler = new GIICoTraveler();
		coTraveler.userId = Integer.parseInt(HttpSessionClass.SessionId);
		coTraveler.name = Name.getText().toString();
		coTraveler.relationship = Relationship.getText().toString();
		coTraveler.message ="";

		String responseText = new HttpCommunicator().GIIHttpPost(HttpUrlManager.GetUrl("cotraveler"),
				new GIIJSONWriter().CreateCotravelerPayload(coTraveler));
		// 
		//GIICoTraveler coTravelerResponse = new GIIJSONReader().ParseCoTraveler(responseText);
		if(responseText.contains("added")){

				Toast.makeText(this, "CoTraveler "+ coTraveler.name + "has been added!", Toast.LENGTH_SHORT).show();				 
		
		finish(); //To close the activity
		}
		else
		{
			Toast.makeText(this, "CoTraveler addition Failed!!", Toast.LENGTH_SHORT).show();	
		}

	}
	
	

	

}
