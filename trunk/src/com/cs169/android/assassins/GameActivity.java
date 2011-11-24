package com.cs169.android.assassins;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.cs169.android.assassins.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.*;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class GameActivity extends AuthenticationActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.game_screen);
        
        checkStatus();
        Button targetBtn = (Button) findViewById(R.id.targetbtn);        
        Button voteBtn = (Button) findViewById(R.id.votebtn);
        Button nfBtn = (Button) findViewById(R.id.nfbtn);
        Button killBtn = (Button) findViewById(R.id.killbtn);
        TextView txt= (TextView) findViewById(R.id.gstxt);
       
         
        
        targetBtn.setOnClickListener(tonClickListener);
        voteBtn.setOnClickListener(vonClickListener);
        nfBtn.setOnClickListener(nonClickListener);
        killBtn.setOnClickListener(konClickListener);
        
        
       
       /** InputStream i=InGameService.getInputStreamFromUrl(InGameService.BASE_URL, true);
       
        try {
			String myString =InGameService.inputStreamAsString(i);
	        //txt.setText(myString);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}**/
        
 }
    
    @Override
    public void onBackPressed() {

       return;
    }
public void checkStatus(){
	String s="";
    String url=InGameService.BASE_URL+"player/info/?access_token="+app.getAccessToken()+"&user_id="+app.getUser().getUserID();
    TextView status=(TextView) findViewById(R.id.status);
    
	String response=InGameService.request(url, true, null);
	JSONArray array=null;
	JSONObject object=null;
	try{ array= new JSONArray(response);
	 object=new JSONObject(array.getString(0));
	}
	catch (JSONException e){
    status.setText("Couldn't retreive status")	;	
    return;
	}
	
	try {
		JSONObject fields=new JSONObject(object.getString("fields"));
		s= fields.getString("status");
		if(s.equals("n")){
			try {
				startActivity(new Intent( createPackageContext("com.cs169.android.assassins",CONTEXT_INCLUDE_CODE),ListOfGamesActivity.class));
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if(s.equals("w")){
			try {
				startActivity(new Intent( createPackageContext("com.cs169.android.assassins",CONTEXT_INCLUDE_CODE),WaitingRoom.class));
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if (s.equals("a")){
				
				did_u_win(fields.getString("target"),"a");
					status.setText("Status:Alive");
					status.setTextColor(Color.GREEN);
				
				
				
		}
		else if (s.equals("d")){
			status.setText("Status:Dead");
			status.setTextColor(Color.GRAY);
		}
		else if (s.equals("v")){
			int i =0;
			did_u_win(fields.getString("target"),"v");
			status.setText("Status:Alive");
			status.setTextColor(0xbbff0099);
		}
			
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}

 private Map arraygetItem(int i) {
	// TODO Auto-generated method stub
	return null;
}
public void did_u_win(String t, String status) throws JSONException{
	String target= t;//fields.getString("target");
	String url2=InGameService.BASE_URL+"player/getfromkey/?access_token="+app.getAccessToken()+"&player_key="+target;
	String response2=InGameService.request(url2,true,null);
	JSONArray array2;
	
		array2 = new JSONArray(response2);
		
	
	 Log.v("GameActivty", " response2: "+ response2.toString());

	JSONObject someString2=new JSONObject(array2.getString(0));
	JSONObject targetJ=new JSONObject(someString2.getString("fields"));
	Log.v("TargetActivity", " targetJ: "+ targetJ.toString());
	//String tName=targetJ.getString("name");
	// targetName.setText(tName);
	 
	
	String tname=targetJ.getString("name");
	if (tname.equals(app.getUser().getUserName())&&(status.equals("v")||status.equals("a"))){
		//won game 
		try {
			startActivity(new Intent( createPackageContext("com.cs169.android.assassins",CONTEXT_INCLUDE_CODE),WinActivity.class));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
public void changeColors(){
Button killBtn = (Button) findViewById(R.id.killbtn);

	 killBtn.setTextColor(Color.GRAY);
	 killBtn.setBackgroundColor(0xffff0000);
 }
 int CAMERA_PIC_REQUEST = 2;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    if( requestCode == CAMERA_PIC_REQUEST)
	    {
	    //  data.getExtras()
	        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
	        //ImageView image =(ImageView) findViewById(R.id.PhotoCaptured);
	       // image.setImageBitmap(thumbnail);
	    }
	    else 
	    {
	        Toast.makeText(GameActivity.this, "Picture NOt taken", Toast.LENGTH_LONG);
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}

 private View.OnClickListener nonClickListener = new View.OnClickListener() {
    	
    	@Override
    	public void onClick(View v) {
    		//startActivity(new Intent(context, EndOfContestActivity.class));
            //setContentView(R.layout.newsfeed);
        	try {
				startActivity(new Intent( createPackageContext("com.cs169.android.assassins",CONTEXT_INCLUDE_CODE),NFActivity.class));
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}

    	}
    };
 private View.OnClickListener konClickListener = new View.OnClickListener() {
	 int CAMERA_PIC_REQUEST = 2;
	 public void onClick(View v) {
		 changeColors();
		
		// Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
         // request code

         //startActivityForResult(cameraIntent, this.CAMERA_PIC_REQUEST);
	    	try {
				startActivity(new Intent( createPackageContext("com.cs169.android.assassins",CONTEXT_INCLUDE_CODE),KillActivity.class));
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}

		}
    };
    private View.OnClickListener vonClickListener = new View.OnClickListener() {
    	
    	@Override
    	public void onClick(View v) {
    	
				try {
					startActivity(new Intent( createPackageContext("com.cs169.android.assassins",CONTEXT_INCLUDE_CODE),VoteActivity.class));
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
			
           // setContentView(R.layout.vote);

    	}
    };
private View.OnClickListener tonClickListener = new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		//startActivity(new Intent(context, EndOfContestActivity.class));
        //setContentView(R.layout.target);
    	try {
			startActivity(new Intent( createPackageContext("com.cs169.android.assassins",CONTEXT_INCLUDE_CODE),TargetActivity.class));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

	}
};

}