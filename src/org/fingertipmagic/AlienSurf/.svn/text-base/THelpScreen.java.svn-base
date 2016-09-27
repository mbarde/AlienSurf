package org.fingertipmagic.AlienSurf;

import de.funpic.mistavista.AlienSurf.R;
import android.content.Context;
import android.media.MediaPlayer;
import android.opengl.Matrix;

public class THelpScreen {
	
    private float[] mProjMatrix = new float[16];
    private float[] mVMatrix = new float[16];
    private float[] mModelMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];
    
    private int surferMode = 0;
    private long lastChangeTime, lastTouchTime;
    private long frameTime = 200000000;
    private long waitToScrollAgain = 1500000000;
    
    private MediaPlayer mediaplayer;
	private TSquareManager squareMan;
	private Context context;
	private TTextGL text;
	private String[] linesStory, linesControls, linesInfo;
	private float minY, maxY, yPos = 5f;
	private boolean autoscroll;
	private float yViewSize = 4;
	private float letterSpace = 1f;	// space a letter needs (includes Y-distance to other letters)
	private float letterSize = 0.5f;	// actual size of a letter
	
	public THelpScreen(	TSquareManager squareMan, TTextGL text, Context context, 
						float[] mVMatrix, float[] mProjMatrix) {
		this.mVMatrix = mVMatrix;
		this.mProjMatrix = mProjMatrix;
		
		this.squareMan = squareMan;
		this.context = context;
		this.text = text;
		
		loadLines();
		
		autoscroll = true;
		
		mediaplayer = MediaPlayer.create(context, R.raw.lillychip);
		mediaplayer.setLooping(true);
	}
	
	// should be called always when the help screen is entered
	public void enter() {
		yPos = 0;
		autoscroll = true;
		mediaplayer.start();
	}
	
	public void update(float delta) {
		if (System.nanoTime() - lastChangeTime > frameTime) {
			surferMode = (surferMode + 1) % 4;
			lastChangeTime = System.nanoTime();
		}
		
		if (!autoscroll && System.nanoTime() - lastTouchTime > waitToScrollAgain)
			autoscroll = true;
		
		if (yPos > minY && autoscroll) yPos -= (delta / 50);
		checkYPos();
	}
		
	public void render() {     
    	Matrix.setIdentityM(mModelMatrix, 0);	
    	Matrix.translateM(mModelMatrix, 0, 0, 0, -Cons.mainBGZDistance);
        squareMan.getSquare().setColor(0.5f, 0.5f, 0.5f);
        squareMan.renderSquare(Cons.SID_MAIN_BACKGROUND, mModelMatrix, mVMatrix, mProjMatrix);
        squareMan.getSquare().setColor(1, 1, 1);
        
        /*
    	Matrix.setIdentityM(mModelMatrix, 0);	
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mModelMatrix, 0); 
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        if (surferMode == 0) squareMan.getSquare(Cons.SID_SURFER_STAND).draw(mMVPMatrix);
        else
        if (surferMode == 1) squareMan.getSquare(Cons.SID_SURFER_STEP01).draw(mMVPMatrix);
        else
        if (surferMode == 2) squareMan.getSquare(Cons.SID_SURFER_STEP02).draw(mMVPMatrix);
        else
        if (surferMode == 3) squareMan.getSquare(Cons.SID_SURFER_STEP03).draw(mMVPMatrix);
		*/
        
        renderLines(linesStory, 0, 1, 1, 0);
		renderLines(linesControls, (linesStory.length+1) * letterSpace, 0, 1, 0);
		renderLines(linesInfo, (linesStory.length+1) * letterSpace + (linesControls.length+1) * letterSpace, 1, 0, 0);
	}

	public void renderLines(String[] lines, float yOffset, float r, float g, float b) {
		
        for (int i = 0; i < lines.length; i++) {
        	float y = - yPos - yOffset - (letterSpace * i);
        	if ( Math.abs(y) < yViewSize ) {
        		Matrix.setIdentityM(mModelMatrix, 0);
        		Matrix.translateM(mModelMatrix, 0, 0, y, 0);		
        		Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mModelMatrix, 0); 
        		Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
            
        		text.showText( new FPoint(0,0), lines[i], letterSize, r, g, b, 
        						mModelMatrix, mVMatrix, mProjMatrix);
        	}
        }
	}
	
	public void moved(FPoint delta) {
		autoscroll = false;
		lastTouchTime = System.nanoTime();
		yPos += (-delta.y / 100);
		checkYPos();
	}
	
	public void loadLines() {
		linesStory = context.getResources().getString(R.string.help_story).split(":");
		linesControls = context.getResources().getString(R.string.help_controls).split(":");
		linesInfo = context.getResources().getString(R.string.help_info).split(":");
		
		maxY = 0;
		minY = -(linesStory.length + linesControls.length + linesInfo.length + 3) * letterSpace;
	}
	
	public void checkYPos() {
		if (yPos < minY) yPos = minY;
		if (yPos > maxY) yPos = maxY;
	}
	
	public void leave() {
		if (mediaplayer != null) mediaplayer.pause();
	}
	
}
