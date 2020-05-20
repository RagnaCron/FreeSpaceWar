package dev.setmanu.spacewar.gameOverScreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dev.setmanu.spacewar.R;
import dev.setmanu.spacewar.game.GameScreen;
import dev.setmanu.spacewar.menuScreen.MenuScreen;

public class GameOverScreen extends Activity implements View.OnClickListener {

    private Button menuButton;
    private Button warButton;

    private TextView bulletsShot;
    private TextView missedEnemies;
    private TextView enemiesKilled;
    private TextView highScoreTextView;

    private SharedPreferences highScoreLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.over_screen);

        menuButton = findViewById(R.id.over_menu_button);
        warButton = findViewById(R.id.next_war_button);
        menuButton.setOnClickListener(this);
        warButton.setOnClickListener(this);

        bulletsShot = findViewById(R.id.shots_fired_score_label);
        missedEnemies = findViewById(R.id.missed_enemies_score_label);
        enemiesKilled = findViewById(R.id.killed_enemies_score_label);
        highScoreTextView = findViewById(R.id.show_high_score_label);

        highScoreLoad = getSharedPreferences("HIGH_SCORE_TABLE", Context.MODE_PRIVATE);

        loadHighScore();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.over_menu_button:
                goToMenu();
                break;
            case R.id.next_war_button:
                startNewWar();
                break;
        }
    }

    private void goToMenu(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }

    private void startNewWar(){
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }

    private void loadHighScore() {
        long bullets = highScoreLoad.getLong("shots", 0);
        long missed = highScoreLoad.getLong("missed", 0);
        long killed = highScoreLoad.getLong("killed", 0);
        long highScore = killed - (bullets / 5) + (missed / 3);
        bulletsShot.setText(String.valueOf(highScoreLoad.getLong("shots", 0)));
        missedEnemies.setText(String.valueOf(highScoreLoad.getLong("missed", 0)));
        enemiesKilled.setText(String.valueOf(highScoreLoad.getLong("killed", 0)));
        highScoreTextView.setText(String.valueOf(highScore));
    }
}
