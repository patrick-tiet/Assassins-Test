package com.cs169.android.assassins;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TakeProfilePictureActivity extends AuthenticationActivity implements SurfaceHolder.Callback {

    private static final String TAG = "Create User Screen";

    Camera mCamera;
    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    private boolean mPreviewRunning = false;
    static final int FOTO_MODE = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_profile_picture);

        ImageButton takePictureButton = (ImageButton) findViewById(R.id.takePicture);

        mSurfaceView = (SurfaceView) findViewById(R.id.preview);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        takePictureButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                mCamera.takePicture(null, null, mPictureCallback);
            }
        });

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
                // TODO: Confirmation dialog

                Intent mIntent = new Intent(TakeProfilePictureActivity.this, ListOfGamesActivity.class);

                Bitmap mybitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

                int height = mybitmap.getHeight();
                int width = mybitmap.getWidth();
                int scaledHeight = height / (width / 200);

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(mybitmap, 200, scaledHeight, true);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                byte[] byteArray = stream.toByteArray();

                String url = InGameService.BASE_URL + "player/updatepicture/";
                Log.v("camera info:", "url:" + url);
                HttpPost httppost = new HttpPost(url);

                MultipartEntity mp = new MultipartEntity();
                ContentBody cb = new ByteArrayBody(byteArray, "image/jpeg", "picture.jpg");
                mp.addPart("picture", cb);
                try {

                    ContentBody token = new StringBody(
                            facebook.getAccessToken());

                    mp.addPart("access_token", token);
                    httppost.setEntity(mp);
                    InGameService.request(url, false, httppost);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mCamera.startPreview();

                setResult(FOTO_MODE, mIntent);
                startActivity(mIntent);
                finish();
            }
        }
    };

}

