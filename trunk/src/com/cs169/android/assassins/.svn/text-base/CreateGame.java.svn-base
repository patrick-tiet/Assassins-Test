package com.cs169.android.assassins;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateGame extends AuthenticationActivity {
    /** Called when the activity is first created. */
	
	Button createGameBtn;
	Context context;
	
	EditText gameNameEt, maxNumEt, locationEt;
	Spinner visibilitySp;
	String gameName, maxNum, location, privacy;
	//Toast toast;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategamescreen);
        context = this;
        
        createGameBtn = (Button) findViewById(R.id.createGameBtn);
        createGameBtn.setOnClickListener(createGameOnClickListener);
        
        gameNameEt = (EditText) findViewById(R.id.gameNameEt);
        maxNumEt = (EditText) findViewById(R.id.maxNumEt);
        locationEt = (EditText) findViewById(R.id.locationEt);
        visibilitySp = (Spinner) findViewById(R.id.visibilitySpinner);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(  
                this,  
                android.R.layout.simple_spinner_item,  
                new String[] {"Public", "Private"});  
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        visibilitySp.setAdapter(adapter);
    }
    
    private View.OnClickListener createGameOnClickListener = new View.OnClickListener() {
    	
    	@Override
    	public void onClick(View v) {	    		
    		
    		gameName = gameNameEt.getText().toString();
    		maxNum = maxNumEt.getText().toString();
    		location = locationEt.getText().toString();
    		    		
    		if(TextUtils.isEmpty(gameName)){
    			Toast.makeText(context, "Please fill in the game name.", Toast.LENGTH_SHORT).show();
    		}
    		else if(TextUtils.isEmpty(maxNum)){
    			Toast.makeText(context, "Please fill in the max players.", Toast.LENGTH_SHORT).show();
    		}
    		else if(TextUtils.isEmpty(location)){
    			Toast.makeText(context, "Please fill in the location.", Toast.LENGTH_SHORT).show();
    		}
    		else{
    			int pos = visibilitySp.getSelectedItemPosition();
    			privacy = "True";
    			if(pos == 0){
    				privacy = "True";
    			}
    			else if(pos == 1){
    				privacy = "False";
    			}
    			else{
    				Toast.makeText(context, "Please choose game privacy.", Toast.LENGTH_SHORT).show();
    			}
    			
    			//access_token, name, maxplayers, public (‘True’ or ‘False’)
    			
    			String url=InGameService.BASE_URL+"game/create/";
    			
    			
    			//We need to refactor POST and GET to utility methods, this is all boilerplate code
    			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("name",gameName));
		        nameValuePairs.add(new BasicNameValuePair("access_token",app.getAccessToken()));
		        nameValuePairs.add(new BasicNameValuePair("maxplayers",maxNum));
		        nameValuePairs.add(new BasicNameValuePair("public",privacy));
    	        
    			//TODO: The following request throws an error
    			
    			HttpPost httppost = new HttpPost(url);
    			try {
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
    			
    	        String response=InGameService.request(url,false,httppost);
    	        Log.v("Response", response);
    	        
    	        Intent waitingIntent = new Intent(context, WaitingRoom.class);
    	        waitingIntent.putExtra("Game Name", gameName);
    	        startActivity(waitingIntent);
    		}
    	}
    };
}