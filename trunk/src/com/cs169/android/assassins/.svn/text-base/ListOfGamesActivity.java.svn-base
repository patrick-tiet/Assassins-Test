package com.cs169.android.assassins;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOfGamesActivity extends AuthenticationListActivity {
	
	private AssassinsApplication app;
	
	private final String TAG = "ListGames";
	
	private MyAdapter myAdapter;
	
	private Button joinGameButton;
	private Button createGameButton;
	Context context;
	
	private Game game;
    
    
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_games);
        init();
        context = this;
        //myAdapter.forceReload();
	}
	protected void onResume() {
		super.onResume();
		//init();
		//myAdapter.forceReload();
	}
    
    public void init(){
    	app = (AssassinsApplication) getApplication();
    	
    	joinGameButton = (Button) findViewById(R.id.ButtonJoinGame);
        createGameButton = (Button) findViewById(R.id.ButtonCreateGame);

        joinGameButton.setOnClickListener(joinGameOnClickListener);
        createGameButton.setOnClickListener(createGameOnClickListener);
        
        updateGameList();
        
    }
    
    // Method that get all the game info by looking at the whole string.
    // input a string that is array with many objects. get each object. In each object, extract game's status, public, etc...
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
    
    public JSONObject getJSONFieldsObject(JSONArray a, int index){    	
		JSONObject jsonObj = new JSONObject();
		JSONObject fieldsObj = new JSONObject();
		
		// If the returnHTTPString is a list:
		try {						
			jsonObj = a.getJSONObject(index);
			Log.v("LogIn", "The 'fields' obj =  "+ jsonObj.getJSONObject("fields"));
			fieldsObj = jsonObj.getJSONObject("fields");
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.v(TAG, "can't get JSONFieldObject: Game object parsing error.");
			e.printStackTrace();
		}		
		return fieldsObj;
    }
    
    //   
    
    public void updateGameList(){
    	// query the server for the list of game with accessToken
    	// Add all the games to the arrayList obj
    	
    	//TODO: check to see if ArrayAdapter<String> is better than ArrayAdapter<Game>
    	// setAdapter then????
    	
    	String returnedGameListString = "";
    	String firstGameInListName = "";
    	try{
    		Log.v(TAG, "app.getAccessToken() = "+app.getAccessToken());
    		
			app.setURLListGames(app.getAccessToken());
			
			Log.v(TAG, "1 app.getErrorParsing(): "+ app.getErrorParsing());
    		
    		returnedGameListString = InGameService.request(app.getURLListGames(), true, null);
    		//InputStream error = ((HttpURLConnection) connection).getErrorStream();
    		Log.v(TAG, "2 app.getErrorParsing(): "+ app.getErrorParsing());
    		firstGameInListName = app.parseReturnedHTTPToString(returnedGameListString, "name");
    		Log.v(TAG, "3 app.getErrorParsing(): "+ app.getErrorParsing());
    		Log.v(TAG, "firsGameInGame: "+ firstGameInListName);
    		if(firstGameInListName.equals("")){
    			app.setErrorParsing(false);
    			Log.v(TAG, "errorParsing Game Array.");
    			// Deactivate Join Game Button
    			joinGameButton.setClickable(false);
    		}
    		else {	
    			Log.v(TAG, "There are public games to join. Start getting games info.");
				JSONArray gameArray = new JSONArray(returnedGameListString);
				Log.v(TAG, "JSONArray(returnedGameListString) = "+gameArray.toString());
				app.getGameList().clear();
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
	    			app.getGameList().add(game);
	    		}
    		}

        	Log.v("GameList", "in updateGameList");
        	Log.v(TAG, "app.getGameList is "+  app.getGameList().toString());
        	Log.v(TAG, "app.getGameList is "+  app.getGameList().get(0).toString());
        	
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
        	
        	
        	
        	myAdapter = new MyAdapter(this, app.getGameList());
            
            setListAdapter(myAdapter);
        	Log.v("GameList", "Done setListAdapter.");
        	//myAdapter.forceReload();
        	
    	} catch(Exception e){
    		Log.v(TAG, "Can't add game to app.getGameList()");
    		e.printStackTrace();
    	}    	
    	
    	
    }
    
    protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		app.setNewGameAdded((Game) myAdapter.getItem(position));
		
		Log.v(TAG, "the app.getNewGameAdded is: " + app.getNewGameAdded().getGameName() + ". Creator is: " + app.getNewGameAdded().getCreator());
		
		Toast.makeText(this, "You selected: " + app.getNewGameAdded().getGameName(), Toast.LENGTH_LONG)
				.show();
	}
    

	 private View.OnClickListener joinGameOnClickListener = new View.OnClickListener() {
	    	
	    	@Override
	    	public void onClick(View v) {	    		
	        	try {
	        		String url= app.urlJoinGame + app.getAccessToken() + "&game_id=" + app.getNewGameAdded().getGameID();
					HttpPost httppost=new HttpPost(url); 
					Log.v(TAG,"urlJoinGame = "+ url);
					 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

				        nameValuePairs.add(new BasicNameValuePair("game_id",app.getNewGameAdded().getGameID()));
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
				} catch (Exception e) {
					Toast.makeText(ListOfGamesActivity.this, "Error Joining Game. You might be in this game already.", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
	        	
	        	Intent waiting = new Intent(context, WaitingRoom.class);
	        	waiting.putExtra("GameName", app.getNewGameAdded().getGameName());
	        	startActivity(waiting);
	
	    	}
	    };
	 private View.OnClickListener createGameOnClickListener = new View.OnClickListener() {
    	
    	@Override
    	public void onClick(View v) {
    		startActivity(new Intent(context, CreateGame.class));
    	}
    };
    
    
    /*
	[
	  {
	    "pk": 1, 
	    "model": "assassins.game", 
	    "fields": {
	      "status": "w", 
	      "public": true, 
	      "max_players": 10, 
	      "name": "This is the game", 
	      "creator": 1
	    }
	  }, 
	  {
	    "pk": 	2, 
	    "model": "assassins.game", 
	    "fields": {
	      "status": "w", 
	      "public": true, 
	      "max_players": 58, 
	      "name": "this is GAME", 
	      "creator": 2
	    }
	  }
	]
	*/
    

}