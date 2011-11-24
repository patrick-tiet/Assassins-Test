package com.cs169.android.assassins;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class TargetActivity extends AuthenticationActivity {
    /** Called when the activity is first created. */
	//Facebook facebook= new Facebook("281380471893391");
	private SharedPreferences mPrefs;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
       
        setContentView(R.layout.target);
        Button bBtn = (Button) findViewById(R.id.bbtn);
        ImageView target=(ImageView)findViewById(R.id.targetpic);
        TextView targetName=(TextView)findViewById(R.id.targetname);
        
        final AssassinsApplication app=new AssassinsApplication();
        super.onCreate(savedInstanceState);
        User user=app.getUser();
       //TODO change to targetID after testing is done!!
        Log.v("target activity", "called request for this address:" +InGameService.BASE_URL+"player/info/?access_token="+app.getAccessToken()+"&user_id="+app.getUser().getUserID());

       
       // String url=InGameService.BASE_URL+"player/info/?access_token="+app.getAccessToken()+"&user_id="+app.getUser().getTargetID()  ; 
        String url=InGameService.BASE_URL+"player/info/?access_token="+app.getAccessToken()+"&user_id="+app.getUser().getUserID();
		//String url=InGameService.BASE_URL+"player/info/?user_id=646897797&access_token=AAAECDlSRSoQBALlkRZCEJHHNk6rwu6nQ7X7cPOryZBJIjtT2M5NNdWi16ecwE1zQiNEajZBq0ZCmXiVuLltdhQydDQEqmfGb1TS3KKsHrAZDZD";

       String response=InGameService.request(url,true,null);
       Log.v("TargetActivity", " valid facebook token ?"+ facebook.isSessionValid());
       Log.v("TargetActivity", " facebook token "+ app.getAccessToken());
       Log.v("TargetActivity", " target is: "+ (""==app.getUser().getTargetID()));
       
      if (!facebook.isSessionValid()){
    	   targetName.setText("can't connect to server :(");
      }
      
   /**  else if (app.getUser().getTargetID()==""||app.getUser().getTargetID()==null){
    	  
   	   targetName.setText("no target assigned :(");
     }**/
        else{
    	  
       
        try {
        
			JSONArray array=new JSONArray(response);
			JSONObject someString=new JSONObject(array.getString(0));
			JSONObject myJ=new JSONObject(someString.getString("fields"));
			String myTarget=myJ.getString("target");
			//JSONArray array=json.getJSONArray("fields");
			String url2=InGameService.BASE_URL+"player/getfromkey/?access_token="+app.getAccessToken()+"&player_key="+myTarget; 
		
			 Log.v("TargetActivity", " url2: "+ url2);

			String response2=InGameService.request(url2,true,null);
			JSONArray array2=new JSONArray(response2);
			 Log.v("TargetActivity", " response2: "+ response2.toString());
	
			JSONObject someString2=new JSONObject(array2.getString(0));
			JSONObject targetJ=new JSONObject(someString2.getString("fields"));
			Log.v("TargetActivity", " targetJ: "+ targetJ.toString());
			//String tName=targetJ.getString("name");
			// targetName.setText(tName);
			 
			String tPic=targetJ.getString("photo_file");
			Drawable targetDrawable= InGameService.getDrawable(InGameService.BASE_URL_MEDIA+tPic);
			target.setImageDrawable(targetDrawable);
			targetName.setText(targetJ.getString("name"));
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
        bBtn.setOnClickListener(bonClickListener);
       // killBtn.setOnClickListener(konClickListener);
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