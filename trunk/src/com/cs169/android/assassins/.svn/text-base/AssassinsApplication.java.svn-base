package com.cs169.android.assassins;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cs169.android.assassins.R;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;




import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Intent.*;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class AssassinsApplication<T> extends Application {
    /** Called when the activity is first created. */

	public Intent myIntent;
	
	private SharedPreferences myPrefs;
    Context context;
    //Activity currActivity;
    Facebook facebook = new com.facebook.android.Facebook("281380471893391");
    String FILENAME = "assassins_data";
	
	private String TAG = "Application";
	private ArrayList<Game> gameList = new ArrayList<Game>();
	private ArrayList<User> userList = new ArrayList<User>();
	
	private ArrayList<Player> playerList = new ArrayList<Player>();
	public static Game newGameAdded = new Game();
	public static User user = new User();
	public static String gameID= "";
	public static String userID = "";
	
	public static String debug = "";
	public static boolean errorParsing = false;
	
	//public static final String ACCESS_TOKEN = "";
	public static String accessToken = "";
	
	public static final String URL_BASE = "http://107.20.135.212:61000/assassins/"; 
	
	/*List available games: assassins/game/games/
    GET request args: (No arguments)*/
	
	public static String urlListGames = URL_BASE + "game/games/?access_token=" ;

	//public static final String URL_LIST_GAMES = URL_BASE + "game/games/?access_token=" + ACCESS_TOKEN;
	
	/*Join game: assassins/player/joingame/
    POST request args: access_token, game_id*/
	public static final String urlJoinGame = URL_BASE + "player/joingame/?access_token=";
	
	/*Get user info: assassins/player/getuserinfo/
    GET request args: access_token*/
	public static String urlPlayerInfo = URL_BASE + "player/info/?access_token=" ;
	
	
	
	@Override
    public void onCreate() {
        super.onCreate();
        
        
    }
	// Added
	public void setUserList(ArrayList<User> a){
		Collections.copy(this.userList, a);
	}
	public ArrayList<User> getUserList(){
		return this.userList;
	}
	//
	public void setGameList(ArrayList<Game> a){
		Collections.copy(this.gameList, a);
	}
	public ArrayList<Game> getGameList(){
		return this.gameList;
	}
	
	public void setPlayerList(ArrayList<Player> a){
		Collections.copy(this.playerList, a);
	}
	public ArrayList<Player> getPlayerList(){
		return this.playerList;
	}
	
	public void setNewGameAdded(Game g){
		this.newGameAdded = g;
	}
	public Game getNewGameAdded(){
		return this.newGameAdded;
	}
    
	public void setUser(User u){
		this.user = u;
	}
	public User getUser(){
		return this.user;
	}
	
	
	
	public void setAccessToken(String a){
		this.accessToken = a;
	}
	public String getAccessToken(){
		return this.accessToken;
	}
	
	public void setUserID(String a){
		this.userID =a;
	}
	public String getUserID(){
		return this.userID;
	}
	
	
	/* SET and GET URL methods */
	public void setURLPlayerInfo(String accessToken, String userID){
		
		this.urlPlayerInfo = URL_BASE + "player/info/?access_token="+ accessToken + "&user_id=" + userID;
	}
	public String getURLPlayerInfo(){
		return this.urlPlayerInfo;
	}
	
	public void setURLListGames(String accessToken){		
		this.urlListGames = URL_BASE + "game/games/?access_token=" + accessToken;
	}
	public String getURLListGames(){
		return this.urlListGames;
	}
	
	
	/* DEBUG */
	public void setDebugString(String d){
		this.debug = d;
	}
	public String getDebugString(){
		return this.debug;
	}
	
	public void setErrorParsing(boolean a){
		this.errorParsing = a;
	}
	public boolean getErrorParsing(){
		return this.errorParsing;
	}
	
	// Get JSON array from HTTPstring
	public JSONArray getJSONArray(String returnedHTTPString){
    	String firstChar = Character.toString(returnedHTTPString.charAt(0));
		JSONArray jsonArray = new JSONArray();
				
		// If the returnHTTPString is a list:
		try {
			if(firstChar.equals("[")){		        
				jsonArray = new JSONArray(returnedHTTPString);
			}
		} catch(JSONException e){
			Log.v(TAG, "Can't convert HTTPstring to a JSONArray.");
		}
		return jsonArray;
					
    }
	
	
	// Get JSON object from a JSONArray with an index. 
    public JSONObject getJSONFieldsObject(JSONArray a, int index){    	
		JSONObject jsonObj = new JSONObject();
		JSONObject fieldsObj = new JSONObject();
		
		// If the returnHTTPString is a list:
		try {						
			jsonObj = a.getJSONObject(index);
			Log.v(TAG, "The 'fields' obj =  "+ jsonObj.getJSONObject("fields"));
			fieldsObj = jsonObj.getJSONObject("fields");
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.v(TAG, "can't get JSONFieldObject: Game object parsing error.");
			e.printStackTrace();
		}		
		return fieldsObj;
    }
	
    
	
	
	// Helper Method to Parse the string returned by calling HTTPrequest() to a String with a given Key
	public String parseReturnedHTTPToString(String returnedHTTPString, String jsonObjKey){
		
		String firstChar = Character.toString(returnedHTTPString.charAt(0));
		JSONArray jsonArray = null;
		String value = "";
		JSONObject jsonObj = null;
		JSONObject fieldsObj = null;
		
		// If the returnHTTPString is a list:
		if(firstChar.equals("[")){
			
	        try {
				jsonArray = new JSONArray(returnedHTTPString);
				Log.v(TAG, "json.toString() =  "+ jsonArray.toString());
				jsonObj = jsonArray.getJSONObject(0);
				Log.v(TAG, "The 'fields' obj =  "+ jsonObj.getJSONObject("fields"));
				fieldsObj = jsonObj.getJSONObject("fields");
				value = fieldsObj.getString(jsonObjKey);
				Log.v(TAG, "the return value is "+ value);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				this.setErrorParsing(true);
				Log.v(TAG, "errorParsing is true.");
				e.printStackTrace();
			}
						
		}
		// if the returnHTTPString is an obj from the FACEBOOK GRAPH API:
		else{
			try {
				jsonObj = new JSONObject(returnedHTTPString);
				Log.v(TAG, "json.toString() =  "+ jsonObj.toString());
				
				//fieldsObj = jsonObj.getJSONObject("fields");
				value = jsonObj.getString(jsonObjKey);
				Log.v(TAG, "the return value is "+ " id = " + value);
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				this.setErrorParsing(true);
				Log.v(TAG, "errorParsing is true.");
				e.printStackTrace();
			}
			
		}
		
		
		Log.v(TAG, "Return ("+ jsonObjKey +") is: "+value );
		return value;
		
	}
	
	
	/*
	 * Update Game List 
	 * 
	 */
	public void updateGameList(){
    	// query the server for the list of game with accessToken
    	// Add all the games to the arrayList obj
    	
    	//TODO: check to see if ArrayAdapter<String> is better than ArrayAdapter<Game>
    	// setAdapter then????
    	
    	String returnedGameListString = "";
    	String firstGameInListName = "";
    	try{
			this.setURLListGames(facebook.getAccessToken());
    		
    		returnedGameListString = InGameService.request(this.getURLListGames(), true, null);
    		//InputStream error = ((HttpURLConnection) connection).getErrorStream();
    		firstGameInListName = this.parseReturnedHTTPToString(returnedGameListString, "name");
    		if(this.getErrorParsing() == true 
    				|| (firstGameInListName.equals(""))){
    			this.setErrorParsing(false);
    			Log.v(TAG, "errorParsing Game Array.");
    			// Deactivate Join Game Button
    			// joinGameButton.setClickable(false);
    		}
    		else if(this.getErrorParsing() == false){	
    			Log.v(TAG, "There are public games to join. Start getting games info.");
				JSONArray gameArray = new JSONArray(returnedGameListString);
				Log.v(TAG, "JSONArray(returnedGameListString) = "+gameArray.toString());
	    		for(int i=0; i < gameArray.length(); i++){
	    			JSONObject jsonObj = gameArray.getJSONObject(i);
	    			
	    			JSONObject fieldObj = jsonObj.getJSONObject("fields");
	    			Game game = new Game();
	    			game.setGameID(jsonObj.getString("pk"));
	    			game.setStatus(fieldObj.getString("status"));
	    			game.setGameName(fieldObj.getString("name"));
	    			game.setPublicGame(fieldObj.getString("public"));   			
	    			game.setCreator(fieldObj.getString("creator"));
	    			game.setMaxPlayers(fieldObj.getString("max_players"));
	    			this.getGameList().add(game);
	    		}
    		}
 
    		
        	Log.v("GameList", "in updateGameList");
        	Log.v(TAG, "app.getGameList is "+  this.getGameList().toString());
        	Log.v(TAG, "app.getGameList is "+  this.getGameList().get(0).toString());
        	
        	
        	
    	} catch(Exception e){
    		Log.v(TAG, "Can't add game to app.getGameList()");
    		e.printStackTrace();
    	}    	
    	
    	
    }
	
	
	
	
}