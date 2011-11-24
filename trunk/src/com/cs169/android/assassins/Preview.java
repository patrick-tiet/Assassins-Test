package com.cs169.android.assassins;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*import com.google.android.maps.GeoPoint;*/

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Preview extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = "Preview";
	SurfaceHolder mHolder;
	public Camera camera;

	Preview(Context context,Camera camera) {
		super(context);
		this.camera=camera;
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where
		// to draw.
		//camera = Camera.open();
		try {
			camera.setPreviewDisplay(holder);

			camera.setPreviewCallback(new PreviewCallback() {

				public void onPreviewFrame(byte[] data, Camera arg1) {
					FileOutputStream outStream = null;
					try {
						outStream = new FileOutputStream(String.format(
								"/sdcard/%d.jpg", System.currentTimeMillis()));
						outStream.write(data);
						outStream.close();
						Log.d(TAG, "onPreviewFrame - wrote bytes: "
								+ data.length);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
					}
					Preview.this.invalidate();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.
		
/*		  camera.stopPreview();
		//camera.release();
		//camera = null;
*/		
		Log.d("CAMERA6", camera.toString());
		
		CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {

			@Override
			public void onFinish() {
				if(camera != null){
					Log.d("CAMERA1", camera.toString());
					camera.setPreviewCallback(null);
					Log.d("CAMERA2", camera.toString());
					camera.stopPreview();
					Log.d("CAMERA1", camera.toString());
					camera.release();
			        camera = null;			
			        camera.release();
			        camera = null;
				}
			}

			@Override
			public void onTick(long millisUntilFinished) {
			}
		};
		
		Log.d("CAMERA7", camera.toString());
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		Camera.Parameters parameters = camera.getParameters();
		parameters.setPreviewSize(w, h);
		camera.setParameters(parameters);
		camera.startPreview();
	}
	
	/*@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if(camera != null){
			camera.stopPreview();
			camera.setPreviewCallback(null);
	        camera.release();
	        camera = null;
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	    if (camera == null) {
	        camera = Camera.open();
	        try {
	            camera.setPreviewDisplay(holder);

	            // TODO test how much setPreviewCallbackWithBuffer is faster
	            camera.setPreviewCallback(this);
	        } catch (IOException e) {
	            camera.release();
	            camera = null;
	        }
	    }
	}*/

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Paint p = new Paint(Color.RED);
		Log.d(TAG, "draw");
		canvas.drawText("PREVIEW", canvas.getWidth() / 2,
				canvas.getHeight() / 2, p);
	}
}