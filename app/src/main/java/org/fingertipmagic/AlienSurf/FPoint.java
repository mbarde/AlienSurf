package org.fingertipmagic.AlienSurf;

public class FPoint {
	
	public float x;
	public float y;
	
	public FPoint(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public FPoint add(FPoint p) {
		return new FPoint(x + p.x, y + p.y);
	}
	
	public FPoint scale(float factor) {
		return new FPoint(x * factor, y * factor);
	}
	
	public void normalize() {
		float norm = (float) Math.sqrt(x*x + y*y);
		this.x = scale(1/norm).x;
		this.y = scale(1/norm).y;
	}

}
