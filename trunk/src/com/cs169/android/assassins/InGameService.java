package com.cs169.android.assassins;

import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;

public class InGameService  {
	static String TAG="assassins";
	protected static final String BASE_URL_0="http://107.20.135.212:61000/";
	protected static final String BASE_URL=BASE_URL_0+"assassins/";
	protected static final String BASE_URL_MEDIA=BASE_URL_0+"media/";
	
			//"http://107.20.135.212:61000/assassins/";

     //String fbaccesstoken = "AAAD8QU48IbgBAIHJvMR07QTajVWhnDNPrnlhjApfVF3YsQsIsOz16mAiaIcGoxIPT0ZBE7Kc7xU2TQRwdBaAWb2uJXZBjjuzi0UcqJZAQZDZD";
	
	public static String request(String url, boolean get, HttpPost httpPost){
		
	Log.v("InGameService", "url = " + url);	
	
    if (get){
	InputStream i=InGameService.getInputStreamFromUrl(url);
    
    try {
    	if (i==null) {
    		return null;
    	}
		String myString =InGameService.inputStreamAsString(i);
        //txt.setText(myString);
		return myString;
		
	} catch (IOException e) {
		
		e.printStackTrace();
		return null;
	}
    }
    else {
    	 InputStream i= postInputStreamtoUrl(url, httpPost);
    		String myString=null;
			try {
				myString = InGameService.inputStreamAsString(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //txt.setText(myString);
    		return myString;
    	 
    }
	 
	}
	public static String inputStreamAsString(InputStream stream)
			throws IOException {
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = br.readLine()) != null) {
		
			sb.append(line + "\n");
			Log.v(TAG+ "input stream",line);
			}

			br.close();
			return sb.toString();
			}
	
	// get== true if you want a to make a GET request ,
	//get==false if you want a POST request
	public static InputStream getInputStreamFromUrl(String url) {
		InputStream content = null;
		
		try {

			Log.v("getInputStreamFromUrl f","1 + url = " + url);


			HttpGet httpGet = new HttpGet(url);
			
			
			Log.v("getInputStreamFromUrl","2 httpGet obj  = "+ httpGet.toString());

			HttpClient httpclient = new DefaultHttpClient();
			// Execute HTTP Get Request
			//Log.v("here","3");
			HttpResponse response = httpclient.execute(httpGet);
			Log.v(TAG+ "getInputFromURL stream","response = "+ response.toString());
			content = response.getEntity().getContent();
                } catch (Exception e) {
			//handle the exception !
                	e.printStackTrace();
		}
		return(InputStream) content;
}

	public static InputStream postInputStreamtoUrl(String url,HttpPost httpPost) {
		InputStream content = null;
			try {
          		HttpClient httpclient = new DefaultHttpClient();
			//HttpPost httpPost = new HttpPost(url);
			//List nameValuePairs = new ArrayList(1);
                        //this is where you add your data to the post method
                      //  nameValuePairs.add(new BasicNameValuePair(
			//"name", "anthony"));
			//httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httpPost);
			content = response.getEntity().getContent();
		        return(InputStream) content;
	        
			} catch (IllegalStateException e) {
				System.err.println( e.toString());
				e.printStackTrace();
			
			
		} catch (IOException e) {
				e.printStackTrace();
			}
		return content;
}
	
	static Drawable getDrawable( String url) {

            HttpGet httpRequest = new HttpGet(url);
   

     HttpClient httpclient = new DefaultHttpClient();
     HttpResponse response = null;
	try {
		 response = (HttpResponse) httpclient.execute(httpRequest);
	} catch (ClientProtocolException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

     HttpEntity entity = response.getEntity();
     BufferedHttpEntity bufHttpEntity = null;
	try {
		bufHttpEntity = new BufferedHttpEntity(entity);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
     InputStream instream = null;
	try {
		instream = bufHttpEntity.getContent();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
     Drawable d=Drawable.createFromStream(instream,"src");

		return d;
		/**try {
			
			Log.v("get drawable ingame service + ",url.toString());
			InputStream is = (InputStream) fetch(url);
			Drawable d = Drawable.createFromStream(is, "src");
			return d;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}**/
	}

	public  static Object fetch(String address) throws MalformedURLException,IOException {
		URL url = new URL(address);
		Object content = url.getContent();
		Log.v("fetch ingame service + ",content.toString());
		return content;
	}
}

	

