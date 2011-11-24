package com.cs169.android.assassins;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cs169.android.assassins.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class NFActivity extends AuthenticationActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsfeed);
        Button bBtn = (Button) findViewById(R.id.bbtn);
       TextView nfTxt=(TextView) findViewById(R.id.nftxt);
      // String newsfeed=InGameService.request(InGameService.BASE_URL +"ingame/newsfeed/$", true); //"assassins/ingame/feed/",true);
       AssassinsApplication app=new AssassinsApplication();
       User user=app.getUser();
       String nf=InGameService.request(InGameService.BASE_URL+"game/feed/?skipnum=0&access_token="+app.getAccessToken(), true,new HttpPost());
       String newsfeed="";
       try {
		JSONArray array= new JSONArray(nf);
		
		for (int i=0;i<array.length();i++){
			JSONObject curr=array.getJSONObject(i);
			JSONObject fields=curr.getJSONObject("fields");
			String currText=fields.getString("text");
			String newline="\n\n";
			newsfeed=newsfeed+newline+"*"+currText;
			
		}
		
		nfTxt.setText(newsfeed);
	} catch (JSONException e) {
		nfTxt.setText("Connection Error");
		return ;
	}
       
       
      
        bBtn.setOnClickListener(bonClickListener);
      
    }
    
   
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