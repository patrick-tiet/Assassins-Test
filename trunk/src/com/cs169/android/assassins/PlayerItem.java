package com.cs169.android.assassins;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PlayerItem extends RelativeLayout{
	
	
	private TextView playerNameTv;
	private Player player;
	
	public PlayerItem(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public void addPlayer(Player player) {
		
		playerNameTv = (TextView) findViewById(R.id.playerNameTv);
		playerNameTv.setFocusable(false);
		playerNameTv.setFocusableInTouchMode(false);
		
		Log.v("ASA", "PlaylistItem: addGame, start");
		this.player = player;
		
		playerNameTv.setText(player.getPlayerName());
		
	}

	public Player getPlayer() {
		return player;
	}
	
	protected void onFinishInflate(){
		super.onFinishInflate();
	}
	
}