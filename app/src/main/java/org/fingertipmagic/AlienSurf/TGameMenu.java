package org.fingertipmagic.AlienSurf;

import android.opengl.Matrix;

public class TGameMenu {
	
	private float[] mMVPMatrix = new float[16];
	private float[] mModelMatrix = new float[16];
	
	private static int M_DEFAULT = 0;
	private static int M_PAUSE = 1;
	private int mode;
	
	private float levelProgress = 0;
	private float pauseX = Cons.HUD_X;
	private float pauseY = 3;
	private float playX = 0;
	private float playY = -2;
	private float restartX = 2.5f;
	private float restartY = -2;
	private float exitX = -2.5f;
	private float exitY = -2;
	private float progBarX = Cons.HUD_X;
	private float progBarY = pauseY - Cons.menuIconWidth - (Cons.menuProgressBarHeight/2);
	private float timeX = Cons.HUD_X;
	private float timeY = progBarY - Cons.menuProgressBarHeight/2 - 1;
	private float timeSize = 0.5f;
	private float time = 0;
	
	private TSquareManager squareMan;
	private TTextGL text;
	
	public TGameMenu(TSquareManager squareMan, TTextGL text) {
		this.squareMan = squareMan;
		this.text = text;
		mode = M_DEFAULT;
	}
	
	public void render(float[] mVMatrix, float[] mProjMatrix) {
		
		if (mode == M_DEFAULT) { // STANDARD ON SCREEN MENU
			// PAUSE Button
			Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0, pauseX, pauseY, 0);
			squareMan.renderSquare(Cons.SID_ICON_PAUSE, mModelMatrix, mVMatrix, mProjMatrix);
			
			// Level progress bar
			if (levelProgress > 0) {
				Matrix.setIdentityM(mModelMatrix, 0);
				Matrix.translateM(mModelMatrix, 0, progBarX, progBarY, 0);
				squareMan.renderSquare(Cons.SID_SIDEBAR, mModelMatrix, mVMatrix, mProjMatrix);
			
				// inclusive dot ;-)
				Matrix.setIdentityM(mModelMatrix, 0);
				float y = progBarY + (Cons.menuProgressBarHeight/2) - (levelProgress * Cons.menuProgressBarHeight);
				Matrix.translateM(mModelMatrix, 0, progBarX, y, 0);
				squareMan.renderSquare(Cons.SID_SIDEBAR_DOT, mModelMatrix, mVMatrix, mProjMatrix);	
			}
			// time 
			if (time > 0) {
				Matrix.setIdentityM(mModelMatrix, 0);
				Matrix.translateM(mModelMatrix, 0, timeX, timeY, 0);
				Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mModelMatrix, 0); 
				Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
				text.showText( new FPoint(0,0), time + "s", timeSize, 1, 1, 1, 
								mModelMatrix, mVMatrix, mProjMatrix);
			}
		} else
		if (mode == M_PAUSE) { // PAUSE MENU
			Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0, restartX, restartY, 0);
			squareMan.renderSquare(Cons.SID_ICON_RESTART, mModelMatrix, mVMatrix, mProjMatrix);
			
			Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0, playX, playY, 0);
			Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mModelMatrix, 0); 
			Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
			squareMan.renderSquare(Cons.SID_ICON_PLAY, mModelMatrix, mVMatrix, mProjMatrix);
			
			Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0, exitX, exitY, 0);
			Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mModelMatrix, 0); 
			Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
			squareMan.renderSquare(Cons.SID_ICON_EXIT, mModelMatrix, mVMatrix, mProjMatrix);
		}
	}
	
	// codes:
	// 0: nothing
	// 1: default to pause
	// 2: play pressed (pause to default)
	// 3: exit to main menu
	// 4: restart level
	public int touched(FPoint pos) {
		if (mode == M_PAUSE) { 
			if (pos.y >= 0.55 && pos.y <= 0.7) {
				if (pos.x >= 0.4 && pos.x <= 0.6) {
					mode = M_DEFAULT;
					return 2; // resume
				}		
				else 
				if (pos.x >= 0.2 && pos.x <= 0.4) {
					return 3; // exit
				}
				else
				if (pos.x >= 0.6 && pos.x <= 0.8) {
					mode = M_DEFAULT;
					return 4; // restart
				}
				else return 0;
			}
			else return 0;
		} else
		if (Cons.HUD_right && pos.x >= 0.8 && pos.y <= 0.2) {
			mode = M_PAUSE;
			return 1;
		} else
			if (!Cons.HUD_right && pos.x <= 0.2 && pos.y <= 0.2) {
				mode = M_PAUSE;
				return 1;
			} else

		return 0;
	}
	
	public void pause() {
		mode = M_PAUSE;
	}
	
	public void setLevelProgress(float progress) {
		this.levelProgress = progress;
		if (levelProgress < 0) levelProgress = 0;
		if (levelProgress > 1) levelProgress = 1;
	}
	
	public void setTime(float time) {
		this.time = (float)Math.round(time * 10) / 10;
	}

}
