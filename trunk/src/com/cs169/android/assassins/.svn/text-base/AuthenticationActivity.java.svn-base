package com.cs169.android.assassins;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class AuthenticationActivity extends Activity {

    Facebook facebook = new Facebook("281380471893391");
    AssassinsApplication app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (AssassinsApplication) getApplication();

        /*
         * Get existing access_token if any
         */
        final SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        String access_token = mPrefs.getString("access_token", null);
        long expires = mPrefs.getLong("access_expires", 0);
        if (access_token != null) {
            facebook.setAccessToken(access_token);
        }
        if (expires != 0) {
            facebook.setAccessExpires(expires);
        }

        /*
         * Only call authorize if the access_token has expired.
         */
        if (!facebook.isSessionValid()) {
            facebook.authorize(this, new DialogListener() {
                @Override
                public void onComplete(Bundle values) {
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString("access_token", facebook.getAccessToken());
                    editor.putLong("access_expires", facebook.getAccessExpires());
                    editor.commit();

                    onAuthorized();
                }

                @Override
                public void onFacebookError(FacebookError error) {
                    // TODO: handle error
                }

                @Override
                public void onError(DialogError e) {
                    // TODO: handle error
                }

                @Override
                public void onCancel() {
                    // TODO: handle error
                }
            });
        } else {
            onAuthorized();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }

    protected void onAuthorized() {
    }
}
