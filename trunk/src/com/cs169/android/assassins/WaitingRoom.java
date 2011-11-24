package com.cs169.android.assassins;





import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class WaitingRoom extends AuthenticationListActivity {
    /** Called when the activity is first created. */
	private AssassinsApplication app;
	
	private PlayerListAdapter playerAdapter;
	Button inviteBtn, startGameBtn, cancelBtn, refreshBtn;
	Context context;
	TextView gameName;
	private static final String TAG = "Waiting Room:";
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitingroom);
        Log.v("GameList", "in updateGameList");
        context = this;
        app = (AssassinsApplication) getApplication();
        
        gameName = (TextView) findViewById(R.id.gameNameTv);
        
        String name = getIntent().getStringExtra("GameName");
        
        if(name != null){        
        	gameName.setText(name);
        }
        Log.v(TAG, "Setting up buttons.");
        
        inviteBtn = (Button) findViewById(R.id.inviteBtn);
        inviteBtn.setOnClickListener(inviteOnClickListener);
        inviteBtn.setEnabled(false);
        
        startGameBtn = (Button) findViewById(R.id.startBtn);
        startGameBtn.setOnClickListener(startGameOnClickListener);
        
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(cancelOnClickListener);
        
        refreshBtn = (Button) findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(refreshOnClickListener);

        Log.v(TAG, "Done setting up buttons.");
        Log.v(TAG, "app.getAccessToken(): "+ app.getAccessToken());
        Log.v(TAG, "app.getAccessToken(): "+ facebook.getAccessToken());
        
        String playerListURL = InGameService.BASE_URL+"game/players/?access_token="+app.getAccessToken();
        Log.v(TAG, "playerListURL: "+ playerListURL);
		String playerListJSON = InGameService.request(playerListURL, true, null);
        Log.v(TAG, "InGameService.request  = " + playerListJSON);        
        updatePlayerList();

    }
    
    
    private View.OnClickListener inviteOnClickListener = new View.OnClickListener() {
    	@Override
    	public void onClick(View v) {	    		
        	startActivity(new Intent(context, InviteScreen.class));
    	}
    };
    
    // Call server to see if the game that the user is in has changed status to "s"(started)
    // or still "w" waiting for the creator to start.
    private View.OnClickListener refreshOnClickListener = new View.OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		/*String url = InGameService.BASE_URL + "/game/info/?access_token = " 
    				+ app.getAccessToken() + "&game_id=" + app.getNewGameAdded().getGameID();
    		Log.v(TAG, "Refresh Button: url is "+ url);
    		String returnedGameInfo = InGameService.request(url, true, null);
    		Log.v(TAG, "returnedGameInfo = "+ returnedGameInfo);
    		String gameNewStatus = app.parseReturnedHTTPToString(returnedGameInfo, "status");*/
    		JSONObject gameObj = Utility.getGameInfo(app.getAccessToken(), app.getNewGameAdded().getGameID());
    		Log.v(TAG, "gameObj="+gameObj.toString());
    		
    		
    		String gameNewStatus = "";
			try {
				JSONObject gameFieldObj = gameObj.getJSONObject("fields");
				Log.v(TAG, "gameObj.getString(status) = " +  gameFieldObj.getString("status")); 
				gameNewStatus = gameFieldObj.getString("status");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
    		if(gameNewStatus.equals("s")){
    			Log.v(TAG, "Game Status has changed to Start. Starting GameActivity.");     			
    			
	    		startActivity(new Intent(context, GameActivity.class));
    		}
    		else{
    			updatePlayerList();
    		}
        	
    	}
    };
    
    private View.OnClickListener startGameOnClickListener = new View.OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		/*
    		 * Handle both the case where user come from ListOfGames or CreateGame screen:
    		 * 
    		 * + After the user click create game, call httpGet "player/info" to obtain the game_id of the player.
    		 * + Call "game/info/?access_token&game_id" to obtain the "pk" of the creator.
    		 * + Compare the "pk" of the creator with the "pk" of the user. 
    		 */
    		JSONObject player = Utility.getPlayerInfo(app.getAccessToken(), app.getUser().getUserID());
    		User user = new User();
            user.parseJSONObject(player);
            app.setUser(user);
            
            if (user.getGameID() != "null") {
                JSONObject game = Utility.getGameInfo(app.getAccessToken(), user.getGameID());
                app.getNewGameAdded().parseJSONObject(game);
            }
    		
    		
    		String gameCreatorPK = app.getNewGameAdded().getCreator();
    		Log.v(TAG, "the PK of the Creator of the Game is: "+ gameCreatorPK);
    		String userPK = app.getUser().getPK();
    		Log.v(TAG, "the PK of the user is: "+ userPK);
    		
    		// Checking if there are 3 or more players to start game
    		JSONArray playersArray = Utility.getPlayersArray(app.getAccessToken());
    		int numPlayers = playersArray.length();
    		
    		Log.v(TAG, "There are " + numPlayers + " in the game.");
    		if(numPlayers < 3){    			
    			Log.v(TAG, "You need 3 players to start the game. ");
                Toast.makeText(WaitingRoom.this, "You MUST have at least 3 players to START the game", Toast.LENGTH_SHORT).show();
                return;    			
    		}
    		else if(!gameCreatorPK.equals(userPK)){
    			Log.v(TAG, "gameCreatorPK is not equals to userPK. Can't start Game.");
                Toast.makeText(WaitingRoom.this, "You MUST be the CREATOR of this game to START", Toast.LENGTH_SHORT).show();
                return;    			
    		}
    		else {
				Log.v(TAG, "gameCreatorPK is equals to userPK. Start Game.");
        		String url= app.URL_BASE + "game/start/?access_token=" + app.getAccessToken();
				HttpPost httppost=new HttpPost(url); 
				Log.v(TAG,"urlJoinGame = "+ url);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			
		        nameValuePairs.add(new BasicNameValuePair("access_token",app.getAccessToken()));
		        Log.v("JoinGame httppost.toString(), ",httppost.toString());
		        Log.v("JoinGame:","value pairs :" +nameValuePairs.toString() );
		        try {
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					InGameService.request(url, false, httppost);
					finish();
				} catch (Exception/**UnsupportedEncodingException**/ e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				    
	    		startActivity(new Intent(context, GameActivity.class));
	    		finish();
	    		
    		}
    	}
    };
    
    private View.OnClickListener cancelOnClickListener = new View.OnClickListener() {
    	@Override
    	public void onClick(View v) {	    		
    		app.getPlayerList().clear();
    		
    		String gameCreatorPK = app.getNewGameAdded().getCreator();
    		String userPK = app.getUser().getPK();
    		
    		
    		// If user clicks cancel and the user is not the creator
    		String url = "";
    		if(!gameCreatorPK.equals(userPK)){
    			
    			url = InGameService.BASE_URL + "player/leavegame/";
 
    		}else{
    		 url = InGameService.BASE_URL + "game/cancel/";	
    		}
    		

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("access_token",app.getAccessToken()));
	        
			HttpPost httppost = new HttpPost(url);
			try {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
	        String response=InGameService.request(url,false,httppost);
    		
    		
    		

        	startActivity(new Intent(context, ListOfGamesActivity.class));
        	finish();
        }
    };
    
    public void updatePlayerList(){
    	    	
    	/*String playerListJSON = "";
    	try{
			app.setURLListGames(app.getAccessToken());
    		String playerListURL = InGameService.BASE_URL+"waitingroom/players/?game_id=";
    		playerListJSON = InGameService.request(app.getURLListGames(), true, null);
    		
    		//TODO: Convert return string playerListJSON into list of players
    		
			Log.v("GameList", "in updatePlayerList");
        	       	
        	playerAdapter = new PlayerListAdapter(this, app.getPlayerList());
            
            setListAdapter(playerAdapter);
        	Log.v("GameList", "Done setListAdapter.");
        	//myAdapter.forceReload();
        	
    	} catch(Exception e){
    		//Log.v(TAG, "Can't add game to app.getGameList()");
    		e.printStackTrace();
    	}    	
    	*/
    	
    	
    	
    	
    	String urlListOfPlayers = app.URL_BASE + "game/players/?access_token="+app.getAccessToken();
    	String returnedPlayerListString = "";
    	String firstPlayerInListName = "";
    	try{
			
			returnedPlayerListString = InGameService.request(urlListOfPlayers, true, null);
			//InputStream error = ((HttpURLConnection) connection).getErrorStream();
			firstPlayerInListName = app.parseReturnedHTTPToString(returnedPlayerListString, "name");
			if(firstPlayerInListName.equals("")){
				app.setErrorParsing(false);
				Log.v(TAG, "errorParsing Game Array.");
				// Deactivate Join Game Button
				//startGameBtn.setClickable(false);
			}
			else {	
				Log.v(TAG, "There are list of players in game: " + app.getUser().getGame() + ". Start getting players info.");
				JSONArray playerArray = new JSONArray(returnedPlayerListString);
				Log.v(TAG, "JSONArray(returnedGameListString) = "+ playerArray.toString());
				app.getPlayerList().clear();
	    		for(int i=0; i < playerArray.length(); i++){
	    			JSONObject jsonObj = playerArray.getJSONObject(i);
	    			
	    			JSONObject fieldObj = jsonObj.getJSONObject("fields");
	    			Player player = new Player();
	    			player.setPlayerStatus(fieldObj.getString("status"));
	    			player.setPlayerName(fieldObj.getString("name"));
	    			
	    			app.getPlayerList().add(player);
	    			Log.v(TAG,"done adding player: " + player.toString() + " to playerList: "+ app.getPlayerList().toString());
	    			/*User user= new User();
	    			user.setPK(jsonObj.getString("pk"));
	    			user.setStatus(fieldObj.getString("status"));
	    			user.setUserName(fieldObj.getString("name"));
	    			user.setUserID(fieldObj.getString("facebook_id"));
	    			user.setGameID(fieldObj.getString("game"));
	    			app.getUserList().add(user);*/
	    		}
			}
	
	    	Log.v(TAG, "in updatePlayerList");
	    	Log.v(TAG, "app.getGameList is "+  app.getPlayerList().toString());
	    	Log.v(TAG, "app.getGameList is "+  app.getPlayerList().get(0).toString());
	    	
	    	/*String[] names = new String[] { "Linux", "Windows7", "Eclipse", "Suse",
					"Ubuntu", "Solaris", "Android", "iPhone" };*/
			// Use your own layout and point the adapter to the UI elements which
			// contains the label
	    	/*Log.v(TAG, "names " + names);*/
	    	/*ArrayAdapter a = new ArrayAdapter<String>(this, R.layout.row_layout,
					R.id.label, names);
	    	a.notifyDataSetChanged();
	    	Log.v(TAG, "arrayAdapter toString " + a.toString());
			this.setListAdapter(a);*/
	    	
	    	
	    	
	    	playerAdapter = new PlayerListAdapter(this, app.getPlayerList());
            
            setListAdapter(playerAdapter);
	    	Log.v(TAG, "Done setListAdapter.");
	    	//myAdapter.forceReload();
    	
		} catch(Exception e){
			Log.v(TAG, "Can't add game to app.getGameList()");
			e.printStackTrace();
		}    	
	
    	
    }
}
/*
[
 {
   "pk": 8, 
   "model": "assassins.player", 
   "fields": {
     "status": "w", 
     "stats": 12, 
     "name": "An Hong Vu", 
     "facebook_id": "865250296", 
     "game": 3, 	
	"photo_file": "photos/profile/picture25221.jpeg", 
	     "target": null
	   }
 }
]*/