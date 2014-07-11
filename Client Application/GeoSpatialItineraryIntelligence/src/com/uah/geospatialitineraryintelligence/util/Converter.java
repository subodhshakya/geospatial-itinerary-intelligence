package com.uah.geospatialitineraryintelligence.util;

import java.io.InputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;

public class Converter {
	public static final String url = "http://192.168.1.109/api";
	
	public static Drawable LoadImageFromWebOperations(String url) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "image_google");
	        return d;
	    } catch (Exception e) {
	        return null;
	    }
	}
	

}

