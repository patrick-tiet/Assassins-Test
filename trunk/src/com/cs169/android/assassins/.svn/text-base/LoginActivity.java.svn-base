package com.cs169.android.assassins;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import org.json.JSONObject;

public class LoginActivity extends AuthenticationActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onAuthorized() {
        new InitializeAppTask().execute();
    }


    private class InitializeAppTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            String facebookId = Utility.getFacebookId(facebook);
            app.getUser().setUserID(facebookId);
            app.setAccessToken(facebook.getAccessToken());
            //int y=0;
            JSONObject player = Utility.getPlayerInfo(facebook.getAccessToken(), facebookId);


            if (player == null) {
                return false;
            } else {
                User user = app.getUser();
                user.parseJSONObject(player);
                if (user.getGameID() != "null") {
                    JSONObject game = Utility.getGameInfo(facebook.getAccessToken(), user.getGameID());
                    app.getNewGameAdded().parseJSONObject(game);
                }
                return true;
            }
        }

        @Override
        protected void onPreExecute() {
            this.dialog = ProgressDialog.show(LoginActivity.this, "", "Loading. Please wait...", true);
        }

        @Override
        protected void onPostExecute(Boolean playerExists) {
            this.dialog.dismiss();

            startActivity(chooseNextActivity(playerExists));
            finish();
        }

        private ProgressDialog dialog;
    }

    private Intent chooseNextActivity(Boolean playerExists) {
        String status = app.getUser().getStatus();

        if (!playerExists) {

            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("To start playing, you must take a picture.")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            // Player does not exist, take a picture
            return new Intent(LoginActivity.this, TakeProfilePictureActivity.class);

        } else if (status.equalsIgnoreCase("n")) {

            // Player is not in a game, take him to the list of games
            return new Intent(LoginActivity.this, ListOfGamesActivity.class);

        } else if (status.equalsIgnoreCase("w") && (app.getUser().getGameID() != null)) {

            // Player is waiting in a game room, take him to the waiting room
            Intent intent = new Intent(LoginActivity.this, WaitingRoom.class);
            intent.putExtra("Game Name", app.getNewGameAdded().getGameName());
            return intent;

        } else {

            // Player is in a game, take him to the game activity
            assert (status.equalsIgnoreCase("a") || status.equalsIgnoreCase("d") || status.equalsIgnoreCase("v"));
            return new Intent(LoginActivity.this, GameActivity.class);

        }
    }

    private static final String TAG = "LogIn";
}
