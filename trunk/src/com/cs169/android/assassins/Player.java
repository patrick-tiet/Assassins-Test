package com.cs169.android.assassins;

import android.R.bool;

public class Player {
	
	private String playerName;
	private String playerStatus;
	private String pk;
	
	public Player(){
		this.playerName = "";
		this.playerStatus = "";
		this.pk = "";
	}
	
	public void setPK(String a){
		this.pk = a;
	}
	public String getPK(){
		return this.pk;
	}
	
	public void setPlayerName(String name){
		this.playerName = name;
	}
	public String getPlayerName(){
		return this.playerName;
	}
	
	public void setPlayerStatus(String a){
		this.playerStatus = a;
	}
	public String getPlayerStatus(){
		return this.playerStatus;
	}
	
}
