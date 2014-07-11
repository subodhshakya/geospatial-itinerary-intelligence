package com.uah.geospatialitineraryintelligence.util;
/**
 * @author Subodh
 * @Description  Custom class for parsing RAW JSON data and convert it to respective class models.
 *
 */

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.uah.geospatialitineraryintelligence.PlaceSearchActivity;
import com.uah.geospatialitineraryintelligence.datamodel.CUser;
import com.uah.geospatialitineraryintelligence.datamodel.GIICity;
import com.uah.geospatialitineraryintelligence.datamodel.GIICoTraveler;
import com.uah.geospatialitineraryintelligence.datamodel.GIICountry;
import com.uah.geospatialitineraryintelligence.datamodel.GIILocationPlace;
import com.uah.geospatialitineraryintelligence.datamodel.GIIPhoto;
import com.uah.geospatialitineraryintelligence.datamodel.GIIPlace;
import com.uah.geospatialitineraryintelligence.datamodel.GIIReview;
import com.uah.geospatialitineraryintelligence.datamodel.GIISector;
import com.uah.geospatialitineraryintelligence.datamodel.GIISegment;

public class GIIJSONReader {

	/**
	 * 
	 * @param response
	 * @return
	 */
	public ArrayList<GIISector> ParseSector(String response) {
		ArrayList<GIISector> giiSectorList = new ArrayList<GIISector>();

		try {

			JSONArray sectorListRoot = new JSONArray(response);
			JSONObject sectorsObject = sectorListRoot.getJSONObject(0);
			JSONArray sectorList = sectorsObject.getJSONArray("sectorList");

			for (int i = 0; i < sectorList.length(); i++) {
				GIISector giiSector = new GIISector();
				giiSector.OriginCityId = sectorList.getJSONObject(i).isNull("originCityId") ? -1 : sectorList
						.getJSONObject(i).getInt("originCityId");
				giiSector.DestinationCityId = sectorList.getJSONObject(i).isNull("destinationCityId") ? -1 : sectorList
						.getJSONObject(i).getInt("destinationCityId");
				giiSector.PlanDate = sectorList.getJSONObject(i).isNull("planDate") ? "empty" : sectorList
						.getJSONObject(i).getString("planDate");
				giiSector.Cost = sectorList.getJSONObject(i).isNull("cost") ? 0.0 : sectorList.getJSONObject(i)
						.getDouble("cost");
				giiSector.Distance = sectorList.getJSONObject(i).isNull("distance") ? 0.0 : sectorList.getJSONObject(i)
						.getDouble("distance");
				giiSector.NoOfSegments = sectorList.getJSONObject(i).isNull("noOfSegments") ? 0 : sectorList
						.getJSONObject(i).getInt("noOfSegments");
				giiSector.UserId = sectorList.getJSONObject(i).isNull("userId") ? 0 : sectorList.getJSONObject(i)
						.getInt("userId");
				giiSector.message = sectorList.getJSONObject(i).isNull("message") ? "empty" : sectorList.getJSONObject(
						i).getString("message");

				ArrayList<GIISegment> giiSegmentList = new ArrayList<GIISegment>();

				JSONObject segmentsObject = sectorList.getJSONObject(i);
				JSONArray segmentList = segmentsObject.getJSONArray("segmentList");
				for (int j = 0; j < segmentList.length(); j++) {
					GIISegment giiSegment = new GIISegment();
					giiSegment.SegmentId = segmentList.getJSONObject(j).isNull("segmentId") ? 0 : segmentList
							.getJSONObject(j).getInt("segmentId");
					giiSegment.OriginCityId = segmentList.getJSONObject(j).isNull("originCityId") ? 0 : segmentList
							.getJSONObject(j).getInt("originCityId");
					giiSegment.DestinationCityId = segmentList.getJSONObject(j).isNull("destinationCityId") ? 0
							: segmentList.getJSONObject(j).getInt("destinationCityId");
					giiSegment.StartDate = segmentList.getJSONObject(j).isNull("startDate") ? "empty" : segmentList
							.getJSONObject(j).getString("startDate");
					giiSegment.EndDate = segmentList.getJSONObject(j).isNull("endDate") ? "empty" : segmentList
							.getJSONObject(j).getString("endDate");
					giiSegment.Distance = segmentList.getJSONObject(j).isNull("distance") ? 0.0 : segmentList
							.getJSONObject(j).getDouble("distance");
					giiSegment.Cost = segmentList.getJSONObject(j).isNull("cost") ? 0.0 : segmentList.getJSONObject(j)
							.getDouble("cost");
					giiSegment.SectorId = segmentList.getJSONObject(j).isNull("sectorId") ? -1 : segmentList
							.getJSONObject(j).getInt("sectorId");
					giiSegment.ReviewId = segmentList.getJSONObject(j).isNull("reviewId") ? -1 : segmentList
							.getJSONObject(j).getInt("reviewId");
					giiSegment.NextSegmentId = segmentList.getJSONObject(j).isNull("nextSegmentId") ? 0 : segmentList
							.getJSONObject(j).getInt("nextSegmentId");
					giiSegmentList.add(giiSegment);
				}

				giiSector.GIISegmentList = giiSegmentList;
				giiSectorList.add(giiSector);
			}
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return giiSectorList;
	}

	/**
	 * 
	 * @param response
	 * @return
	 */
	public ArrayList<GIICountry> ParseCountry(String response) {
		ArrayList<GIICountry> giiCountryList = new ArrayList<GIICountry>();

		try {

			JSONArray countryListRoot = new JSONArray(response);
			JSONObject countriesObject = countryListRoot.getJSONObject(0);
			JSONArray countryList = countriesObject.getJSONArray("countryList");

			for (int i = 0; i < countryList.length(); i++) {
				GIICountry giiCountry = new GIICountry();
				giiCountry.CapitalCityId = countryList.getJSONObject(i).isNull("countryId") ? -1 : countryList
						.getJSONObject(i).getInt("countryId");
				giiCountry.Name = countryList.getJSONObject(i).isNull("name") ? "empty" : countryList.getJSONObject(i)
						.getString("name");
				giiCountry.CapitalCityId = countryList.getJSONObject(i).isNull("capitalCityId") ? -1 : countryList
						.getJSONObject(i).getInt("capitalCityId");
				giiCountry.message = countryList.getJSONObject(i).isNull("message") ? "empty" : countryList
						.getJSONObject(i).getString("message");

				giiCountryList.add(giiCountry);

			}
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return giiCountryList;
	}

	/**
	 * 
	 * @param response
	 * @return
	 */
	public ArrayList<GIICity> ParseCities(String response) {
		ArrayList<GIICity> giiCityList = new ArrayList<GIICity>();

		try {

			JSONArray cityListRoot = new JSONArray(response);
			JSONObject citiesObject = cityListRoot.getJSONObject(0);
			JSONArray cityList = citiesObject.getJSONArray("cityList");

			for (int i = 0; i < cityList.length(); i++) {
				GIICity giiCity = new GIICity();
				giiCity.CityId = cityList.getJSONObject(i).isNull("cityId") ? -1 : cityList.getJSONObject(i).getInt(
						"cityId");
				giiCity.Name = cityList.getJSONObject(i).isNull("name") ? "empty" : cityList.getJSONObject(i)
						.getString("name");
				giiCity.CountryId = cityList.getJSONObject(i).isNull("countryId") ? -1 : cityList.getJSONObject(i)
						.getInt("countryId");
				giiCity.ZipCode = cityList.getJSONObject(i).isNull("zipCode") ? -1 : cityList.getJSONObject(i).getInt(
						"zipCode");
				giiCity.message = cityList.getJSONObject(i).isNull("message") ? "empty" : cityList.getJSONObject(i)
						.getString("message");

				giiCityList.add(giiCity);
			}
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return giiCityList;
	}

	/**
	 * 
	 * @param response
	 * @return
	 */
	public ArrayList<GIISegment> ParseSegments(String response) {
		ArrayList<GIISegment> giiSegmentList = new ArrayList<GIISegment>();
		try {
			JSONArray segmentListRoot = new JSONArray(response);
			JSONObject segmentsObject = segmentListRoot.getJSONObject(0);
			JSONArray segmentList = segmentsObject.getJSONArray("segmentList");
			for (int j = 0; j < segmentList.length(); j++) {
				GIISegment giiSegment = new GIISegment();
				giiSegment.SegmentId = segmentList.getJSONObject(j).isNull("segmentId") ? 0 : segmentList
						.getJSONObject(j).getInt("segmentId");
				giiSegment.OriginCityId = segmentList.getJSONObject(j).isNull("originCityId") ? 0 : segmentList
						.getJSONObject(j).getInt("originCityId");
				giiSegment.DestinationCityId = segmentList.getJSONObject(j).isNull("destinationCityId") ? 0
						: segmentList.getJSONObject(j).getInt("destinationCityId");
				giiSegment.StartDate = segmentList.getJSONObject(j).isNull("startDate") ? "empty" : segmentList
						.getJSONObject(j).getString("startDate");
				giiSegment.EndDate = segmentList.getJSONObject(j).isNull("endDate") ? "empty" : segmentList
						.getJSONObject(j).getString("endDate");
				giiSegment.Distance = segmentList.getJSONObject(j).isNull("distance") ? 0.0 : segmentList
						.getJSONObject(j).getDouble("distance");
				giiSegment.Cost = segmentList.getJSONObject(j).isNull("cost") ? 0.0 : segmentList.getJSONObject(j)
						.getDouble("cost");
				giiSegment.SectorId = segmentList.getJSONObject(j).isNull("sectorId") ? -1 : segmentList.getJSONObject(
						j).getInt("sectorId");
				giiSegment.ReviewId = segmentList.getJSONObject(j).isNull("reviewId") ? -1 : segmentList.getJSONObject(
						j).getInt("reviewId");
				giiSegment.NextSegmentId = segmentList.getJSONObject(j).isNull("nextSegmentId") ? 0 : segmentList
						.getJSONObject(j).getInt("nextSegmentId");
				giiSegmentList.add(giiSegment);
			}
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return giiSegmentList;

	}

	/**
	 * Parse response string and maps to GIISector object.
	 * 
	 * @param responseText
	 *            : response string from server
	 * @return giiSector: GIISector type object
	 */
	public GIISector ParseSingleSector(String responseText) {

		GIISector giiSector = new GIISector();
		try {
			JSONObject sectorsObject = new JSONObject(responseText);

			sectorsObject.getString("message").toString();
			giiSector.OriginCityId = sectorsObject.isNull("originCityId") ? -1 : sectorsObject.getInt("originCityId");
			giiSector.DestinationCityId = sectorsObject.isNull("destinationCityId") ? -1 : sectorsObject
					.getInt("destinationCityId");
			giiSector.PlanDate = sectorsObject.isNull("planDate") ? "empty" : sectorsObject.getString("planDate");
			giiSector.Cost = sectorsObject.isNull("cost") ? 0.0 : sectorsObject.getDouble("cost");
			giiSector.Distance = sectorsObject.isNull("distance") ? 0.0 : sectorsObject.getDouble("distance");
			giiSector.NoOfSegments = sectorsObject.isNull("noOfSegments") ? 0 : sectorsObject.getInt("noOfSegments");
			giiSector.UserId = sectorsObject.isNull("userId") ? 0 : sectorsObject.getInt("userId");
			giiSector.message = sectorsObject.isNull("message") ? "empty" : sectorsObject.getString("message");

			ArrayList<GIISegment> giiSegmentList = new ArrayList<GIISegment>();

			JSONArray segmentList = sectorsObject.getJSONArray("segmentList");
			for (int j = 0; j < segmentList.length(); j++) {
				GIISegment giiSegment = new GIISegment();
				giiSegment.SegmentId = segmentList.getJSONObject(j).isNull("segmentId") ? 0 : segmentList
						.getJSONObject(j).getInt("segmentId");
				giiSegment.OriginCityId = segmentList.getJSONObject(j).isNull("originCityId") ? 0 : segmentList
						.getJSONObject(j).getInt("originCityId");
				giiSegment.DestinationCityId = segmentList.getJSONObject(j).isNull("destinationCityId") ? 0
						: segmentList.getJSONObject(j).getInt("destinationCityId");
				giiSegment.StartDate = segmentList.getJSONObject(j).isNull("startDate") ? "empty" : segmentList
						.getJSONObject(j).getString("startDate");
				giiSegment.EndDate = segmentList.getJSONObject(j).isNull("endDate") ? "empty" : segmentList
						.getJSONObject(j).getString("endDate");
				giiSegment.Distance = segmentList.getJSONObject(j).isNull("distance") ? 0.0 : segmentList
						.getJSONObject(j).getDouble("distance");
				giiSegment.Cost = segmentList.getJSONObject(j).isNull("cost") ? 0.0 : segmentList.getJSONObject(j)
						.getDouble("cost");
				giiSegment.SectorId = segmentList.getJSONObject(j).isNull("sectorId") ? -1 : segmentList.getJSONObject(
						j).getInt("sectorId");
				giiSegment.ReviewId = segmentList.getJSONObject(j).isNull("reviewId") ? -1 : segmentList.getJSONObject(
						j).getInt("reviewId");
				giiSegment.NextSegmentId = segmentList.getJSONObject(j).isNull("nextSegmentId") ? 0 : segmentList
						.getJSONObject(j).getInt("nextSegmentId");
				giiSegmentList.add(giiSegment);
			}

			giiSector.GIISegmentList = giiSegmentList;
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return giiSector;
	}

	/**
	 * Converts JSONstring to GIIReview object
	 * 
	 * @param responseText
	 * @return
	 */
	public GIIReview ParseReview(String responseText) {

		Gson g = new Gson();
		GIIReview giiReview = g.fromJson(responseText, GIIReview.class);
		return giiReview;
	}
	public GIILocationPlace ParseLocationPlace(String responseText) {
		
		Gson g = new Gson();
		GIILocationPlace giiLocationPlace = g.fromJson(responseText, GIILocationPlace.class);
		return giiLocationPlace;
	}
	public GIIPhoto ParsePhoto(String responseText) {
		
		Gson g = new Gson();
		GIIPhoto giiPhoto = g.fromJson(responseText, GIIPhoto.class);
		return giiPhoto;
	}
	/**
	 * Parses cotraveler response to the cotravel model
	 * @param responseText
	 * @return
	 */
	
	public GIICoTraveler ParseCoTraveler(String responseText) {
		
		Gson g = new Gson();
		GIICoTraveler giiCoTraveler = g.fromJson(responseText, GIICoTraveler.class);
		return giiCoTraveler;
	}
	public CUser ParseUser(String responseText) {
		
		Gson g = new Gson();
		CUser user = g.fromJson(responseText, CUser.class);
		return user;
	}

	/**
	 * Gets the google places from the response to the httprequest to https://maps.googleapis.com/maps/api/place/nearbysearch/json?
	 * @param response
	 * @return ArrayList of GIIPlace
	 */
	public ArrayList<GIIPlace> ParseGooglePlaces(String response) {
		ArrayList<GIIPlace> giiPlaceList = new ArrayList<GIIPlace>();

		// Need to handle to status of the results.
		try {

			JSONObject placeResults = new JSONObject(response); 
			PlaceSearchActivity.SearchResultStatus = placeResults.getString("status");
			JSONArray placeList = placeResults.getJSONArray("results");
			
			for (int i = 0; i < placeList.length(); i++) {
				GIIPlace giiPlace = new GIIPlace(); 
				giiPlace.vicinity = placeList.getJSONObject(i).isNull("vicinity") ? "empty" : placeList
						.getJSONObject(i).getString("vicinity");
				giiPlace.name = placeList.getJSONObject(i).isNull("name") ? "empty" : placeList
						.getJSONObject(i).getString("name");
				giiPlace.reference = placeList.getJSONObject(i).isNull("reference") ? "empty" : placeList
						.getJSONObject(i).getString("reference");
				giiPlace.rating = placeList.getJSONObject(i).isNull("rating") ? 0.0 : placeList.getJSONObject(i)
						.getDouble("rating");
				giiPlace.id = placeList.getJSONObject(i).isNull("id") ? "empty" : placeList.getJSONObject(i)
						.getString("id");
				giiPlace.iconSrc = placeList.getJSONObject(i).isNull("icon") ? "empty" : placeList
						.getJSONObject(i).getString("icon");   
				JSONObject test = placeList.getJSONObject(i).getJSONObject("geometry"); 
				JSONObject test1 = test.getJSONObject("location");
				giiPlace.latitute = placeList.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
				giiPlace.longitude = placeList.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
			
				giiPlaceList.add(giiPlace); 
			}
 
			
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return giiPlaceList;

	}
}
