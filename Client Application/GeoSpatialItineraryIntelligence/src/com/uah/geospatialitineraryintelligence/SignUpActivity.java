package com.uah.geospatialitineraryintelligence;
/**
 * @author Archana
 * @Description  Activity for signing up new user in the system
 *
 */
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.uah.geospatialitineraryintelligence.communication.HttpCommunicator;
import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.communication.HttpUrlManager;
import com.uah.geospatialitineraryintelligence.datamodel.CUser;
import com.uah.geospatialitineraryintelligence.util.AlertDialogManager;

public class SignUpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		btnSignUp = (Button) findViewById(R.id.btn_SignUp);
		etFirstName = (EditText) findViewById(R.id.edit_FirstName);
		etMiddleName = (EditText) findViewById(R.id.edit_MiddleName);
		etLastName = (EditText) findViewById(R.id.edit_LastName);
		etUserName = (EditText) findViewById(R.id.edit_UserName);
		etPassword = (EditText) findViewById(R.id.edit_Password);
		etConfirmPassword = (EditText) findViewById(R.id.edit_ConfirmPassword);
		etStreet = (EditText) findViewById(R.id.edit_Street);
		etCity = (EditText) findViewById(R.id.edit_City);
		etState = (EditText) findViewById(R.id.edit_State);
		etCountry = (EditText) findViewById(R.id.edit_Country);
		etZip = (EditText) findViewById(R.id.edit_Zip);

		etConfirmPassword.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!etConfirmPassword.hasFocus())
					if (!etConfirmPassword.getText().toString().equals(etPassword.getText().toString())) {
						AlertDialogManager alert = new AlertDialogManager();
						alert.showAlertDialog(SignUpActivity.this, "Error", "Password mismatch", false);
					}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

	Button btnSignUp;
	EditText etFirstName, etMiddleName, etLastName, etUserName, etPassword, etConfirmPassword, etStreet, etCity,
			etState, etCountry, etZip;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) { return true; }
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_sign_up, container, false);
			return rootView;
		}
	}

	public void signUp(View v) {


		String firstName = etFirstName.getText().toString();
		String middleName = etMiddleName.getText().toString();
		String lastName = etLastName.getText().toString();
		String email = etUserName.getText().toString();
		String password = etPassword.getText().toString();
		String confirmPassword = etConfirmPassword.getText().toString();
		String street = etStreet.getText().toString();
		String city = etCity.getText().toString();
		String state = etState.getText().toString();
		String country = etCountry.getText().toString();
		int zip = Integer.parseInt(etZip.getText().toString());

		if (password.equals(confirmPassword)) {
			CUser user = new CUser(firstName, middleName, lastName, email, password, street, city, state, country, zip,
					0);
			boolean hasError = false;

			// try {
			String url = HttpUrlManager.GetUrl("user");
			String responseText = new HttpCommunicator().SignUpPost(url, user);

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
				Intent intent = new Intent(this, HomeActivity.class);
				startActivity(intent);
			}
			else {
				AlertDialogManager alert = new AlertDialogManager();
				alert.showAlertDialog(SignUpActivity.this, "Oops!!!", HttpSessionClass.SessionMessage, false);
			}

		}
		else {
			AlertDialogManager alert = new AlertDialogManager();
			alert.showAlertDialog(this, "Error", "Password mismatch", false);
			etPassword.setText("");
			etConfirmPassword.setText("");
		}

	}

}
