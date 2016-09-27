package org.fingertipmagic.AlienSurf;

import android.opengl.Matrix;

public class TSurfer extends TBoundingBox {
	
	private float angleFactor = 20;
	private FPoint direction;
	private float speed;
	private boolean onBlood, jump;
	private boolean boost = false;
	private long jumpStart, jumpStop, boostStartTime, boostDuration;
	private boolean jumpEnabled;
	private int jumpWaitTime = 500000000; // nano sec
	private int jumpTime = 450000000;
	private int itemID = -1;
	private float itemXOffset = 1;
	private float itemYOffset = 1;
	private TSquareManager squareMan;
	
	public TSurfer(float x, float y, TSquareManager squareMan) {
		super(x, y, Cons.surferWidth, Cons.surferHeight);		
		this.squareMan = squareMan;		
		size = new FPoint(Cons.surferWidth, Cons.surferHeight);		
		setPos(x,y);
	}
	
	public void init() {
		direction = new FPoint(0, -1);
		speed = 1f;
		onBlood = false;
		jump = false;
		jumpEnabled = true;
		jumpWaitTime = 500000000; // nano sec
		jumpTime = 300000000;
		itemID = -1;
		boost = false;
	}
	
	public void render(float[] mModelMatrix, float[] mVMatrix, float[] mProjMatrix) {
        Matrix.translateM(mModelMatrix, 0, pos.x, pos.y, 0.0f);
    
		if (jump) squareMan.renderSquare(Cons.SID_SURFER_JUMP, mModelMatrix, mVMatrix, mProjMatrix);	
		else squareMan.renderSquare(Cons.SID_SURFER, mModelMatrix, mVMatrix, mProjMatrix);
		
		// draw item
		if (itemID > -1) {
	        Matrix.translateM(mModelMatrix, 0, itemXOffset, itemYOffset, 0.0f);
	        squareMan.renderSquare(itemID, mModelMatrix, mVMatrix, mProjMatrix);
		}
	}
	
	public void update(float delta) {
		pos = pos.add( direction.scale(speed * delta * 0.1f) );
		
		if ( jump && getTime() - jumpStart >= jumpTime ) {
			jump = false;
			jumpEnabled = false;
			jumpStop = getTime();
			if (itemID == Cons.SID_ITEM_BIGJUMP) {
				jumpTime /= 3;
				itemID = -1;
			}
		}
		
		if ( !jump && getTime() - jumpStop >= jumpWaitTime) jumpEnabled = true;
		
		if (!jump) {
			if (onBlood) speed += 0.01f;
			else speed -= 0.02f;
		}
		
		if (speed <= Cons.minSpeed) speed = Cons.minSpeed;
		if (speed > Cons.maxSpeed && !boost) speed = Cons.maxSpeed;
		
		if (boost && System.nanoTime() - boostStartTime >= boostDuration) boost = false;
	}
	
	public void surfRight() { direction.x = 1; }
	
	public void surfLeft() { direction.x = -1; }
	
	public void changeDirection(float value) {
		if (value > angleFactor) value = angleFactor;
		if (value <-angleFactor) value =-angleFactor;
		direction.x = - value / angleFactor;
	}
	
	public void surfDown() { direction.x = 0; }
	
	public void jump() {
		if (itemID == Cons.SID_ITEM_SPEED) {
			speed = 7;
			boost = true;
			boostStartTime = System.nanoTime();
			boostDuration = 300000000;
			itemID = -1;
			return;
		} 
		
		if (!jump && jumpEnabled) {
			if (itemID == Cons.SID_ITEM_BIGJUMP) jumpTime *= 3;
			jumpStart = getTime();
			jump = true;
		}
	}
	
	public long getTime() {
	    return System.nanoTime();
	}
	
	public void setOnBlood(boolean b) { onBlood = b; };
	public boolean isJumping() { return jump; };
	public float getSpeed() { return speed; };
	
	public void setPos(float x, float y) {
		pos = new FPoint(x,y);
		init();
	}
	
	// used to hold surfer in the bounds of the auditorium
	public void setPosX(float x) {
		pos.x = x;
		direction = new FPoint(0, -1);
	}
	
	public FPoint getPos() { return pos; };
	public FPoint getSize() { return size; };
	
	public void setItemID(int itemID) { 
		if (this.itemID == -1 ) this.itemID = itemID;
	}
	
	public void setSpeed(float speed) { this.speed = speed; }
	
	public boolean afterJumpState(){ return !jumpEnabled; }

}
