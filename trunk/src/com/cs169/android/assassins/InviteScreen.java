package com.cs169.android.assassins;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InviteScreen extends Activity {
    /** Called when the activity is first created. */
	
	Button facebookInviteBtn, phoneInviteBtn, emailInviteBtn, inviteBtn;
	Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitescreen);
        
        facebookInviteBtn = (Button) findViewById(R.id.facebookInviteBtn);
        phoneInviteBtn = (Button) findViewById(R.id.phoneInviteBtn);
        emailInviteBtn = (Button) findViewById(R.id.emailInviteBtn);
        inviteBtn = (Button) findViewById(R.id.inviteBtn);
    } 
    
    
    private View.OnClickListener onClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			/***case R.id.facebookInviteBtn:				
				//TODO: Add using Facebook
				break;
			case R.id.phoneInviteBtn:
				//TODO: Add using phone
				break;
			case R.id.emailInviteBtn:
				//TODO: Add using email
				break;
			case R.id.inviteBtn:
				//TODO: Invite added people
				break;**/ 
			}
		}
	};
}