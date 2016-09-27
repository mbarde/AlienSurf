package org.fingertipmagic.AlienSurf;

import org.fingertipmagic.AlienSurf.AndroidAlienSurfActivity;

import de.funpic.mistavista.AlienSurf.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.Matrix;

public class TLevel {
	
	private AndroidAlienSurfActivity activityContext;
    private float[] mProjMatrix = new float[16];
    private float[] mVMatrix = new float[16];
    private float[] mModelMatrix = new float[16];
	
	final int GS_NO_INIT = 0;
	final int GS_FLY = 1;
	final int GS_WAIT = 2;
	final int GS_RUN = 3;
	final int GS_PAUSE = 4;
	private int gamestate = GS_NO_INIT;
	private int stateBeforePause;
	
	private int levelID;
	
	private long startTime, pauseStartTime;
	private int killedAliens;
	private FPoint camPos;
	private FPoint camOffset = new FPoint(0,-2);
	private float camFly;
	private TSegment[][] segments;	
	private TSurfer surfer;
	private TBackground back;
	private TTextGL text;
	private TSquareManager squareMan;
	private TGameMenu gameMenu;
	private TSoundPlayer soundPlayer;
	private MyGLRenderer renderer;
	
	private String string0, string1, recString, newLevelString;
	private long s0startTime, s1startTime, s0duration, s1duration;
	private boolean s0timer = false;
	private boolean s1timer = false;
	
	private boolean boom = false;
	private boolean pleaseEndGame = false;
	
	private TLevelConfig config;
	
	public TLevel(TLevelConfig config, MyGLRenderer renderer, Context activityContext, 
						TTextureManager texMan, TSquareManager squareMan, TSoundPlayer soundPlayer,
						TTextGL text, float[] mVMatrix, float[] mProjMatrix, int levelID) {
		this.config = config;
		this.renderer = renderer;
		this.activityContext = (AndroidAlienSurfActivity) activityContext;
		this.mVMatrix = mVMatrix;
		this.mProjMatrix = mProjMatrix;
		this.text = text;
		this.squareMan = squareMan;
		this.soundPlayer = soundPlayer;
		this.levelID = levelID;	
		pleaseEndGame = false;
		
		camFly = (Cons.segHeight * config.height) / 100;
		
		surfer = new TSurfer( config.width/2*Cons.segWidth, 
								config.height*Cons.segHeight, squareMan);
		
		back = new TBackground(squareMan, Cons.SID_LEVEL_BACKGROUND, 0, 0, 
								Cons.segHeight*2, Cons.segHeight*config.height, 0);
		
		gameMenu = new TGameMenu(squareMan, text);
		
		string0 = activityContext.getResources().getString(R.string.smack_them);
		string1 = activityContext.getResources().getString(R.string.touch_to_start);
		recString = "";
		newLevelString = "";
				
		startFly();
	}
	
	public void startFly() {
		// initialize segments
		segments = new TSegment[config.width][config.height];
		for (int x = 0; x < config.width; x++)
			for (int y = 0; y < config.height; y++) {
				segments[x][y] = new TSegment(x*Cons.segWidth, y*Cons.segHeight,
										Cons.segWidth, Cons.segHeight, 
										squareMan, config );
				if (y % 2 != 0) {
					if (Math.random() >= config.student_p) segments[x][y].hasStudent = true;
					else
					if (Math.random() >= config.itemspawn_p) segments[x][y].spawnItem();
					else 
					if (Math.random() >= config.obstacle_p) segments[x][y].hasObstacle = true;
				}
			}
		
		camPos = new FPoint( config.width*Cons.segWidth/2, 0);
		back.setPos( new FPoint(camPos.x - Cons.segWidth/2, camPos.y) );
		surfer.setPos(config.width/2*Cons.segWidth, config.height*Cons.segHeight);
		gamestate = GS_WAIT; // normalle = GS_FLY but somehow it SUX
		pleaseEndGame = false;	
		gameMenu.setTime(0);
		gameMenu.setLevelProgress(0);
	}
	
	public void startRun() {
		string0 = ""; string1= ""; recString = "";	newLevelString = "";
		killedAliens = 0;
		gamestate = GS_RUN;		
		back.setPos( new FPoint(camPos.x - Cons.segWidth/2, camPos.y) );
		startTime = System.nanoTime();
	}
	
	public void endGame(boolean restart) {
		
		if (restart) {
			string0 = activityContext.getResources().getString(R.string.restart);;
		} else {		
			float timeS = (System.nanoTime() - startTime) * 1.0e-9f;
			timeS = (float)Math.round(timeS*100)/100f;
		
			s0timer = false; s1timer = false;
			string0 = activityContext.getResources().getString(R.string.time) + ": " + timeS + "s";
			string1 = activityContext.getResources().getString(R.string.killed) + ": " + killedAliens;
			
			this.saveResult(timeS, killedAliens);
			}
		
		startFly();
	}
	
