package org.fingertipmagic.AlienSurf;

public class TLevelConfig {
	
	public int width, height, sid_blood, sid_student, sid_obstacle, sid_smashedobstacle;
	public float student_p, bloodspawn_p, itemspawn_p, obstacle_p;
	
	public TLevelConfig(String s, TTextureManager texMan,
						TSquareManager squareMan) {
		loadFromString(s, texMan, squareMan);
	}
	
	// s must look like this:
	// width,height,student_p,bloodspawn_p,itemspawn_p,obstacle_p,
	// backGroundTexID,alienSquareID,obastacleSquareID	
	public boolean loadFromString(String s, TTextureManager texMan,
									TSquareManager squareMan) {
		String[] values = s.split(",");
		if (values.length != 10) return false;
		
		width = Integer.parseInt(values[0]);
		height = Integer.parseInt(values[1]);
		student_p = Float.parseFloat(values[2]);
		bloodspawn_p = Float.parseFloat(values[3]);
		itemspawn_p = Float.parseFloat(values[4]);
		obstacle_p = Float.parseFloat(values[5]);
		int tid_back = Integer.parseInt(values[6]);
		sid_student = Integer.parseInt(values[7]);
		sid_obstacle = Integer.parseInt(values[8]);
		sid_smashedobstacle = Integer.parseInt(values[9]);
		
		float x = width * Cons.segWidth;
		float y = Cons.yViewSize + Cons.segHeight*2;
		
		squareMan.setSquareAdapter(Cons.SID_LEVEL_BACKGROUND,
							new SquareAdapter(x, y * 2 , texMan.getTexture(tid_back),
							0, 0, 0, y, x, y, x, 0) );
		
		return true;
	}
}
