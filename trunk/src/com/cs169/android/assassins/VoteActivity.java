package com.cs169.android.assassins;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cs169.android.assassins.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
public class VoteActivity extends AuthenticationActivity {
    /** Called when the activity is first created. */
	int i=0;
	JSONArray array;
	String verdictID;
	String response;
	ImageView truePic,questionPic;
	TextView valid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);
        i=0;
        

        Button yesBtn = (Button) findViewById(R.id.yesbtn);        
        Button noBtn = (Button) findViewById(R.id.nobtn);
        Button bBtn = (Button) findViewById(R.id.bbtn);
        valid=(TextView) findViewById(R.id.valid);
        truePic= (ImageView) findViewById(R.id.truepic);
        questionPic= (ImageView) findViewById(R.id.picquestion);
        
        
        yesBtn.setOnClickListener(yonClickListener);
        noBtn.setOnClickListener(nonClickListener);
        bBtn.setOnClickListener(bonClickListener);
      
        String url=InGameService.BASE_URL+"ingame/getvoteinfo/?access_token="+app.getAccessToken();
        Log.v("VoteActivity", url);
        response=InGameService.request(url,true,null);
        //valid.setText(response);
       changeUI();
    }
    public void onResume(Bundle savedInstanceState) {
       
       
    }
    
    private void changeUI(){
    	  try {
 			 array=new JSONArray(response);
 			 if (array.length()>0 &&!(i>array.length())){
 				 if(i==array.length()){
 					 truePic.setImageResource(R.drawable.xxx);
 					 questionPic.setImageResource(R.drawable.xxx);
 					 valid.setText("no more votes ");
 					 
 					 
 					 return;
 				 }
 				 valid.setText("valid Kill?");
 				 JSONObject someString=new JSONObject(array.getString(i));
 				 	verdictID=someString.getString("pk");
 				 	Log.v("verdict ID", verdictID);
 					JSONObject targetJ=new JSONObject(someString.getString("fields"));
 					 Log.v("VoteActivity", targetJ.toString());
 					String q=targetJ.getString("photo_file");
 					String qUrl=InGameService.BASE_URL_MEDIA+q;
 					questionPic.setImageDrawable(InGameService.getDrawable(qUrl));
 				 String tG=targetJ.getString("target");
 				 	
 				 String url2=InGameService.BASE_URL+"player/getfromkey/?player_key="+tG+"&access_token="+app.getAccessToken();
 				 String response2=InGameService.request(url2,true,null);
 				 
 				 
 				 JSONArray array2=new JSONArray(response2);
 				 JSONObject tGJ=new JSONObject(array2.getString(0));
 				Log.v("VoteActivity","tGj:"+ tGJ.toString());
 				 JSONObject fields2=new JSONObject(tGJ.getString("fields"));
 				Log.v("VoteActivity","fields2:"+ tGJ.toString());
 				 String original=fields2.getString("photo_file");
 				 String oUrl=InGameService.BASE_URL_MEDIA+original;
 				 truePic.setImageDrawable(InGameService.getDrawable(oUrl));
 				 
 				 
 				
 			 }
 			 
 			
 		} catch (JSONException e) {
 			// TODO Auto-generated catch block
 			 			e.printStackTrace();
 		}
    	  
    	
    }
    
    
    private void castVote(boolean bool){
    	if( i==array.length()){
    		return;
    	}
    	String vote;
    	if (bool){
    		vote="yes";
    		
    	}
    	else {
    		vote= "no";
    	}
    	String url=InGameService.BASE_URL+"ingame/castvote/";
    	HttpPost httpPost=new HttpPost(url);
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("access_token",app.getAccessToken()));
		nameValuePairs.add(new BasicNameValuePair("decision",vote ));
		nameValuePairs.add(new BasicNameValuePair("verdict_id",verdictID ));
		Log.v("VoteActivity","pairs:"+ nameValuePairs.get(0));
		Log.v("VoteActivity","pairs:"+ nameValuePairs.get(1));
		Log.v("VoteActivity","pairs:"+ nameValuePairs.get(2));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.v("VoteActivity","http:"+ httpPost.toString());
		Log.v("VoteActivity","url from post:"+ url);
		InGameService.request(url, false, httpPost);
		i++;
    	changeUI();
    	
    }
    
    
    
        private View.OnClickListener yonClickListener = new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        	
        		castVote(true);
        		
        	}
        };

private View.OnClickListener nonClickListener = new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		castVote(false);
        	}
        };
private View.OnClickListener bonClickListener = new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		//startActivity(new Intent(context, EndOfContestActivity.class));
        		try {
					startActivity(new Intent( createPackageContext("com.cs169.android.assassins",CONTEXT_INCLUDE_CODE),GameActivity.class));
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        };
        
        
 }