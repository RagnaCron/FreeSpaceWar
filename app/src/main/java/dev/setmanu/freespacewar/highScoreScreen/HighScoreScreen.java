package dev.setmanu.freespacewar.highScoreScreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import dev.setmanu.freespacewar.R;
import dev.setmanu.freespacewar.menuScreen.MenuScreen;

public class HighScoreScreen extends Activity implements View.OnClickListener {

    private SharedPreferences gameHighScore;

    private TextView shotsTextView;
    private TextView missedTextView;
    private TextView killsTextView;
    private TextView highScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_screen);

        findViewById(R.id.over_menu_button).setOnClickListener(this);

        shotsTextView = findViewById(R.id.shots_fired_score_label);
        missedTextView = findViewById(R.id.missed_enemies_score_label);
        killsTextView = findViewById(R.id.killed_enemies_score_label);
        highScoreTextView = findViewById(R.id.show_high_score_label);

        gameHighScore = getSharedPreferences("HIGH_SCORE_TABLE", Context.MODE_PRIVATE);

        showLastHighScore();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.over_menu_button) {
            menuActivity();
        }
    }

    private void menuActivity() {
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }

    private void showLastHighScore() {
        long bullets = gameHighScore.getLong("shots", 0);
        long missed = gameHighScore.getLong("missed", 0);
        long killed = gameHighScore.getLong("killed", 0);
        long highScore = killed - (bullets / 5) + (missed / 3);
        shotsTextView.setText(String.valueOf(gameHighScore.getLong("shots", 0)));
        missedTextView.setText(String.valueOf(gameHighScore.getLong("missed", 0)));
        killsTextView.setText(String.valueOf(gameHighScore.getLong("killed", 0)));
        highScoreTextView.setText(String.valueOf(highScore));
    }
}
