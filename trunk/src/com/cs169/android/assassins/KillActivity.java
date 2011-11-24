package com.cs169.android.assassins;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.IOException;


public class KillActivity extends AuthenticationActivity implements SurfaceHolder.Callback {


    private static final String TAG = "CameraDemo";
    Camera mCamera;


    Button buttonClick, bBtn;
    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    private boolean mPreviewRunning = false;
    private Context mContext = this;
    static final int FOTO_MODE = 0;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kill);

        //preview = new Preview(this,camera);
        //((SurfaceView) findViewById(R.id.preview)).addView(preview);

        mSurfaceView = (SurfaceView) findViewById(R.id.preview);


        mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.addCallback(this);

        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        buttonClick = (Button) findViewById(R.id.ki);
        bBtn = (Button) findViewById(R.id.bbtn);
        buttonClick.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //preview.camera.takePicture(shutterCallback, rawCallback,jpegCallback);
            }
        });


        buttonClick.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

           	 buttonClick.setTextColor(Color.GRAY);
           	 buttonClick.setBackgroundColor(Color.BLACK);
           	Long time=System.currentTimeMillis();
                mCamera.takePicture(null, mPictureCallback, mPictureCallback);
                
                
                buttonClick.setTextColor(Color.BLACK);
              	 buttonClick.setBackgroundColor(0xff990000);
            }
        });

        bBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    startActivity(new Intent(createPackageContext("com.cs169.android.assassins", CONTEXT_INCLUDE_CODE), GameActivity.class));
                } catch (NameNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        Log.d(TAG, "onCreate'd");


    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mPreviewRunning) {
            mCamera.stopPreview();
        }

        Camera.Parameters p = mCamera.getParameters();
        p.setPreviewSize(width, height);
        mCamera.setParameters(p);

        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCamera.startPreview();
        mPreviewRunning = true;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mPreviewRunning = false;
        mCamera.release();
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] imageData, Camera c) {

            Log.v("camera info:", "got there:" + (imageData == null));
            if (imageData != null) {

                Intent mIntent = new Intent();
                
                AssassinsApplication app = new AssassinsApplication();
                // Bitmap bitmap=
                // BitmapFactory.decodeByteArray(imageData,0,imageData.length);
                // String imageString=Base64.encodeToString(imageData
                // ,Base64.DEFAULT);
                String url = InGameService.BASE_URL + "ingame/killrequest/";
                HttpPost httppost = new HttpPost(url);
                // Log.v("camera info:","string respresentation:"
                // +imageString.toString() );

                // List<NameValuePair> nameValuePairs = new
                // ArrayList<NameValuePair>();

                // nameValuePairs.add(new
                // BasicNameValuePair("access_token",facebook.getAccessToken()));
                // nameValuePairs.add(new
                // BasicNameValuePair("picture",imageString));

                // Log.v("taking pic, ",httppost.toString());
                // Log.v("camera info:","value pairs 0 :"
                // +nameValuePairs.get(0).toString());
                // Log.v("camera info:","value pairs 1 :"
                // +nameValuePairs.get(1).toString());
                // Log.v("camera info:","value pairs  :"
                // +nameValuePairs.toString());

                MultipartEntity mp = new MultipartEntity();
                ContentBody cb = new ByteArrayBody(imageData, "image/jpeg", "picture.jpg");
                mp.addPart("picture", cb);
                try {

                    ContentBody token = new StringBody(
                            app.getAccessToken());

                    mp.addPart("access_token", token);
                    httppost.setEntity(mp);
                    InGameService.request(url, false, httppost);
                    
                   
                } catch (Exception/** UnsupportedEncodingException **/
                        e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                mCamera.startPreview();

                setResult(FOTO_MODE, mIntent);
               

            }

        }
    };
}

