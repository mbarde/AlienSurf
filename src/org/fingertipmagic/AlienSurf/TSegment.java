package org.fingertipmagic.AlienSurf;

import android.opengl.Matrix;

public class TSegment extends TBoundingBox  {

	TSquareManager squareMan;
	private int itemID = -1;
	private TLevelConfig config;
	public boolean blood, hasStudent, hasObstacle, hadObstacle;
	
	public TSegment(float x, float y, float width, float height, 
					TSquareManager squareMan, TLevelConfig config)
	{
		super(x, y, width, height);
		
		this.squareMan = squareMan;
		this.config = config;
		
		pos = new FPoint(x,y);
		
		blood = false;
		hasStudent = false;
		hasObstacle = false;
		hadObstacle = false;
	}
	
	public void render(float[] mModelMatrix, float[] mVMatrix, float[] mProjMatrix) {

		/*
		if (hasObstacle || hasStudent) {
			float[] oldModelMatrix = mModelMatrix;
			Matrix.translateM(mModelMatrix, 0, pos.x, pos.y, 0.0f);
			Matrix.rotateM(mModelMatrix, 0, 30, 1, 0, 0);
			Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mModelMatrix, 0); 
			Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
			if (hasStudent) sqStudent.draw(mMVPMatrix);		
			if (hasObstacle) sqObstacle.draw(mMVPMatrix);
			mModelMatrix = oldModelMatrix;
		} */
		
		Matrix.translateM(mModelMatrix, 0, pos.x, pos.y, 0.0f);
		
		if (hasStudent) squareMan.renderSquare(config.sid_student, mModelMatrix, mVMatrix, mProjMatrix);	
		if (hasObstacle) squareMan.renderSquare(config.sid_obstacle, mModelMatrix, mVMatrix, mProjMatrix);
		if (hadObstacle) squareMan.renderSquare(config.sid_smashedobstacle, mModelMatrix, mVMatrix, mProjMatrix);
		if (blood) squareMan.renderSquare(config.sid_blood, mModelMatrix, mVMatrix, mProjMatrix);
		if (itemID > -1) squareMan.renderSquare(itemID, mModelMatrix, mVMatrix, mProjMatrix);
	}
	
	public void spawnItem() {
		itemID = Cons.squareIDitemOffset + (int)Math.round( Math.random() * (Cons.itemCount-1) );
	}
	
	public int getItemID() { return itemID; }
	public void setItemID(int itemID) { this.itemID = itemID; }
	
	public void smashObstacle() { 
		if (hasObstacle) {
			hasObstacle = false;
			hadObstacle = true;
		}
	}

}
