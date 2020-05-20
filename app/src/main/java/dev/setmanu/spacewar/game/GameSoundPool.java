package dev.setmanu.spacewar.game;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import dev.setmanu.spacewar.R;

public final class GameSoundPool {

    private static GameSoundPool INSTANCE;

    private final static int MAXIMUM_STREAMS = 60;
    private final static int PRIORITY_LOW = 1;
    private final static int PRIORITY_HIGH = 2;
    private final static float RATE = 1f;
    private final static int NO_LOOP = 0;
    private final static float VOLUME = 0.3f;

    private int explosionSoundID;
    private int laserShotID;
    private boolean isSoundPoolLoaded;
    private SoundPool soundPool;

    private MediaPlayer backgroundSound;

    public static synchronized GameSoundPool getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new GameSoundPool(context);
        return INSTANCE;
    }

    private GameSoundPool(Context context) {
        backgroundSound = MediaPlayer.create(context, R.raw.preparing_for_war);
        backgroundSound.setLooping(true);

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(attributes).setMaxStreams(MAXIMUM_STREAMS);
        soundPool = builder.build();

        this.soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            isSoundPoolLoaded = true;
            playBackGroundSound();
        });

        explosionSoundID = soundPool.load(context, R.raw.explosion_1, PRIORITY_HIGH);
        laserShotID = soundPool.load(context, R.raw.laser1, PRIORITY_HIGH);
    }

    public void playBackGroundSound() {
        if (!backgroundSound.isPlaying())
            backgroundSound.start();
    }

    public void stopBackGroundSound() {
        backgroundSound.stop();
        backgroundSound.prepareAsync();
    }

    public void playExplosionSound() {
        if (isSoundPoolLoaded) {
            soundPool.play(explosionSoundID, VOLUME, VOLUME, PRIORITY_LOW, NO_LOOP, RATE);
        }
    }

    public void playLaserShotSound() {
        if (isSoundPoolLoaded) {
            soundPool.play(laserShotID, VOLUME, VOLUME, PRIORITY_LOW, NO_LOOP, RATE);
        }
    }

    public void releaseSoundPool() {
        backgroundSound.stop();
        backgroundSound = null;
        soundPool.release();
        soundPool = null;
    }

}
