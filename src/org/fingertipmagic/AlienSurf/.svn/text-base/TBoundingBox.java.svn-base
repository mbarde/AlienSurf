package org.fingertipmagic.AlienSurf;

public class TBoundingBox {
	
	protected FPoint pos, size;
	
	public FPoint getPos() { return pos; };
	public void setPos(float x, float y) { pos = new FPoint(x, y); };
	public FPoint getSize() { return size; };	
	
	public TBoundingBox(FPoint pos, FPoint size) {
		this.pos = pos;
		this.size = size;
	}
	
	public TBoundingBox(float x, float y, float width, float height) {
		pos = new FPoint(x, y);
		size = new FPoint(width, height);
	}
	
	public boolean isPointInside(FPoint p) {
		return ( Math.abs(p.x - pos.x) <= size.x )
				&& ( Math.abs(p.y - pos.y) <= size.y );
	}
	
	public boolean quadIntersect(TBoundingBox box) {
		float xDist = Math.abs( pos.x - box.getPos().x );
		float yDist = Math.abs( pos.y - box.getPos().y );
		
		return ( xDist <= (size.x/2) + (box.getSize().x/2) )
				  && ( yDist <= (size.y/2) + (box.getSize().y/2) );
	}
	
}
