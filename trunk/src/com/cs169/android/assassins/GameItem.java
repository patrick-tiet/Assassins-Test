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


public class GameItem extends RelativeLayout{
	
	
	private TextView tvStatus;
	private TextView tvGameName;
	private TextView tvPublic;
	private TextView tvCreator;
	private TextView tvMaxPlayer;
	private Game game;
	
	public GameItem(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public void addGame(Game game) {
		
		
		
		tvStatus = (TextView) findViewById(R.id.textViewGameStatus);
		tvStatus.setFocusable(false);
		tvStatus.setFocusableInTouchMode(false);
		
		tvGameName = (TextView) findViewById(R.id.textViewGameName);
		tvGameName.setFocusable(false);
		tvGameName.setFocusableInTouchMode(false);
		
		tvPublic = (TextView) findViewById(R.id.textViewGamePublic);
		tvPublic.setFocusable(false);
		tvPublic.setFocusableInTouchMode(false);
		
		tvCreator = (TextView) findViewById(R.id.textViewGameCreator);
		tvCreator.setFocusable(false);
		tvCreator.setFocusableInTouchMode(false);
		
		tvMaxPlayer = (TextView) findViewById(R.id.textViewGameMaxPlayer);
		tvMaxPlayer.setFocusable(false);
		tvMaxPlayer.setFocusableInTouchMode(false);
		
		
		
		Log.v("ASA", "PlaylistItem: addGame, start");
		this.game = game;
		
		Log.v("ASA", "PlaylistItem: album - "+game.getCreator());
		tvCreator.setText(game.getCreator());		
		Log.v("ASA", "PlaylistItem: album - "+game.getGameName());
		tvGameName.setText(game.getGameName());
		Log.v("ASA", "PlaylistItem: album - "+game.getMaxPlayers());
		tvMaxPlayer.setText(game.getMaxPlayers());
		Log.v("ASA", "PlaylistItem: album - "+game.getPublicGame());
		tvPublic.setText(game.getPublicGame());
		Log.v("ASA", "PlaylistItem: album - "+game.getStatus());
		tvStatus.setText(game.getStatus());
		
	}

	public Game getGame() {
		return game;
	}
	
	protected void onFinishInflate(){
		super.onFinishInflate();
	}
	
}
