package org.fingertipmagic.AlienSurf;

import de.funpic.mistavista.AlienSurf.R;
import android.content.Context;

public class TTextureManager {

	public int[] textures;
	private Context activityContext;
	
	public TTextureManager(Context activityContext) {
		this.activityContext = activityContext;
		loadTextures();
	}
	
	public int getTexture(int id) {
		if (id >= 0 && id < textures.length) return textures[id];
		else return -1;
	}
	
	private void loadTextures() {
		textures = new int[Cons.TextureCount];
		textures[Cons.TID_CHAIR] = TextureHelper.loadTexture(activityContext, R.drawable.chair);
		textures[Cons.TID_BLOOD] = TextureHelper.loadTexture(activityContext, R.drawable.blood);
		textures[Cons.TID_ALIEN] = TextureHelper.loadTexture(activityContext, R.drawable.student);
		textures[Cons.TID_CACTUS] = TextureHelper.loadTexture(activityContext, R.drawable.cactus);
		textures[Cons.TID_SMASHED_CACTUS] = TextureHelper.loadTexture(activityContext, R.drawable.smashed_cactus);
		textures[Cons.TID_ALIEN_WATER] = TextureHelper.loadTexture(activityContext, R.drawable.alien_water);
		textures[Cons.TID_ALIEN_YELLOW] = TextureHelper.loadTexture(activityContext, R.drawable.alien_yellow);
		textures[Cons.TID_SURFER] = TextureHelper.loadTexture(activityContext, R.drawable.surfer);
		textures[Cons.TID_JUMP] = TextureHelper.loadTexture(activityContext, R.drawable.surfer_jump);
		textures[Cons.TID_GRASS] = TextureHelper.loadTexture(activityContext, R.drawable.grass);
		textures[Cons.TID_DESERT] = TextureHelper.loadTexture(activityContext, R.drawable.desert);
		textures[Cons.TID_STONE] = TextureHelper.loadTexture(activityContext, R.drawable.stone);
		textures[Cons.TID_SPACESHIP] = TextureHelper.loadTexture(activityContext, R.drawable.spaceship);
		textures[Cons.TID_OCEAN] = TextureHelper.loadTexture(activityContext, R.drawable.ocean);
		textures[Cons.TID_FONT] = TextureHelper.loadTexture(activityContext, R.drawable.font);
		textures[Cons.TID_MAIN_BACKGROUND] = TextureHelper.loadTexture(activityContext, R.drawable.main_background);
		
		textures[Cons.TID_ITEM_BOOM] = TextureHelper.loadTexture(activityContext, R.drawable.item_boom);
		textures[Cons.TID_ITEM_BIGJUMP] = TextureHelper.loadTexture(activityContext, R.drawable.item_bigjump);
		textures[Cons.TID_ITEM_SPEED] = TextureHelper.loadTexture(activityContext, R.drawable.item_speed);

		textures[Cons.TID_ICON_PAUSE] = TextureHelper.loadTexture(activityContext, R.drawable.icon_pause);
		textures[Cons.TID_ICON_RESTART] = TextureHelper.loadTexture(activityContext, R.drawable.icon_restart);
		textures[Cons.TID_ICON_PLAY] = TextureHelper.loadTexture(activityContext, R.drawable.icon_play);
		textures[Cons.TID_ICON_EXIT] = TextureHelper.loadTexture(activityContext, R.drawable.icon_exit);
		textures[Cons.TID_SIDEBAR] = TextureHelper.loadTexture(activityContext, R.drawable.sidebar);
		textures[Cons.TID_SIDEBAR_DOT] = TextureHelper.loadTexture(activityContext, R.drawable.sidebar_dot);

		textures[Cons.TID_SURFER_STAND] = TextureHelper.loadTexture(activityContext, R.drawable.surfer_stand);
		textures[Cons.TID_SURFER_STEP01] = TextureHelper.loadTexture(activityContext, R.drawable.surfer_step01);
		textures[Cons.TID_SURFER_STEP02] = TextureHelper.loadTexture(activityContext, R.drawable.surfer_step02);
		textures[Cons.TID_SURFER_STEP03] = TextureHelper.loadTexture(activityContext, R.drawable.surfer_step03);
		
		textures[Cons.TID_ICON_HELPSCREEN] = TextureHelper.loadTexture(activityContext, R.drawable.icon_help);
		textures[Cons.TID_ICON_AUDITORIUM] = TextureHelper.loadTexture(activityContext, R.drawable.icon_auditorium);
		textures[Cons.TID_ICON_DESERT] = TextureHelper.loadTexture(activityContext, R.drawable.icon_desert);
		textures[Cons.TID_ICON_OCEAN] = TextureHelper.loadTexture(activityContext, R.drawable.icon_ocean);		
		textures[Cons.TID_ICON_VALLEY] = TextureHelper.loadTexture(activityContext, R.drawable.icon_valley);
		textures[Cons.TID_ICON_SPACESHIP] = TextureHelper.loadTexture(activityContext, R.drawable.icon_spaceship);
	}

}
