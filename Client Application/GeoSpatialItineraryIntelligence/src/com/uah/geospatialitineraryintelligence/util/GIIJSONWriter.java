package com.uah.geospatialitineraryintelligence.util;
/**
 * @author Archana
 * @Description  Custom for writing JSON data from class model and raw data.
 *
 */

import org.json.JSONException;
import org.json.JSONObject;

import com.uah.geospatialitineraryintelligence.datamodel.GIICoTraveler;
import com.uah.geospatialitineraryintelligence.datamodel.GIILocationPlace;
import com.uah.geospatialitineraryintelligence.datamodel.GIIPhoto;
import com.uah.geospatialitineraryintelligence.datamodel.GIIReview;
import com.uah.geospatialitineraryintelligence.datamodel.GIISearch;
import com.uah.geospatialitineraryintelligence.datamodel.GIISector;
import com.uah.geospatialitineraryintelligence.datamodel.GIISegment;
import com.google.gson.Gson;

public class GIIJSONWriter {
	public String CreateSectorPayload(GIISector giiSector)
	{
		String sectorPayload = "";
		try {
			JSONObject payload = new JSONObject();
						
			payload.put("sectorId",giiSector.SectorId);			
			payload.put("originCityId",giiSector.OriginCityId);			
			payload.put("destinationCityId", giiSector.DestinationCityId);			
			payload.put("planDate", giiSector.PlanDate);			
			payload.put("cost", giiSector.Cost);
			payload.put("distance", giiSector.Distance);
			payload.put("noOfSegments", giiSector.NoOfSegments);
			payload.put("userId", giiSector.UserId);
			payload.put("message", giiSector.message);
			payload.put("completed", giiSector.completed);
			
			for(GIISegment giiSegment : giiSector.GIISegmentList)
			{
				JSONObject jSegment = new JSONObject();
				jSegment.put("segmentId", giiSegment.SegmentId);
				jSegment.put("originCityId", giiSegment.OriginCityId);
				jSegment.put("destinationCityId",giiSegment.DestinationCityId);
				jSegment.put("startDate",giiSegment.StartDate);
				jSegment.put("endDate",giiSegment.EndDate);
				jSegment.put("distance",giiSegment.Distance);
				jSegment.put("cost",giiSegment.Cost);
				jSegment.put("sectorId",giiSegment.SectorId);
				jSegment.put("reviewId",giiSegment.ReviewId);
				jSegment.put("nextSegmentId",giiSegment.NextSegmentId);
												
				payload.accumulate("segmentList", jSegment);
			}					
			sectorPayload = payload.toString();  			  
		} catch (JSONException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sectorPayload;
	}
	
	public String CreateSearchPayload(GIISearch searchObj){
		//Using Gson library to make Json Object from sector class;
				Gson g = new Gson();
				String jsonString =  g.toJson(searchObj);
				return jsonString;
		
	}
	
	public String CreateSegmentPayload(GIISegment giiSegment){
		
		Gson g = new Gson();
		String jsonString = g.toJson(giiSegment);
		return jsonString;
	}
	public String CreateReviewPayload(GIIReview giiReview){
		
		Gson g = new Gson();
		String jsonString = g.toJson(giiReview);
		return jsonString;
	}
	public String CreateCotravelerPayload(GIICoTraveler giiCotraveler){
		
		Gson g = new Gson();
		String jsonString = g.toJson(giiCotraveler);
		return jsonString;
	}
	public String CreatePlacePayload(GIILocationPlace giiLocation){
		
		Gson g = new Gson();
		String jsonString = g.toJson(giiLocation);
		return jsonString;
	}
	public String CreatePhotoPayload(GIIPhoto giiPhoto){
		
		Gson g = new Gson();
		String jsonString;
		//return jsonString;

		JSONObject photo = new JSONObject();
		try {
			photo.put("PlaceId",giiPhoto.PlaceId);
			photo.put("UserId",giiPhoto.UserId);
			photo.put("Image",giiPhoto.Image);
			photo.put("Message",giiPhoto.Message);
			photo.put("UploadDate",giiPhoto.UploadDate);
			photo.put("PhotoId",giiPhoto.PhotoId);
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jsonString = photo.toString();
		return jsonString;
	}
}
