// some kind of wrapper around the SoundPool

package org.fingertipmagic.AlienSurf;

import de.funpic.mistavista.AlienSurf.R.raw;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

public class TSoundPlayer {
	private Context context;
	private SoundPool soundPool;
	private boolean loaded = false;
	
	public TSoundPlayer(Context context) {
		this.context = context;
	    // Load the sound
	    soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	    soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
	      @Override
	      public void onLoadComplete(SoundPool soundPool, int sampleId,
	          int status) {
	    	  loaded = true;
	      }
	    });
	    loadSounds();
	}
	
	private void loadSounds() {
		Cons.SND_SPLAT = soundPool.load(context, raw.splat, 1);
		Cons.SND_EXPLOSION = soundPool.load(context, raw.syn_exp, 1);
	}
	
	public void play(int soundID, float rate) {
		// Getting the user sound settings
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		float actualVolume = (float) audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float) audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = actualVolume / maxVolume;
		// Is the sound loaded already?
		if (loaded) soundPool.play(soundID, volume, volume, 1, 0, rate);
	}

}
