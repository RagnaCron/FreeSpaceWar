package dev.setmanu.freespacewar.menuScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import dev.setmanu.freespacewar.game.GameScreen;
import dev.setmanu.freespacewar.R;
import dev.setmanu.freespacewar.highScoreScreen.HighScoreScreen;
import dev.setmanu.freespacewar.settingsScreen.SettingsScreen;

public final class MenuScreen extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);

        findViewById(R.id.start_a_ware).setOnClickListener(this);
        findViewById(R.id.it_was_a_good_war).setOnClickListener(this);
        findViewById(R.id.change_life).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_a_ware) {
            startActivity(new Intent(this, GameScreen.class));
        }
        else if (v.getId() == R.id.it_was_a_good_war) {
            startActivity(new Intent(this, HighScoreScreen.class));
        }
        else if (v.getId() == R.id.change_life) {
            startActivity(new Intent(this, SettingsScreen.class));
        }
    }
}
