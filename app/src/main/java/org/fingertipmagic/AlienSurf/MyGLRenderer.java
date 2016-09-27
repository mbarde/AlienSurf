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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import de.funpic.mistavista.AlienSurf.R;

import android.app.Dialog;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class MyGLRenderer implements GLSurfaceView.Renderer {
	
	private static int M_START = 0;
	private static int M_GAME = 1;
	private static int M_HELP = 2;
	
	// Position the eye behind the origin.
	final float eyeX = 0.0f;
	final float eyeY = 0.0f;
	final float eyeZ = 5;

	// We are looking toward the distance
	final float lookX = 0.0f;
	final float lookY = 0.0f;
	final float lookZ = -5.0f;

	// Set our up vector. This is where our head would be pointing were we holding the camera.
	final float upX = 0.0f;
	final float upY = 1.0f;
	final float upZ = 0.0f;
	
	public float dx = 0.0f;
	public float dy = 0.0f;

    private static final String TAG = "MyGLRenderer";

    private final float[] mProjMatrix = new float[16];
    private final float[] mVMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];
    private final float[] mMVPMatrix = new float[16];
    
    private Context activityContext;
    private AndroidAlienSurfActivity aContext;
    private TLevelFactory factory = null;
    private TTextureManager texMan = null;
    private TSquareManager squareMan = null;
    private TSoundPlayer soundPlayer = null;
    private TTextGL text = null;
    private TLevel level = null;
    private TMainScreen main = null;
    private THelpScreen help = null;
    private int mode = 0;
    private int splashCount;
    private boolean init;
    private Square splashSquare;

    private boolean ignoreTouch = false;
    private long lastTouchTime, touchIgnoreTime;
    
    private float timeDeltaS = 1;
    public float FPS = 0.0f;
    
    public MyGLRenderer(Context activityContext) {    	
    	this.activityContext = activityContext;
    	this.aContext = (AndroidAlienSurfActivity) activityContext;
    }
    
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
	
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// Set the view matrix. This matrix can be said to represent the camera position.
		// NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
		// view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
		Matrix.setLookAtM(mVMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
    	
		init = false;
		splashCount = 0;
		int texID = TextureHelper.loadTexture(activityContext, R.drawable.big_logo);
		splashSquare = new Square(0,0,0, 1,1, texID);
		
		// Enable blending		
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    }
    
    public void init() {
		texMan = new TTextureManager(activityContext);
		squareMan = new TSquareManager(texMan, splashSquare);
		soundPlayer = new TSoundPlayer(activityContext);
		text = new TTextGL( texMan.getTexture(Cons.TID_FONT), splashSquare );
		
		if (main == null) main = new TMainScreen(squareMan, text, activityContext, mVMatrix, mProjMatrix, Cons.LID_HELPSCREEN);
		if (help == null) help = new THelpScreen(squareMan, text, activityContext, mVMatrix, mProjMatrix);
		
		if (factory == null) factory = new TLevelFactory(this, activityContext, texMan, squareMan, soundPlayer,
				text, mVMatrix, mProjMatrix);
		
	   	ignoreTouch = false;	
	   	
	   	init = true;
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        final long lastNS = System.nanoTime();
        
    	GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        
    	if (!init) {
    		Matrix.setIdentityM(mModelMatrix, 0);
    		Matrix.scaleM(mModelMatrix, 0, 12, 4, 1);
    		Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mModelMatrix, 0); 
    		Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
    		splashSquare.draw(mMVPMatrix);
    		splashCount++;
    		if (splashCount > 1) init();
    		return;
    	}
    	
    	if (ignoreTouch)
    		if (System.nanoTime() - lastTouchTime > touchIgnoreTime) ignoreTouch = false;
    	
    	if (main == null && text != null) {
    		Matrix.setIdentityM(mModelMatrix, 0);
    		text.showText( new FPoint(0,0), "Loading...", 1, 1, 1, 1, mModelMatrix, mVMatrix, mProjMatrix);
    	}
    	
    	if (mode == M_START) main.render();
    	else
    	if (mode == M_GAME) { 
    		level.render();  
    		level.update(timeDeltaS);
    	}
    	else
    	if (mode == M_HELP) {
    		help.render();
    		help.update(timeDeltaS);
    	}
        
        final long  timeDeltaNS = System.nanoTime() - lastNS;

        // Determine time since last frame in seconds.
        final float timeDeltaS = timeDeltaNS * 1.0e-9f;
        FPS = 1 / timeDeltaS;

        aContext.fps = Math.round( FPS );
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
		// Set the OpenGL viewport to the same size as the surface.
		GLES20.glViewport(0, 0, width, height);

		// Create a new perspective projection matrix. The height will stay the same
		// while the width will vary as per aspect ratio.
		final float ratio = (float) width / height;
		final float left = -ratio;
		final float right = ratio;
		final float bottom = -1.0f;
		final float top = 1.0f;
		final float near = 1.0f;
		final float far = 10.0f;
		
		Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
		if (level != null) level.setProjMatrix(mProjMatrix);
		if (main != null) main.setProjMatrix(mProjMatrix);
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
    
    public void touched(FPoint pos) { 
    	if (!ignoreTouch) {
    		
    	if (mode == M_GAME) level.touched(pos); 
    	
    	}
    }
    
    public void onLongPress(FPoint pos) {
    	if (!ignoreTouch) {
    	
    	if (mode == M_START && main != null)  {
    		int h = main.onLongPress(pos);
    		if (h >= Cons.bonusLevels) {
    			level = factory.createLevel(h-Cons.bonusLevels);	
    			mode = M_GAME;
    		} else
    			if (h == Cons.LID_HELPSCREEN) {
    				mode = M_HELP;
    				help.enter();
    			}
    	}
    	}
    }
    
    public int backPressed() {
    	if (mode == M_START) return 0;
    	else
    	if (mode == M_GAME) {
    		mode = M_START;
    		main.enter(level.getID());
    	}
    	else
    	if (mode == M_HELP) {
    		help.leave();
    		mode = M_START;
    		main.enter(Cons.LID_HELPSCREEN);
    	}
    	return -1;
    }
    
    public void ignoreTouch(long time) {
    	lastTouchTime = System.nanoTime();
    	touchIgnoreTime = time;
    	ignoreTouch = true;
    }
    
    public void moved(FPoint delta) {
    	if (mode == M_START && main != null) main.moved(delta);
    	//else
    	//if (mode == M_HELP) help.moved(delta);
    }
    
    public void scrolled(FPoint delta) {
    	if (mode == M_HELP) help.moved(delta);   	
    }
    
    public void onPause() { 
    	if (mode == M_GAME) level.pause();
    	else
    	if (mode == M_HELP) { help.leave(); }
    }
    
    public void onResume() { 
    	if (mode == M_HELP) mode = M_START;
    }    
    
}