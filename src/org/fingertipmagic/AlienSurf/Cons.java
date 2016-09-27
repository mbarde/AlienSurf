package org.fingertipmagic.AlienSurf;

public class Cons {
	
	// texture ids
	public static int TextureCount = 35;
	public static int TID_CHAIR = 0;
	public static int TID_BLOOD = 1;
	public static int TID_ALIEN = 2;
	public static int TID_SURFER = 3;
	public static int TID_JUMP = 4;
	public static int TID_FONT = 5;
	public static int TID_ITEM_BOOM = 6;
	public static int TID_ITEM_BIGJUMP = 7;
	public static int TID_ITEM_SPEED = 8;
	public static int TID_GRASS = 9;
	public static int TID_ICON_AUDITORIUM = 10;
	public static int TID_ICON_DESERT = 11;
	public static int TID_ICON_OCEAN = 12;
	public static int TID_DESERT = 13;
	public static int TID_OCEAN = 14;
	public static int TID_ALIEN_WATER = 15;
	public static int TID_ALIEN_YELLOW = 16;
	public static int TID_STONE = 17;
	public static int TID_ICON_VALLEY = 18;
	public static int TID_CACTUS = 19;
	public static int TID_MAIN_BACKGROUND = 20;
	public static int TID_ICON_SPACESHIP = 21;
	public static int TID_SPACESHIP = 22;
	public static int TID_ICON_HELPSCREEN = 23;
	public static int TID_SMASHED_CACTUS = 24;
	public static int TID_ICON_PAUSE = 25;
	public static int TID_ICON_RESTART = 26;
	public static int TID_ICON_PLAY = 27;
	public static int TID_ICON_EXIT = 28;
	public static int TID_SURFER_STAND = 29;
	public static int TID_SURFER_STEP01 = 30;
	public static int TID_SURFER_STEP02 = 31;
	public static int TID_SURFER_STEP03 = 32;
	public static int TID_SIDEBAR = 33;
	public static int TID_SIDEBAR_DOT = 34;
	
	// square ids
	public static int squareCount = 23;
	public static int SID_BLOOD = 0;
	public static int SID_ALIEN = 1;
	public static int SID_ITEM_BOOM = 2;
	public static int SID_ITEM_BIGJUMP = 3;
	public static int SID_ITEM_SPEED = 4;
	public static int SID_ALIEN_WATER = 5;
	public static int SID_ALIEN_YELLOW = 6;
	public static int SID_CACTUS = 7;
	public static int SID_MAIN_BACKGROUND = 8;
	public static int SID_SMASHED_CACTUS = 9;
	public static int SID_ICON_PAUSE = 10;
	public static int SID_ICON_RESTART = 11;
	public static int SID_ICON_PLAY = 12;
	public static int SID_ICON_EXIT = 13;
	public static int SID_SURFER_STAND = 14;
	public static int SID_SURFER_STEP01 = 15;
	public static int SID_SURFER_STEP02 = 16;
	public static int SID_SURFER_STEP03 = 17;
	public static int SID_SIDEBAR = 18;
	public static int SID_SIDEBAR_DOT = 19;
	public static int SID_LEVEL_BACKGROUND = 20;
	public static int SID_SURFER = 21;
	public static int SID_SURFER_JUMP = 22;
	
	// sound ids
	public static int soundCount = 2;
	public static int SND_SPLAT;
	public static int SND_EXPLOSION;
	
	// level ids
	
	public static int levelCount = 6;
	public static int bonusLevels = 1;
	
	public static int LID_HELPSCREEN = 0;
	public static int LID_AUDITORIUM = 1;
	public static int LID_DESERT = 2;	
	public static int LID_OCEAN = 3;
	public static int LID_VALLEY = 4;
	public static int LID_SPACESHIP = 5;	
	
	// level costs
	public static int[] levelCosts = { 0, 0, 0, 0, 0, 0 };
	
	// TODO: medals for times
			
	public static float iconWidth = 6f;
	public static float iconDistance = 2f;
	public static float mainBGWidth = 100f;
	public static float mainBGHeight = 20f;
	public static float mainBGZDistance = 6f;
	
	public static int itemCount = 3;
	public static int squareIDitemOffset = 2;
	
	public static float minSpeed = 0.1f;
	public static float maxSpeed = 2f;
	
	public static float inGameFontSize = 0.7f;
	
	public static float bgSurferSize = 9;
	
	public static boolean HUD_right = false;
	public static float HUD_X = -6;
	public static float menuProgressBarHeight = 3;
	public static float menuProgressBarWidth = 0.25f;
	public static float menuProgressBarDotRadius = 0.5f;
	
	// size of a icon in the in game menu
	public static float menuIconWidth = 2;
	
	// size of a segment of the background
	// (background consists of many segments)
	public static float segWidth = 1;
	public static float segHeight = 1;
	
	// defines the the size of the view sector
	public static int xViewSize = 10;
	public static int yViewSize = 5;	
	
	public static float surferWidth = 2;
	public static float surferHeight = 2;
	
	public static int INT_ERROR = -1337;
	
	public static String S_BEST_TIME = "alienSurf.best.time";
	public static String S_KILLCOUNT = "alienSurf.killCount";

}
