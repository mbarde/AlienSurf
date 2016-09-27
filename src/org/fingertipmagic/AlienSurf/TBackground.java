package org.fingertipmagic.AlienSurf;

import android.opengl.Matrix;

public class TBackground {
	
	private TSquareManager squareMan;
	private int sidBack;
	private FPoint pos;
	
	// defines the maximal distance between the centre of the background
	// and the cam position. if this distance is passed change the position
	// of the background
	private float maxDist, top, bottom;
	
	public TBackground(TSquareManager squareMan, int sidBack, float x, float y, float maxDist, float top, float bottom) {
		this.squareMan = squareMan;
		this.sidBack = sidBack;
		this.maxDist = maxDist;
		this.top = top - (squareMan.getSquareAdapter(sidBack).height / 2);
		this.bottom = bottom + (squareMan.getSquareAdapter(sidBack).height / 2);
		pos = new FPoint(x, y);
		checkPos();
	}
	
	public void update(FPoint camPos) {
		while (pos.y - camPos.y > maxDist) pos.y -= maxDist;
		while (camPos.y - pos.y > maxDist) pos.y += maxDist;
		checkPos();
	}
	
	public void render(float[] mModelMatrix, float[] mVMatrix, float[] mProjMatrix) {
        Matrix.translateM(mModelMatrix, 0, pos.x, pos.y, 0.0f);
		squareMan.renderSquare(sidBack, mModelMatrix, mVMatrix, mProjMatrix);
	}
	
	private void checkPos() {
		if (pos.y < bottom) pos.y = bottom;
		if (pos.y > top) pos.y = top;
	}
	
	public void setPos(FPoint pos) { 
			this.pos = new FPoint(pos.x, pos.y);
			checkPos();
	}
	
	public FPoint getPos() { return pos; }
}
