package org.fingertipmagic.AlienSurf;

import android.opengl.Matrix;

// loads and handles all quads
// which will be used more than 
// one time

public class TSquareManager {
	
	private float[] hModelMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];
	private Square square;
	private SquareAdapter[] squares, icons;
	private TTextureManager texMan;
	
	public TSquareManager(TTextureManager texMan, Square square) {
		this.texMan = texMan;
		if (square == null) this.square = new Square(0,0,0, 1,1, 0);
		else this.square = square;
		loadSquares();
	}
	
	private void loadSquares() {
		squares = new SquareAdapter[Cons.squareCount];
		
		squares[Cons.SID_SURFER] = new SquareAdapter(Cons.surferWidth, Cons.surferHeight, 
										texMan.getTexture(Cons.TID_SURFER) );
		squares[Cons.SID_SURFER_JUMP] = new SquareAdapter(Cons.surferWidth, 
									Cons.surferHeight + (Cons.surferHeight/2), 
									texMan.getTexture(Cons.TID_JUMP) );
		
		squares[Cons.SID_BLOOD] = new SquareAdapter(Cons.segWidth,Cons.segHeight, 
									texMan.getTexture(Cons.TID_BLOOD));
		squares[Cons.SID_ALIEN] = new SquareAdapter(Cons.segWidth,Cons.segHeight, 
									texMan.getTexture(Cons.TID_ALIEN));
		squares[Cons.SID_CACTUS] = new SquareAdapter(Cons.segWidth,Cons.segHeight, 
									texMan.getTexture(Cons.TID_CACTUS));
		squares[Cons.SID_SMASHED_CACTUS] = new SquareAdapter(Cons.segWidth,Cons.segHeight, 
									texMan.getTexture(Cons.TID_SMASHED_CACTUS));
		squares[Cons.SID_ALIEN_WATER] = new SquareAdapter(Cons.segWidth,Cons.segHeight*2, 
									texMan.getTexture(Cons.TID_ALIEN_WATER));
		squares[Cons.SID_ALIEN_YELLOW] = new SquareAdapter(Cons.segWidth,Cons.segHeight, 
									texMan.getTexture(Cons.TID_ALIEN_YELLOW));
		squares[Cons.SID_ITEM_BOOM] = new SquareAdapter(Cons.segWidth,Cons.segHeight,
									texMan.getTexture(Cons.TID_ITEM_BOOM));
		squares[Cons.SID_ITEM_BIGJUMP] = new SquareAdapter(Cons.segWidth,Cons.segHeight, 
									texMan.getTexture(Cons.TID_ITEM_BIGJUMP));
		squares[Cons.SID_ITEM_SPEED] = new SquareAdapter(Cons.segWidth,Cons.segHeight, 
									texMan.getTexture(Cons.TID_ITEM_SPEED));
		squares[Cons.SID_SIDEBAR] = new SquareAdapter(Cons.menuProgressBarWidth,Cons.menuProgressBarHeight, 
									texMan.getTexture(Cons.TID_SIDEBAR));
		squares[Cons.SID_SIDEBAR_DOT] = new SquareAdapter(Cons.menuProgressBarDotRadius,Cons.menuProgressBarDotRadius, 
									texMan.getTexture(Cons.TID_SIDEBAR_DOT));
		squares[Cons.SID_ICON_PAUSE] = new SquareAdapter(Cons.menuIconWidth,Cons.menuIconWidth, 
									texMan.getTexture(Cons.TID_ICON_PAUSE));
		squares[Cons.SID_ICON_RESTART] = new SquareAdapter(Cons.menuIconWidth,Cons.menuIconWidth, 
									texMan.getTexture(Cons.TID_ICON_RESTART));
		squares[Cons.SID_ICON_PLAY] = new SquareAdapter(Cons.menuIconWidth,Cons.menuIconWidth, 
									texMan.getTexture(Cons.TID_ICON_PLAY));
		squares[Cons.SID_ICON_EXIT] = new SquareAdapter(Cons.menuIconWidth,Cons.menuIconWidth, 
									texMan.getTexture(Cons.TID_ICON_EXIT));
		squares[Cons.SID_MAIN_BACKGROUND] = new SquareAdapter(Cons.mainBGWidth, Cons.mainBGHeight,
													texMan.getTexture(Cons.TID_MAIN_BACKGROUND),
													0.0f, 0.0f, 				
													0.0f, 2f,
													4f, 2f,
													4f, 0.0f );
		
		squares[Cons.SID_SURFER_STAND] = new SquareAdapter(Cons.bgSurferSize,Cons.bgSurferSize, 
									texMan.getTexture(Cons.TID_SURFER_STAND));
		squares[Cons.SID_SURFER_STEP01] = new SquareAdapter(Cons.bgSurferSize,Cons.bgSurferSize, 
									texMan.getTexture(Cons.TID_SURFER_STEP01));
		squares[Cons.SID_SURFER_STEP02] = new SquareAdapter(Cons.bgSurferSize,Cons.bgSurferSize, 
									texMan.getTexture(Cons.TID_SURFER_STEP02));
		squares[Cons.SID_SURFER_STEP03] = new SquareAdapter(Cons.bgSurferSize,Cons.bgSurferSize, 
									texMan.getTexture(Cons.TID_SURFER_STEP03));
		
		icons = new SquareAdapter[Cons.levelCount];
		icons[Cons.LID_HELPSCREEN] = new SquareAdapter(Cons.iconWidth,Cons.iconWidth, 
										texMan.getTexture(Cons.TID_ICON_HELPSCREEN));
		icons[Cons.LID_AUDITORIUM] = new SquareAdapter(Cons.iconWidth,Cons.iconWidth, 
										texMan.getTexture(Cons.TID_ICON_AUDITORIUM));
		icons[Cons.LID_DESERT] = new SquareAdapter(Cons.iconWidth,Cons.iconWidth, 
										texMan.getTexture(Cons.TID_ICON_DESERT));
		icons[Cons.LID_OCEAN] = new SquareAdapter(Cons.iconWidth,Cons.iconWidth, 
										texMan.getTexture(Cons.TID_ICON_OCEAN));
		icons[Cons.LID_VALLEY] = new SquareAdapter(Cons.iconWidth,Cons.iconWidth, 
										texMan.getTexture(Cons.TID_ICON_VALLEY));
		icons[Cons.LID_SPACESHIP] = new SquareAdapter(Cons.iconWidth,Cons.iconWidth, 
										texMan.getTexture(Cons.TID_ICON_SPACESHIP));
	}
	
	public boolean renderSquare(int id, float[] mModelMatrix, float[] mVMatrix, float[] mProjMatrix) {
		if (id < 0 || id >= squares.length) return false;
		
		drawSquareAdapter(squares[id], mModelMatrix, mVMatrix, mProjMatrix);
		return true;
	}
	
	public boolean renderLevelIcon(int id, float[] mModelMatrix, float[] mVMatrix, float[] mProjMatrix) {
		if (id < 0 || id >= icons.length) return false;
		
		drawSquareAdapter(icons[id], mModelMatrix, mVMatrix, mProjMatrix);
		return true;
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
	
	public void setSquareAdapter(int id, SquareAdapter sq) {
		if (id < 0 || id >= squares.length || sq == null) return;
		squares[id] = sq;
	}
	
	public SquareAdapter getSquareAdapter(int id) {
		if (id < 0 || id >= squares.length) return null;
		return squares[id];
	}
	
	public SquareAdapter getLevelIcon(int id) {
		if (id < 0 || id >= icons.length) return null;
		return icons[id];
	}
	
	public Square getSquare() {
		return square;
	}
	
}
