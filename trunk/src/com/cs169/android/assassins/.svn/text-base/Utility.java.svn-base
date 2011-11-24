package com.cs169.android.assassins;

import android.os.Bundle;
import com.facebook.android.Facebook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;

public class Utility {
	
	public static JSONArray getPlayersArray(String accessToken) {
        String url = InGameService.BASE_URL + "game/players/?access_token=" + accessToken ;
        String info = InGameService.request(url, true, null);

        try {
            JSONArray players = new JSONArray(info);
            assert(players.length() == 1);
            return players;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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
	]
	*/
	
    public static JSONObject getGameInfo(String accessToken, String gameId) {
        String url = InGameService.BASE_URL + "game/info/?access_token=" + accessToken + "&game_id=" + gameId;
        String info = InGameService.request(url, true, null);

        try {
            JSONArray games = new JSONArray(info);
            assert(games.length() == 1);
            return games.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getPlayerInfo(String accessToken, String facebookId) {
        String url = InGameService.BASE_URL + "player/info/?access_token=" + accessToken + "&user_id=" + facebookId;
        String info = InGameService.request(url, true, null);

        try {
            JSONArray players = new JSONArray(info);
            assert(players.length() == 1);
            return players.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFacebookId(Facebook facebook) {
        Bundle params = new Bundle();
        params.putString("access_token", facebook.getAccessToken());
        try {
            JSONObject me = new JSONObject(facebook.request("me", params));
            return me.getString("id");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
