package org.fingertipmagic.AlienSurf;

public class SquareAdapter {
	public float width, height;
	public int texID;		
	public float[] textureCoordData = null;

	public SquareAdapter(float width, float height, int texID) {
		this.width = width;
		this.height = height;
		this.texID = texID;
	}

	public SquareAdapter(	float width, float height, int texID,		
			float x0, float y0, float x1, float y1,
			float x2, float y2, float x3, float y3) {
		this(width, height, texID);
		textureCoordData = new float[8];
		textureCoordData[0] = x0; textureCoordData[1] = y0;
		textureCoordData[2] = x1; textureCoordData[3] = y1;
		textureCoordData[4] = x2; textureCoordData[5] = y2;
		textureCoordData[6] = x3; textureCoordData[7] = y3;
	}

}
