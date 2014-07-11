package com.uah.geospatialitineraryintelligence.communication;

/**
 * @author Binaya Raj
 * @Description  HttpUrlManager manages URL for different Web Api model controllers to the server with respect to the required model.
 *
 */

public class HttpUrlManager {
	// private static final String URL = "http://192.168.1.106:8081/api/";
	// private static final String URL = "http://192.168.1.109:8081/api/";
	// private static final String URL = "http://192.168.10.113:8081/api/";
	public static final String URL = "http://10.4.26.67:8081/api/";

	private static final String LOGIN = "login";
	private static final String USER = "userinfo";
	private static final String SECTOR = "sector";
	private static final String COUNTRY = "country";
	private static final String CITY = "city";
	private static final String SEARCH = "search";
	private static final String TRAVEL = "travel";
	private static final String REVIEW = "review";
	private static final String PLACE = "place";
	private static final String COTRAVELER = "cotraveler";
	private static final String PHOTO = "photo";

	public static String GetUrl(String type) {
		String urltype = null;
		if (type == "login") {
			urltype = LOGIN;
		}
		else if (type == "user") {
			urltype = USER;
		}
		else if (type == "sector") {
			urltype = SECTOR;
		}
		else if (type == "country")
			urltype = COUNTRY;
		else if (type == "city")
			urltype = CITY;
		else if (type == "search")
			urltype = SEARCH;
		else if (type == "travel")
			urltype = TRAVEL;
		else if (type == "review")
			urltype = REVIEW;
		else if (type == "place")
			urltype = PLACE;
		else if (type == "cotraveler")
			urltype = COTRAVELER;
		else if (type == "photo")
			urltype = PHOTO;

		return URL + urltype;
	}

	public static String GetReviewUrl(String type, int Id) {
		int SectorId = 0;
		int SegmentId = 0;
		int PlaceId = 0;

		if (type.equalsIgnoreCase("sectorId"))
			SectorId = Id;
		else if (type.equalsIgnoreCase("segmentId"))
			SegmentId = Id;
		else if (type.equalsIgnoreCase("placeId"))
			PlaceId = Id;
		// url for getting review is like:
		// url/review/userid|sectorId|segmentId|placeId
		String Url = GetUrl("review") + "/" + HttpSessionClass.SessionId + "%7C" + SectorId + "%7C" + SegmentId + "%7C"
				+ PlaceId;
		return Url;
	}

}
