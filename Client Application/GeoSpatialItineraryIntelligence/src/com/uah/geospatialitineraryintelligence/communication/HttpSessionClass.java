package com.uah.geospatialitineraryintelligence.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.uah.geospatialitineraryintelligence.datamodel.GIICity;
import com.uah.geospatialitineraryintelligence.datamodel.GIICountry;
import com.uah.geospatialitineraryintelligence.datamodel.GIIPlace;
import com.uah.geospatialitineraryintelligence.datamodel.GIISector;
import com.uah.geospatialitineraryintelligence.datamodel.GIISegment;
import com.uah.geospatialitineraryintelligence.util.GIIJSONReader;

/**
 * @author Binaya Raj
 * @Description  HttpSessionClass maintains the session of the logged in user in the application. It also maintains the session data for the application.
 *
 */
public class HttpSessionClass {
	public static String SessionId = null;
	public static String SessionUserName = null;
	public static String SessionMessage = null;
	public static boolean IsLoggedIn = false;
	
	public static Map<Integer, String> SessionCountries = new HashMap<Integer, String>();
	public static Map<Integer, String> SessionCities = new HashMap<Integer, String>();
	public static GIISector currentSector = new GIISector();
	public static GIISegment currentSegment = new GIISegment();
	public static GIIPlace selectedPlace = new GIIPlace();
	public static GIISegment[] searchSegmentArray= null;
	//public static 
	
	// This class will set the User session in the application.

	
	
	public static GIIPlace getSelectedPlace() {
		return selectedPlace;
	}

	public static void setSelectedPlace(GIIPlace selectedPlace) {
		HttpSessionClass.selectedPlace = selectedPlace;
	}

	public static GIISegment getCurrentSegment() {
		return currentSegment;
	}

	public static void setCurrentSegment(GIISegment currentSegment) {
		HttpSessionClass.currentSegment = currentSegment;
	}

	public static GIISector getCurrentSector() {
		return currentSector;
	}

	public static void setCurrentSector(GIISector currentSector) {
		HttpSessionClass.currentSector = currentSector;
	}

	public void SetSessionVariables(JSONObject sessionObj) {
		try {
			SessionMessage = sessionObj.getString("message").toString();
			if (SessionMessage.equalsIgnoreCase("success")) {
				IsLoggedIn = true;
				SessionId = sessionObj.getString("userId");
				SessionUserName = sessionObj.getString("firstName") + " "
						+ sessionObj.getString("middleName") + " "
						+ sessionObj.getString("lastName");
			} else {

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Clears session variables for the user
	public void LogoutUser() {
		
		SessionId = null;
		SessionMessage = null;
		SessionUserName = null;
		IsLoggedIn = false;

	}

	public static void SetSessionCities() {
		String Url = HttpUrlManager.GetUrl("city");
		String response = new HttpCommunicator().GIIHttpGet(Url);
		ArrayList<GIICity> cityList = new GIIJSONReader().ParseCities(response);

		for (GIICity city : cityList) {
			SessionCities.put(city.CityId, city.Name.trim());
		}

	}

	public static void SetSessionCountries() {
		String Url = HttpUrlManager.GetUrl("country");
		String response = new HttpCommunicator().GIIHttpGet(Url);
		ArrayList<GIICountry> countryList = new GIIJSONReader()
				.ParseCountry(response);

		for (GIICountry country : countryList) {
			SessionCountries.put(+country.CountryId, country.Name.trim());
		}

	}

	/**
	 * Method to get "Key" from <Key,Value> pairs through "value"
	 * @param SessionMap : Map<Integer, String>: key-value pair (Eg. SessionCities, SessionCountries)
	 * @param value: String: Value of the key-value pair
	 * @return
	 */
	public static Integer GetKeyFromValue(Map<Integer, String> SessionMap,
			String value) {
		Integer Key = 0;
		for (Entry<Integer, String> entry : SessionMap.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(value.trim())) {
				Key = entry.getKey();
			}
		}
		return Key;
	}

}

