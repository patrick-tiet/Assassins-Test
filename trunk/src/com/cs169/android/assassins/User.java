package com.cs169.android.assassins;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String userName;
    private String userID;
    private String targetName;
    private String targetID;
    private String status;
    private String stats;
    private String game; // Game Name
    private String gameID; // Game ID which httpRequest return "game":3
    private String photoURL;
    private String pk;

    public User() {
        this.pk = "";
        this.gameID = "null";
        this.userName = "";
        this.userID = "";
        this.targetName = "";
        this.targetID = "null";
        this.status = "";
        this.stats = "";
        this.game = "";
        this.photoURL = "";

    }

    public void parseJSONObject(JSONObject player) {
        try {
            JSONObject fields = player.getJSONObject("fields");

            this.setUserID(fields.getString("facebook_id"));
            this.setUserName(fields.getString("name"));
            this.setStatus(fields.getString("status"));
            this.setStats(fields.getString("stats"));
            this.setGameID(fields.getString("game"));
            this.setPhotoURL(fields.getString("photo_file"));
            this.setPK(player.getString("pk"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setPK(String a) {
        this.pk = a;
    }

    public String getPK() {
        return this.pk;
    }

    public void setGameID(String a) {
        this.gameID = a;
    }

    public String getGameID() {
        return this.gameID;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserID(String id) {
        this.userID = id;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setTargetName(String name) {
        this.targetName = name;
    }

    public String getTargetName() {
        return this.targetName;
    }

    public void setTargetID(String id) {
        this.targetID = id;
    }

    public String getTargetID() {
        return this.targetID;
    }

    public void setStatus(String a) {
        this.status = a;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStats(String a) {
        this.stats = a;
    }

    public String getStats() {
        return this.stats;
    }

    public void setGame(String a) {
        this.game = a;
    }

    public String getGame() {
        return this.game;
    }

    public void setPhotoURL(String a) {
        this.photoURL = a;
    }

    public String getPhotoURL() {
        return this.photoURL;
    }
}
