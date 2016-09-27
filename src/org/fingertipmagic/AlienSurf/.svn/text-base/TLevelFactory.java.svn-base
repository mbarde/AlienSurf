package org.fingertipmagic.AlienSurf;

import android.content.Context;

public class TLevelFactory {
	
	private AndroidAlienSurfActivity activityContext;
    private float[] mVMatrix = new float[16];
	private float[] mProjMatrix = new float[16];
    
    private TTextureManager texMan;
    private TSquareManager squareMan;
    private TSoundPlayer soundPlayer;
    private MyGLRenderer renderer;
    private TTextGL text;
    
    private String[] levelStrings;
    
    public TLevelFactory(MyGLRenderer renderer, Context activityContext, 
    						TTextureManager texMan,  TSquareManager squareMan, 
    						TSoundPlayer soundPlayer, TTextGL text,
							float[] mVMatrix, float[] mProjMatrix) {
    	this.activityContext = (AndroidAlienSurfActivity)activityContext;
    	this.renderer = renderer;
    	this.mProjMatrix = mProjMatrix;
    	this.mVMatrix = mVMatrix;
    	
    	this.texMan = texMan;
    	this.squareMan = squareMan;
    	this.soundPlayer = soundPlayer;
    	this.text = text;
    	
    	loadLevelStrings();
    }    
    
    public TLevel createLevel(int id) {
    	if (id < 0 || id >= levelStrings.length) return null;
    	TLevelConfig config = new TLevelConfig(levelStrings[id], texMan, squareMan);
    	return new TLevel(config, renderer, activityContext, texMan, squareMan, soundPlayer,
    						text, mVMatrix, mProjMatrix, id + Cons.bonusLevels);    						
    }
	
	// to add a level:
	// update Cons.levelCount
	// add level id in Cons
	// add texture
	// add square
	// add level string in here
    
    private void loadLevelStrings() {
    	levelStrings = new String[Cons.levelCount];
    	// width,height,student_p,bloodspawn_p,itemspawn_p,obstacle_p,
    	// backGroundTexID,alienSquareID,obastacleSquareID	
    	levelStrings[0] = "12,350,0.85,0.4,0.995,1,"+Cons.TID_CHAIR+","+Cons.SID_ALIEN+","+Cons.SID_CACTUS+","+Cons.SID_SMASHED_CACTUS;
    	levelStrings[1] = "30,350,0.9,0.4,0.995,0.97,"+Cons.TID_DESERT+","+Cons.SID_ALIEN_WATER+","+Cons.SID_CACTUS+","+Cons.SID_SMASHED_CACTUS;
    	levelStrings[2] = "20,350,0.9,0.3,0.995,1,"+Cons.TID_OCEAN+","+Cons.SID_ALIEN_WATER+","+Cons.SID_CACTUS+","+Cons.SID_SMASHED_CACTUS;
    	levelStrings[3] = "6,350,0.85,0.1,0.995,0.97,"+Cons.TID_STONE+","+Cons.SID_ALIEN_YELLOW+","+Cons.SID_CACTUS+","+Cons.SID_SMASHED_CACTUS;
    	levelStrings[4] = "12,350,0.8,0.7,0.9,1,"+Cons.TID_SPACESHIP+","+Cons.SID_ALIEN+","+Cons.SID_CACTUS+","+Cons.SID_SMASHED_CACTUS;
    }
    
}
