package com.uah.geospatialitineraryintelligence;
/**
 * @author Subodh
 * @Description  Activity for handling review of sector
 *
 */
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.uah.geospatialitineraryintelligence.communication.HttpCommunicator;
import com.uah.geospatialitineraryintelligence.communication.HttpSessionClass;
import com.uah.geospatialitineraryintelligence.communication.HttpUrlManager;
import com.uah.geospatialitineraryintelligence.datamodel.GIIPhoto;
import com.uah.geospatialitineraryintelligence.datamodel.GIIReview;
import com.uah.geospatialitineraryintelligence.datamodel.GIISector;
import com.uah.geospatialitineraryintelligence.util.GIIJSONReader;
import com.uah.geospatialitineraryintelligence.util.GIIJSONWriter;

public class ReviewSectorActivity extends Activity {

	Button btnSubmit;
	Button btnCancel;
	EditText OriginCity;
	EditText DestinationCity;
	EditText review;
	RatingBar ratingBar;
	GIISector currentSector;
	Uri imageUri;
	private static final int SELECT_PICTURE = 0;
	private ImageView imageView;
	String imageBinary;
	byte[] byteImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review_sector);
		
		 imageView = (ImageView) findViewById(R.id.image);
		
		currentSector = HttpSessionClass.getCurrentSector();

		 btnSubmit = (Button) findViewById(R.id.btnSubmit);
		 btnCancel = (Button) findViewById(R.id.dismiss);
		 OriginCity = (EditText) findViewById(R.id.originCity);
		 DestinationCity = (EditText) findViewById(R.id.destinationCity);
		 review = (EditText) findViewById(R.id.Comment);
		 ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		
		OriginCity.setText(HttpSessionClass.SessionCities.get(currentSector.OriginCityId));
		DestinationCity.setText(HttpSessionClass.SessionCities.get(currentSector.DestinationCityId));
		
		
		/**
		 * Handling Review Section
		 */
		String Url = HttpUrlManager.GetReviewUrl("sectorId", currentSector.SectorId);
		String responseText = new HttpCommunicator().GIIHttpGet(Url);		

		GIIReview reviewRespose = new GIIJSONReader().ParseReview(responseText);
		
		review.setText(reviewRespose.comment);
		ratingBar.setRating(Float.parseFloat(reviewRespose.rating + ""));
		
		/**
		 * End of Review Section Handling
		 */

		btnSubmit.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				SubmitReview();
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

	}
	
	/**
	 * Method to select picture from Gallery
	 * @param view
	 */
	  public void pickPhoto(View view) { 
	    	//TODO: launch the photo picker
	    	Intent intent = new Intent();
	    	 intent.setType("image/*");
	    	 intent.setAction(Intent.ACTION_GET_CONTENT);
	    	 startActivityForResult(Intent.createChooser(intent,
	    	 "Select Picture"), SELECT_PICTURE);	    	
	    }
	    
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	super.onActivityResult(requestCode, resultCode, data);
	    	if(requestCode == SELECT_PICTURE && resultCode == RESULT_OK && null != data) {
	    	imageUri =	data.getData();
	    		
	    		 System.out.println("Current image Path is ----->" + imageUri);
	    		 imageView.setImageURI(imageUri); // Sets selected image in the imageView
	    		 
	    		 
	    		 BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable(); // Gets the image
	    		 Bitmap bmap = drawable.getBitmap();
	    		 
	    		 ByteArrayOutputStream bos = new ByteArrayOutputStream();  
	    		 bmap.compress(CompressFormat.PNG,100,bos); 
	    		 byteImage = bos.toByteArray(); // Converts image to binary bytes.
	    		 imageBinary = Base64.encodeBytes(byteImage);  // Encodes the bytes of image to base64encoding	 
	    		 
	    	} 
	    }	
	    
	    /**
	     * Uploads the selected image to the server.
	     * @param view
	     */
	    public void uploadPhoto(View view) {
	    	try {
	    		GIIPhoto photo = new GIIPhoto();
	    		photo.PlaceId = currentSector.OriginCityId;
	    		photo.UserId = Integer.parseInt(HttpSessionClass.SessionId);
	    		photo.Image = imageBinary;
	    		photo.UploadDate = "2014-04-16";
	    		
	    		String responseText = new HttpCommunicator().GIIHttpPost(HttpUrlManager.GetUrl("photo") , new GIIJSONWriter().CreatePhotoPayload(photo));
	    		photo = new GIIJSONReader().ParsePhoto(responseText);
	    		if(photo.Message.equalsIgnoreCase("success"))
	    		{
	    			Toast.makeText(this, "Photo Upload Ready!", Toast.LENGTH_SHORT).show();
	    			Thread.sleep(1000);
	    		}
	    		
	    		
			} catch (Exception e) { 
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plan, menu);
		return true;
	}

	public void SubmitReview() {		

		/**
		 * Section for sending Review of the segment to the server
		 */
		GIIReview reviewObj = new GIIReview();
		reviewObj.segmentId = 0;
		reviewObj.comment = review.getText().toString();
		reviewObj.rating = ratingBar.getRating();
		reviewObj.placeId = 0;
		reviewObj.sectorId =currentSector.SectorId;
		reviewObj.userId = Integer.parseInt(HttpSessionClass.SessionId);
		reviewObj.message = "";

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		// // get current date time with Date()
		Date date = new Date();
		reviewObj.reviewDate = dateFormat.format(date);

		String responseText = new HttpCommunicator().GIIHttpPost(HttpUrlManager.GetUrl("review"),
				new GIIJSONWriter().CreateReviewPayload(reviewObj));
		// .
		GIIReview reviewRespose = new GIIJSONReader().ParseReview(responseText);
		if (reviewRespose.message.equalsIgnoreCase("success")) {

			Toast.makeText(this, "Current Itinerary Sector Review Updated!", Toast.LENGTH_SHORT).show();
		}
		
		finish(); //To close the activity
	}


}
