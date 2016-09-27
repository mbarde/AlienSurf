/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fingertipmagic.AlienSurf;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class AndroidAlienSurfActivity extends Activity implements SensorEventListener 
	{

	public float azimuth = 0.0f;
	public float pitch = 	0.0f;
	public float roll = 0.0f;
	public float fps = 		0.0f;
	
	public float[] rotMatrix = new float[16];
	public float[] inclMatrix = new float[16];
	public float[] gravity = new float[3];
	public float[] geomagnetic = new float[3];
	
    private MyGLSurfaceView mGLView;
    
    private SensorManager mSensorManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        
        setVolumeControlStream(AudioManager.STREAM_MUSIC); 
        System.out.println("CREATED");
        
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
      // Do something here if sensor accuracy changes.
      // You must implement this callback in your code.
    }    

    @Override
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
       
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
        mGLView.getRenderer().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_FASTEST);
    
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
        mGLView.getRenderer().onResume();
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
    	if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) 
    		gravity = event.values.clone();
    	if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD ) 
    		geomagnetic = event.values.clone();
    	
    	if (gravity != null && geomagnetic != null) {

    		SensorManager.getRotationMatrix(rotMatrix, inclMatrix, gravity, geomagnetic);
    		
    		float[] actual_orientation = new float[3];
    	    SensorManager.getOrientation(rotMatrix, actual_orientation);
    	    
    	    azimuth = (float)Math.toDegrees( actual_orientation[0] );
    	    pitch =  (float)Math.toDegrees( actual_orientation[1] );
    	    roll = (float)Math.toDegrees( actual_orientation[2] );
    	}
    	
    	//angle = event.values[1];
 
    	String title = "a: " + Math.round(azimuth) 
    					+ "p: " + Math.round(pitch) + "r: " + Math.round(roll) + "fps: " + fps;
    	setTitle( title );
    }
    
    @Override
    public void onBackPressed() {
    	if ( mGLView.getRenderer().backPressed() == 0 ) this.finish();
    }
    
}

class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    
    private GestureDetector mGestureDetector;

    public MyGLSurfaceView(Context context) {
        super(context);
        
        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer(context);
        setRenderer(mRenderer);

		AndroidAlienSurfActivity activity = (AndroidAlienSurfActivity) context;
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
        mGestureDetector = new GestureDetector(context, new TGestureListener(mRenderer,
        										metrics.widthPixels, metrics.heightPixels) );
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
    	return (mGestureDetector.onTouchEvent(e));
    }
    
    public MyGLRenderer getRenderer() { return mRenderer; }
    
}
