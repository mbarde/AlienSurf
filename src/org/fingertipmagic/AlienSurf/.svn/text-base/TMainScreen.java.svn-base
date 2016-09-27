package org.fingertipmagic.AlienSurf;

import de.funpic.mistavista.AlienSurf.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.Matrix;

public class TMainScreen {

    private float[] mProjMatrix = new float[16];
    private float[] mVMatrix = new float[16];
    private float[] mModelMatrix = new float[16];
    
    private float[] times;
    
    private AndroidAlienSurfActivity activityContext;
    
    // contains the information of which levels are enabled
    private boolean[] enabledMap = new boolean[Cons.levelCount];
	
	private float xPos = 0f;
	private float xStep = 0.5f;
	private float toXPos;
	private int activeIcon = 0;
	private int globalKillCount = 0;
	private TSquareManager squareMan;
	private TTextGL text;
	
	public TMainScreen(TSquareManager squareMan, TTextGL text, Context activityContext, float[] mVMatrix, float[] mProjMatrix, int activeID) {
		this.mVMatrix = mVMatrix;
		this.mProjMatrix = mProjMatrix;
		
		this.squareMan = squareMan;
		this.text = text;
		this.activityContext = (AndroidAlienSurfActivity)activityContext;
		
		times = new float[Cons.levelCount];
		
		enter(activeID);
	}
	
	public void update() {
		if (xPos < toXPos) xPos += xStep;
		if (xPos > toXPos) xPos -= xStep;
	}
	
	public void enter(int activeID) {
		SharedPreferences sharedPref = activityContext.getPreferences(Context.MODE_PRIVATE);
		globalKillCount = sharedPref.getInt( Cons.S_KILLCOUNT, 0);
		
		for (int i = 0; i < times.length; i++)
			times[i] = sharedPref.getFloat( Cons.S_BEST_TIME + i, 0);
		
		this.activeIcon = activeID;
		initEnabledMap();
	}
	
	private void initEnabledMap() {
		enabledMap = new boolean[Cons.levelCount];
		
		for (int i = 0; i < Cons.levelCount; i++) {
			if (globalKillCount >= Cons.levelCosts[i]) enabledMap[i] = true;
			else enabledMap[i] = false;
		}
	}
	
	public void render() {
		update();

		// BACKGROUND
		Matrix.setIdentityM(mModelMatrix, 0);
		Matrix.translateM(mModelMatrix, 0,-xPos/2, 0, 0.0f);
		
		Matrix.translateM(mModelMatrix, 0, 0, 0, -Cons.mainBGZDistance);
		squareMan.renderSquare(Cons.SID_MAIN_BACKGROUND, mModelMatrix, mVMatrix, mProjMatrix);
		
		// ICONS
		// TODO: render medals for levels
		int i = 0;
		SquareAdapter icon = squareMan.getLevelIcon(i);
		while (icon != null) {
			float z = Math.abs( i * (Cons.iconDistance + Cons.iconWidth) - xPos );
			
			Matrix.setIdentityM(mModelMatrix, 0);			
			Matrix.translateM(mModelMatrix, 0, (Cons.iconWidth + Cons.iconDistance)*i - xPos, 0, 0.0f);
			Matrix.translateM(mModelMatrix, 0, 0, 0, -z/8);
			if (!enabledMap[i]) squareMan.getSquare().setColor(0.5f, 0.5f, 0.5f);
			squareMan.renderLevelIcon(i, mModelMatrix, mVMatrix, mProjMatrix);
			if (!enabledMap[i]) squareMan.getSquare().setColor(1f, 1f, 1f);
			
			i++;
			icon = squareMan.getLevelIcon(i);
		}
		
		// TEXT
		if (activeIcon == Cons.LID_HELPSCREEN) {
			Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0,-xPos, 0, 0.0f);
			text.showText( new FPoint(xPos,3.4f), 
							activityContext.getResources().getString(R.string.help_titel), 
							0.5f, 0, 1, 0, mModelMatrix, mVMatrix, mProjMatrix);
		} else {			
			Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0,-xPos, 0, 0.0f);
			text.showText( new FPoint(xPos,3.4f), 
							activityContext.getResources().getString(R.string.besttime) 
							+ ": " + times[activeIcon] + "s", 0.5f, 0, 1, 0, 
							mModelMatrix, mVMatrix, mProjMatrix);
		}
		
		if (!enabledMap[activeIcon]) {
			Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0,-xPos, 0, 0.0f);
			text.showText( new FPoint(xPos,0), activityContext.getResources().getString(R.string.level_locked_01) 
							+ " " + Cons.levelCosts[activeIcon] + " " + activityContext.getResources().getString(R.string.level_locked_02), 
							0.3f, 1, 1, 1, mModelMatrix, mVMatrix, mProjMatrix);
		}
		
		Matrix.setIdentityM(mModelMatrix, 0);
		Matrix.translateM(mModelMatrix, 0,-xPos, 0, 0.0f);
		text.showText( new FPoint(xPos,-3.4f), 
				activityContext.getResources().getString(R.string.totalkillcount) 
				+ ": " + globalKillCount, 0.5f, 0, 1, 0, 
				mModelMatrix, mVMatrix, mProjMatrix);
	}
	
	public void moved(FPoint delta) {
		//xPos -= (delta.x / 100);
		
		if (delta.x < -10) activeIcon++;
		if (delta.x >  10) activeIcon--;		
		if (activeIcon < 0) activeIcon = Cons.levelCount-1;
		if (activeIcon > Cons.levelCount-1) activeIcon = 0;
		
		toXPos = activeIcon * (Cons.iconWidth + Cons.iconDistance);
	}
	
	public int onLongPress(FPoint pos) {
		if (xPos != toXPos || !enabledMap[activeIcon]) return Cons.INT_ERROR;
		return activeIcon;
	}
	
	public void setProjMatrix(float[] mProjMatrix) { this.mProjMatrix = mProjMatrix; }

}
