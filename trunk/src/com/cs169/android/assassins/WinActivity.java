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
public class WinActivity extends AuthenticationActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win);
        Button bBtn = (Button) findViewById(R.id.LoGBtn);
       
	
       
       
      
        bBtn.setOnClickListener(bonClickListener);
      
    }
    
   
private View.OnClickListener bonClickListener = new View.OnClickListener() {
        	
	
    	@Override
    	public void onClick(View v) {
    		//startActivity(new Intent(context, EndOfContestActivity.class));
    		try {
				startActivity(new Intent( createPackageContext("com.cs169.android.assassins",CONTEXT_INCLUDE_CODE),ListOfGamesActivity.class));
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
    	
    	}
};
}
    
    
