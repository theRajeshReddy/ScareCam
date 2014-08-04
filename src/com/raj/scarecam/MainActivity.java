package com.raj.scarecam;

import com.raj.scarecam.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private Camera mCamera;
	private CameraPreview mPreview;

//	public static final int MEDIA_TYPE_IMAGE = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Create an instance of Camera
		mCamera = getCameraInstance();

		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCamera);

		final FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		
		preview.addView(mPreview);

		final MediaPlayer mp = MediaPlayer.create(this, R.raw.s1);
		final ImageButton scare = (ImageButton) findViewById(R.id.button_capture);
		final ImageButton scare_img = (ImageButton) findViewById(R.id.scare_image);
		final View b = findViewById(R.id.scare_image);		
		
		scare.bringToFront();

		scare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				b.setVisibility(View.VISIBLE);
				preview.bringChildToFront(scare_img);
				mp.setVolume(2, 2);
				// Get instance of Vibrator from current Context
				Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				// Vibrate for 800 milliseconds
				vb.vibrate(800);
				mp.start();
				}
		});	
		
		scare_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				b.setVisibility(View.GONE);
			}
		});
		
}
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Closing ScareCam")
	        .setMessage("Are you sure you are done scaring people?")
	        .setPositiveButton("Yeh", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            finish();    
	        }

	    })
	    .setNegativeButton("Na", null)
	    .show();
	}
	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open(); // attempt to get a Camera instance
			c.setDisplayOrientation(90);
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}

}
