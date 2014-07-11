package com.uah.geospatialitineraryintelligence.communication;

/**
 * @author Binaya Raj
 * @Description  HttCommunicator handles the communication part with the server with httpRequest and httpResponse
 *
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import com.uah.geospatialitineraryintelligence.datamodel.CUser;

public class HttpCommunicator {

	DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());	

	// Method to Post Login details to the server.
	public String LoginPost(String url, String userName, String password) {
		HttpPost httppost = new HttpPost(url);
		String result = null;

		HttpConnectionParams
				.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
																		// Limit
		HttpResponse response;
		JSONObject json = new JSONObject();

		try {
			httppost.setHeader("Content-type", "application/json");

			json.put("email", userName);
			json.put("password", password);

			StringEntity se = new StringEntity(json.toString());

			httppost.setEntity(se);
			response = httpclient.execute(httppost);
			//if (response.getStatusLine().getStatusCode() == 200)
			{

				/* Checking response */
				if (response != null) {
					InputStream in = response.getEntity().getContent(); // Get
																		// the
																		// data
																		// in
																		// the
																		// entity
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in, "UTF-8"), 8);
					StringBuilder sb = new StringBuilder();

					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					result = sb.toString();
				}
			}			

		} catch (Exception e) {
			e.printStackTrace();
			// createDialog("Error", "Cannot Estabilish Connection");
		}
		return result;
 
	}

	public String HttpPost() {
		
		String URL = "http://192.168.1.106:8081/api/userinfo/";
		HttpPost httppost = new HttpPost(URL);
		String result = null;

		HttpConnectionParams
				.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
																		// Limit
		HttpResponse response;
		JSONObject json = new JSONObject();
		
		try {
			httppost.setHeader("Content-type", "application/json");

			json.put("firstName", "" );
			json.put("middleName", "");
			json.put("lastName", "");
			json.put("password", "");
			json.put("email", "");
			json.put("street", "");
			json.put("city", "");
			json.put("state", "");
			json.put("country", "");
			json.put("zipCode", "");
			json.put("noOfTraveler", "");

			StringEntity se = new StringEntity(json.toString());

			httppost.setEntity(se);
			response = httpclient.execute(httppost);

			/* Checking response */
			if (response != null) {
				InputStream in = response.getEntity().getContent(); // Get the
																	// data in
																	// the
																	// entity
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in, "UTF-8"), 8);
				StringBuilder sb = new StringBuilder();

				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				result = sb.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
			// createDialog("Error", "Cannot Estabilish Connection");
		}
		return result;

	}

public String SignUpPost(String URL, CUser user) {
		
		//String URL = "http://192.168.1.106:8081/api/userinfo/";
		HttpPost httppost = new HttpPost(URL);
		String result = null;

		HttpConnectionParams
				.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
																		// Limit
		HttpResponse response;
		JSONObject json = new JSONObject();
		
		try {
			httppost.setHeader("Content-type", "application/json");

			json.put("firstName", user.getFirstName() );
			json.put("middleName", user.getMiddleName());
			json.put("lastName", user.getLastName());
			json.put("password", user.getPassword());
			json.put("email", user.getEmail());
			json.put("street", user.getStreet());
			json.put("city", user.getCity());
			json.put("state", user.getState());
			json.put("country", user.getCountry());
			json.put("zipCode", user.getZipCode());
			json.put("noOfTraveler", user.getNoOfTraveler());

			StringEntity se = new StringEntity(json.toString());

			httppost.setEntity(se);
			response = httpclient.execute(httppost);

			/* Checking response */
			if (response != null) {
				InputStream in = response.getEntity().getContent(); // Get the
																	// data in
																	// the
																	// entity
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in, "UTF-8"), 8);
				StringBuilder sb = new StringBuilder();

				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				result = sb.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
			// createDialog("Error", "Cannot Estabilish Connection");
		}
		return result;

	}

	
	public String GIIHttpGet(String Url) {
		HttpGet httpget = new HttpGet(Url);
		InputStream inputStream = null;
		String result = "none";
		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			inputStream = entity.getContent();
			// json is UTF-8 by default
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			result = sb.toString();
		} catch (Exception e) {
			// Oops
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception squish) {
			}
		}
		return result;
	}
	
	
	
	public String GetSectors(String url) {
		
		InputStream inputStream = null;
		HttpGet httpget = new HttpGet(url);
		String result = null;
		
		try {
			HttpResponse response = httpclient.execute(httpget); 
			HttpEntity entity = response.getEntity();

			inputStream = entity.getContent();
			// json is UTF-8 by default
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			result = sb.toString();
		} catch (Exception e) {
			// Oops
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception squish) {
			}
		}
		return result;

	}
	
	public String GIIHttpPost(String Url, String Payload){
		HttpPost httppost = new HttpPost(Url);
		String result = null;

		HttpConnectionParams
				.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
																		// Limit
		HttpResponse response;
		
		
		try {
			httppost.setHeader("Content-type", "application/json");			

			StringEntity se = new StringEntity(Payload);

			httppost.setEntity(se);
			response = httpclient.execute(httppost);

			/* Checking response */
			if (response != null) {
				InputStream in = response.getEntity().getContent(); // Get the
																	// data in
																	// the
																	// entity
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in, "UTF-8"), 8);
				StringBuilder sb = new StringBuilder();

				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				result = sb.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
			// createDialog("Error", "Cannot Estabilish Connection");
		}
		return result;
	}

}
