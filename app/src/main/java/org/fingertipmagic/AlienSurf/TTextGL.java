package org.fingertipmagic.AlienSurf;

import android.opengl.Matrix;

public class TTextGL{
	
	private SquareAdapter[] squares;
	private Square square;
	private float width = 490;
	private float height = 678;
	
	private float[] hModelMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];
	
	public TTextGL(int texture, Square square) {
		generateSquares(texture, width, height);
		this.square = square;
	}
	
	private void generateSquares(int texture, float width, float height) {
		squares = new SquareAdapter[70];
		
		for (int i = 0; i<70; i++) {
			float x = 1/10f * (i % 10);
			float y = 1/7f * (float)Math.floor(i / 10);
									
			squares[i] = new SquareAdapter(1, 1, texture,
					x, y,
					x, y+1/7f,
					x+1/10f, y+1/7f,
					x+1/10f, y ); 
		}
	}
	
	// shows text, cPos is the center position
	public void showText(FPoint cPos, String text, float fsize, 
						float clr_r, float clr_g, float clr_b,
						float[] mModelMatrix, float[] mVMatrix, float[] mProjMatrix) 
	{
		// init pos and translate to the pos
		FPoint pos = new FPoint(cPos.x - text.length()*fsize/2, cPos.y);
		
		Matrix.translateM(mModelMatrix, 0, pos.x, pos.y, 0.0f);
		Matrix.scaleM(mModelMatrix, 0, fsize, fsize, 1);
		
		int nr;
		
		// map ASCII characters to our character map
		for (int i = 0; i < text.length(); i++) {
			int c = text.charAt(i);
			
			// big, small, special, numbers
			if (c >= 65 && c <= 90) nr = c - 65;
				else
			if (c >= 97 && c <= 122) nr = c - 97 + 26;
				else
					
			if ( text.charAt(i) == 'ä') nr = 52;
				else
			if ( text.charAt(i) == 'ö') nr = 53;
				else
			if ( text.charAt(i) == 'ü') nr = 54;
				else
			if ( text.charAt(i) == '.') nr = 55;
				else
			if ( text.charAt(i) == ',') nr = 56;
				else
			if ( text.charAt(i) == '-') nr = 57;
				else
			if ( text.charAt(i) == '!') nr = 58;
				else
			if ( text.charAt(i) == ':') nr = 59;
				else
			if ( c >= 48 && c <= 57) nr = 60 + c - 48;
				else
					nr = -1;
			
			if (nr > -1) {
				square.setColor(clr_r, clr_g, clr_b);
				drawSquareAdapter(squares[nr], mModelMatrix, mVMatrix, mProjMatrix);								
				square.setColor(1, 1, 1);
			}
			
			// just translate one step to the right
			Matrix.translateM(mModelMatrix, 0, 1, 0, 0.0f);
		}
		
	}
	
	private void drawSquareAdapter(SquareAdapter sq,  float[] mModelMatrix, float[] mVMatrix, float[] mProjMatrix) {	
		hModelMatrix = mModelMatrix.clone();
		Matrix.scaleM(hModelMatrix, 0, sq.width, sq.height, 0);
		Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, hModelMatrix, 0); 
		Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
		square.setTextureHandle(sq.texID);
		if (sq.textureCoordData != null) square.setTexCoords(sq.textureCoordData);
		square.draw(mMVPMatrix);
		if (sq.textureCoordData != null) square.setTexCoords(0,0, 0,1, 1,1, 1,0);
	}
	
}