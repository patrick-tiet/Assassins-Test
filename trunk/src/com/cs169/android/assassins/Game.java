package com.cs169.android.assassins;

import org.json.JSONException;
import org.json.JSONObject;

public class Game {
    /*{
         "pk": 1,
         "model": "assassins.game",
         "fields": {
           "status": "w",
           "public": true,
           "max_players": 10,
           "name": "This is the game",
           "creator": 1
         }
       }, */

    private String status;
    private String gameName;
    private String publicGame;
    private String numberOfPlayers;
    private String max_players;
    private String creator;
    private String game_id;

    public Game() {
        this.status = "";
        this.gameName = "";
        this.publicGame = "";
        this.numberOfPlayers = "0";
        this.max_players = "0";
        this.creator = "";
        this.game_id = "";
    }

    public void parseJSONObject(JSONObject game) {
        try {
            JSONObject fields = game.getJSONObject("fields");

            this.setCreator(fields.getString("creator"));
            this.setGameID(game.getString("pk"));
            this.setGameName(fields.getString("name"));
            this.setMaxPlayers(fields.getString("max_players"));
            this.setPublicGame(fields.getString("public"));
            this.setStatus(fields.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setGameID(String id) {
        this.game_id = id;
    }

    public String getGameID() {
        return this.game_id;
    }

    public void setStatus(String a) {
        this.status = a;
    }

    public String getStatus() {
        return this.status;
    }

    public void setGameName(String name) {
        this.gameName = name;
    }

    public String getGameName() {
        return this.gameName;
    }

    public void setPublicGame(String val) {
        this.publicGame = val;
    }

    public String getPublicGame() {
        return this.publicGame;
    }

    public void setNumberOfPlayers(String num) {
        this.numberOfPlayers = num;
    }

    public String getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    public void setMaxPlayers(String a) {
        this.max_players = a;
    }

    public String getMaxPlayers() {
        return this.max_players;
    }

    public void setCreator(String a) {
        this.creator = a;
    }

    public String getCreator() {
        return this.creator;
    }

    public String toString() {
        String val = "Game.toString() is = " + this.getCreator()
                + this.gameName + this.max_players + this.publicGame + this.status;
        return val;
    }

}
