package dev.setmanu.freespacewar.settingsScreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import dev.setmanu.freespacewar.R;
import dev.setmanu.freespacewar.menuScreen.MenuScreen;

public class SettingsScreen extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private final static int INITIAL_VOLUME = 50;

    private SharedPreferences settings;

    private Switch backgroundSound;
    private SeekBar backgroundVolume;
    private Switch shootingSound;
    private SeekBar shootingVolume;
    private Switch explosionSound;
    private SeekBar explosionVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        initSettingsInterface();

        settings = getSharedPreferences("SOUND_SETTINGS", Context.MODE_PRIVATE);

        loadSettings();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                startActivity(new Intent(this, MenuScreen.class));
                break;
            case R.id.save_button:
                saveSettings();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.background_sound:
                backgroundVolume.setEnabled(isChecked);
                break;
            case R.id.shooting_sound:
                shootingVolume.setEnabled(isChecked);
                break;
            case R.id.explosion_sound:
                explosionVolume.setEnabled(isChecked);
                break;
        }
    }

    private void loadSettings() {
        backgroundSound.setChecked(settings.getBoolean("background_sound", true));
        backgroundVolume.setProgress(settings.getInt("background_volume", INITIAL_VOLUME));
        shootingSound.setChecked(settings.getBoolean("shooting_sound", true));
        shootingVolume.setProgress(settings.getInt("shooting_volume", INITIAL_VOLUME));
        explosionSound.setChecked(settings.getBoolean("explosion_sound", true));
        explosionVolume.setProgress(settings.getInt("explosion_volume", INITIAL_VOLUME));
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("background_volume", backgroundVolume.getProgress());
        editor.putBoolean("background_sound", backgroundSound.isChecked());
        editor.putInt("shooting_volume", shootingVolume.getProgress());
        editor.putBoolean("shooting_sound", shootingSound.isChecked());
        editor.putInt("explosion_volume", explosionVolume.getProgress());
        editor.putBoolean("explosion_sound", explosionSound.isChecked());
        editor.apply();
    }

    private void initSettingsInterface() {
        findViewById(R.id.back_button).setOnClickListener(this);
        findViewById(R.id.save_button).setOnClickListener(this);
        backgroundSound = findViewById(R.id.background_sound);
        backgroundSound.setOnCheckedChangeListener(this);
        backgroundVolume = findViewById(R.id.background_volume);
        shootingSound = findViewById(R.id.shooting_sound);
        shootingSound.setOnCheckedChangeListener(this);
        shootingVolume = findViewById(R.id.shooting_volume);
        explosionSound = findViewById(R.id.explosion_sound);
        explosionSound.setOnCheckedChangeListener(this);
        explosionVolume = findViewById(R.id.explosion_volume);
    }

}
