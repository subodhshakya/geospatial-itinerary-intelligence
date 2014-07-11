package com.uah.geospatialitineraryintelligence;
/**
 * @author Subodh
 * @Description  Login Activity of the Application
 *
 */
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uah.geospatialitineraryintelligence.communication.HttpCommunicator;
import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.communication.HttpUrlManager;
import com.uah.geospatialitineraryintelligence.util.AlertDialogManager;
import com.uah.geospatialitineraryintelligence.util.ConnectionDetector;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.uah.geospatialitineraryintelligence.MESSAGE";
	public final Context mContext = this;

	// flag for Internet connection status
	Boolean isInternetPresent = false;
	// Connection detector class
	ConnectionDetector cd;
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_map);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_main);

		Button buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
		buttonSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SignUp(v);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void SignUp(View view) {
		Intent iSignup = new Intent(this, SignUpActivity.class);
		startActivity(iSignup);
	}

	/** Called when the user clicks the Login button */
	public void Login(View view) {

		EditText userName = (EditText) findViewById(R.id.usernameText);
		EditText passwordText = (EditText) findViewById(R.id.passwordText);

		/**
		 * Checks if the device has Internet Connection
		 */
		cd = new ConnectionDetector(getApplicationContext());
		// Check if Internet present
		isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			// Internet Connection is not present
			alert.showAlertDialog(MainActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
		/**
		 * End o Internet Connection Check
		 */

		/**
		 * This part is for testing with maps purpose as it opens
		 * DisplayMessageActivity
		 */
		// Intent intent1 = new Intent(this, DisplayMessageActivity.class);
		/*
		 * Intent intent1 = new Intent(this, PlaceSearchActivity.class);
		 * 
		 * //String message1 = userName.getText().toString();
		 * //intent1.putExtra(EXTRA_MESSAGE, message1); startActivity(intent1);
		 */

		String userEmail = userName.getText().toString();
		String password = passwordText.getText().toString();

		String url = HttpUrlManager.GetUrl("login"); // Gets URL for Login  

		// Http Post for Login
		String responseText = new HttpCommunicator().LoginPost(url, userEmail, password);
		if (responseText != null) {
			try {
				JSONObject obj = new JSONObject(responseText);
				new HttpSessionClass().SetSessionVariables(obj);

			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (HttpSessionClass.SessionMessage.equalsIgnoreCase("success")) {

				// Do something in response to button

				// Intent intent = new Intent(this,
				// DisplayMessageActivity.class);

				Intent intent = new Intent(this, HomeActivity.class);

				String message = userName.getText().toString();
				intent.putExtra(EXTRA_MESSAGE, message);
				startActivity(intent);

				HttpSessionClass.SetSessionCities();
				HttpSessionClass.SetSessionCountries();

			}
			else {
				AlertDialogManager alert = new AlertDialogManager();
				alert.showAlertDialog(mContext, "Login Error", HttpSessionClass.SessionMessage, false);

			}
		}
		else
			Toast.makeText(this, "Server Connection Failed!", Toast.LENGTH_SHORT).show();
	}

}