	public void render() {
		actualizeCamPos();
		
		Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, -camPos.x, -camPos.y, 0.0f);		
		back.render(mModelMatrix, mVMatrix, mProjMatrix);		
		
		drawSegments( Math.round( camPos.x / Cons.segWidth ) - Cons.xViewSize, 
				Math.round( camPos.x / Cons.segWidth ) + Cons.xViewSize,
				Math.round( camPos.y / Cons.segHeight ) - Cons.yViewSize,
				Math.round( camPos.y / Cons.segHeight ) + Cons.yViewSize );
        
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, -camPos.x, -camPos.y, 0.0f);		
		surfer.render(mModelMatrix, mVMatrix, mProjMatrix);
		
		if (newLevelString.length() > 0) {
			Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0, 0, -2.5f, 0);
			text.showText( new FPoint(0,0), newLevelString, Cons.inGameFontSize - 0.2f, 
							1f, 1f, 1f, mModelMatrix, mVMatrix, mProjMatrix);
		}

		if (recString.length() > 0) {
			Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0, 0, 1.5f, 0);
			text.showText( new FPoint(0,0), recString, Cons.inGameFontSize + 0.3f, 
							0f, 1f, 0f, mModelMatrix, mVMatrix, mProjMatrix);
		}
		
		if (string0.length() > 0) {		
			Matrix.setIdentityM(mModelMatrix, 0);
			text.showText( new FPoint(0,0), string0, Cons.inGameFontSize, 
							1.0f, 0f, 0f, mModelMatrix, mVMatrix, mProjMatrix);
		}
		
		if (string1.length() > 0) {		
			Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0, 0, -1.5f, 0.0f);	
			text.showText( new FPoint(0,0), string1, Cons.inGameFontSize, 
							1.0f, 1.0f, 0f, mModelMatrix, mVMatrix, mProjMatrix);
		}		
			
		gameMenu.render(mVMatrix, mProjMatrix);
	
	}
	
	public void update(float delta) {
	if (pleaseEndGame) endGame(true);
	if (gamestate != GS_PAUSE) {
				
		if (gamestate == GS_RUN) {
			
			gameMenu.setTime( (System.nanoTime() - startTime) * 1.0e-9f );
			gameMenu.setLevelProgress( (config.height * Cons.segHeight - surfer.getPos().y) / config.height * Cons.segHeight);

			surfer.changeDirection(activityContext.pitch);
			
			surfer.update(delta);
		
			// surfer is not allowed to leave the lecture room
			if ( surfer.getPos().x - surfer.getSize().x / 2 < 0)  
				surfer.setPosX( surfer.getSize().x / 2 );

			if ( surfer.getPos().x + surfer.getSize().x / 2 > config.width * Cons.segWidth)  
				surfer.setPosX( config.width * Cons.segWidth - surfer.getSize().x / 2 );
		
		// surfer is at the bottom
		if (surfer.getPos().y <= 0) endGame(false);
		}
		
		if (s0timer && System.nanoTime() - s0startTime >= s0duration) {
			string0 = "";
			s0timer = false;
		}
		if (s1timer && System.nanoTime() - s1startTime >= s1duration) {
			string1 = "";
			s1timer = false;
		}
		
		back.update(camPos);
	}
	}
	
	
	public void drawSegments(int fx, int tx, int fy, int ty) {
		int itemID = -1;
		int roundKillCount = killedAliens;
		
		if (fx < 0) fx = 0;
		if (tx > config.width-1) tx = config.width - 1;
		if (fy < 0) fy = 0;
		if (ty > config.height-1) ty = config.height - 1;
		
		surfer.setOnBlood(false);
		for (int x = fx; x <= tx; x++)
			for (int y = ty; y >= fy; y--) {
				
				// if powerup boom all aliens and obstacles will be smashed
				if (boom && segments[x][y].hasStudent) {
					spawnBlood( x, y, 1, 2, config.bloodspawn_p);
					segments[x][y].hasStudent = false;
					segments[x][y].smashObstacle();
					killedAliens++;
				} else {				
				
				// check if alien has been smashed				
				if ( !surfer.isJumping() && segments[x][y].quadIntersect(surfer) ) {
					if (segments[x][y].hasObstacle) {
						if (!surfer.afterJumpState())
							if (surfer.getSpeed() <= Cons.maxSpeed)
								surfer.setSpeed(Cons.minSpeed);
						segments[x][y].smashObstacle();
					}
					
					if (segments[x][y].hasStudent) {
						spawnBlood( x, y, 1, 2, config.bloodspawn_p);
						segments[x][y].hasStudent = false;
						killedAliens++;
						soundPlayer.play(Cons.SND_SPLAT, (float)(0.5 + Math.random()));
						} else if (segments[x][y].getItemID() > -1) {
							itemID = segments[x][y].getItemID();
							segments[x][y].setItemID(-1);
							}	
				}
				
				if ( segments[x][y].blood && segments[x][y].quadIntersect(surfer) )
					surfer.setOnBlood(true);
				
				// draw the chair
                Matrix.setIdentityM(mModelMatrix, 0);
                Matrix.translateM(mModelMatrix, 0, -camPos.x, -camPos.y, 0.0f);
        		segments[x][y].render(mModelMatrix, mVMatrix, mProjMatrix); 
				}
			}
		
		if (boom) boom = false;
		
		if (itemID == Cons.SID_ITEM_BOOM) {
			boom = true;
			soundPlayer.play(Cons.SND_EXPLOSION, (float)(0.8 + (Math.random()/2)));
		}
		else
		if (itemID == Cons.SID_ITEM_BIGJUMP) surfer.setItemID(Cons.SID_ITEM_BIGJUMP);
		else
		if (itemID == Cons.SID_ITEM_SPEED) surfer.setItemID(Cons.SID_ITEM_SPEED);
		
		checkForSpecialKill(killedAliens - roundKillCount);
		
		}
	
	public void actualizeCamPos() { 
		if (gamestate == GS_FLY) {
			camPos.y += camFly;
			if (camPos.y >= (config.height * Cons.segHeight) ) gamestate = GS_WAIT;
		}
		
		if (gamestate == GS_RUN || gamestate == GS_WAIT) {
			camPos = surfer.getPos().add(camOffset);
		}
	}
	
	// spawns blood around the chair on position x and y 
	// with dx is the x "radius" and dy the y "radius"
	// and p the blood spawn probability
	public void spawnBlood(int x, int y, int dx, int dy, float p) {
		int fx, tx, fy, ty;
		
		fx = x - dx;
		tx = x + dx;
		fy = y - dy;
		ty = y + dy;

		if (fx < 0) fx = 0;
		if (tx >= config.width) tx = config.width - 1;
		if (fy < 0) fy = 0;
		if (ty >= config.height) ty = config.height - 1;
		
		for (int i = fx; i <= tx; i++) 
			for (int j = fy; j <= ty; j++)
				if (Math.random() >= p) segments[i][j].blood = true;	
	}
	
	public void touched(FPoint pos) {
		int h = gameMenu.touched(pos);
		
		if (h == 1) this.pause();
		else
		if (h == 2) this.resume();
		else
		if (h == 3) {
			renderer.backPressed();
			renderer.ignoreTouch(500000000);
		}
		else
		if (h == 4) pleaseEndGame = true;
		else {
			if (gamestate == GS_WAIT) startRun();
			if (gamestate == GS_RUN) surfer.jump();
			//if (gamestate == GS_PAUSE) this.resume();
		}
	}	
	
	public void checkForSpecialKill(int count) {
		boolean newString = false;
		
		if (count > 10) {
			string1 = "HYPER KILL!";
			newString = true;
		}
		else
		if (count > 5) {
			string1 = "Mega kill!";
			newString = true;
		}
		if (count > 3) {
			string1 = "Multi kill!";
			newString = true;
		}
		else
		if (count == 3) {
			string1 = "Triple kill!";
			newString = true;
		}
		
		if (newString) {
			s1timer = true;
			s1duration = 800000000;
			s1startTime = System.nanoTime();
		}
	}
	
	// checks if results are better than 
	// the actually saved ones and saves them
	public void saveResult(float time, int killCount) {
		SharedPreferences sharedPref = activityContext.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		// update best time
		String timeString = Cons.S_BEST_TIME + levelID;		
		float oldTime = sharedPref.getFloat( timeString, 0f );		
		if (oldTime > time || oldTime == 0) {
			recString = activityContext.getResources().getString(R.string.new_record);
			editor.putFloat(timeString, time);
			editor.commit();
		}
		
		// update gloabl killcount
		int oldKillCount = sharedPref.getInt( Cons.S_KILLCOUNT, 0);
		editor.putInt(Cons.S_KILLCOUNT, oldKillCount + killCount);
		editor.commit();
		
		for (int i : Cons.levelCosts) 
			if (oldKillCount < i && oldKillCount + killCount >= i)
				newLevelString = activityContext.getResources().getString(R.string.level_unlocked);
	}
	
	public FPoint getCamPos() { return camPos; }

	public void setProjMatrix(float[] mProjMatrix) { this.mProjMatrix = mProjMatrix; }
	
	public void pause() {
		pauseStartTime = System.nanoTime();
		gameMenu.pause();
		string0 = activityContext.getResources().getString(R.string.pause);
		stateBeforePause = gamestate;
		gamestate = GS_PAUSE;
	}
	
	public void resume() { 
		gamestate = stateBeforePause;
		string0 = ""; recString = ""; newLevelString = "";
		startTime += (System.nanoTime() - pauseStartTime);
		if (gamestate == GS_WAIT) string1 = activityContext.getResources().getString(R.string.touch_to_start);
	}
	
	public int getID() { return this.levelID; }
}
