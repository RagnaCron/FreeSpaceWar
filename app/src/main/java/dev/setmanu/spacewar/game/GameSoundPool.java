package dev.setmanu.spacewar.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import dev.setmanu.spacewar.R;

public final class GameSoundPool {

    private static GameSoundPool INSTANCE;

    private final static int MAXIMUM_STREAMS = 60;
    private final static int PRIORITY = 1;
    private final static float RATE = 1f;
    private final static int NO_LOOP = 0;
    private final static int INITIAL_VOLUME = 50;

    private int explosionSoundID;
    private int laserShotID;
    private boolean isSoundPoolLoaded;
    private SoundPool soundPool;

    private boolean hasBackgroundSound;
    private float backgroundVolume;
    private boolean hasShootingSound;
    private float shootingVolume;
    private boolean hasExplosionSound;
    private float explosionVolume;

    SharedPreferences settings;

    private MediaPlayer backgroundSound;

    public static synchronized GameSoundPool getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new GameSoundPool(context);
        return INSTANCE;
    }

    private GameSoundPool(Context context) {
        settings = context.getSharedPreferences("SOUND_SETTINGS", Context.MODE_PRIVATE);
        loadSoundSettings();

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
        });

        explosionSoundID = soundPool.load(context, R.raw.explosion_1, PRIORITY);
        laserShotID = soundPool.load(context, R.raw.laser1, PRIORITY);
    }

    public void loadSoundSettings() {
        hasBackgroundSound = (settings.getBoolean("background_sound", true));
        backgroundVolume = (settings.getInt("background_volume", INITIAL_VOLUME) / 100f);
        hasShootingSound = (settings.getBoolean("shooting_sound", true));
        shootingVolume = (settings.getInt("shooting_volume", INITIAL_VOLUME) / 100f);
        hasExplosionSound = (settings.getBoolean("explosion_sound", true));
        explosionVolume = (settings.getInt("explosion_volume", INITIAL_VOLUME) / 100f);
    }

    public void playBackGroundSound() {
        if (!backgroundSound.isPlaying() && hasBackgroundSound) {
            backgroundSound.setVolume(backgroundVolume, backgroundVolume);
            backgroundSound.start();
        }
    }

    public void stopBackGroundSound() {
        if (backgroundSound.isPlaying()) {
            backgroundSound.stop();
            backgroundSound.prepareAsync();
        }

    }

    public void playExplosionSound() {
        if (isSoundPoolLoaded && hasExplosionSound) {
            soundPool.play(explosionSoundID, explosionVolume, explosionVolume, PRIORITY, NO_LOOP, RATE);
        }
    }

    public void playLaserShotSound() {
        if (isSoundPoolLoaded && hasShootingSound) {
            soundPool.play(laserShotID, shootingVolume, shootingVolume, PRIORITY, NO_LOOP, RATE);
        }
    }

    public void releaseSoundPool() {
        backgroundSound.stop();
        backgroundSound = null;
        soundPool.release();
        soundPool = null;
    }

}
