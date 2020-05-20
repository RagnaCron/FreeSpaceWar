package dev.setmanu.spacewar.menuScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import dev.setmanu.spacewar.game.GameScreen;
import dev.setmanu.spacewar.R;

public final class MenuScreen extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);

        findViewById(R.id.start_a_ware).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_a_ware) {
            startWar();
        }
    }

    private void startWar() {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }
}
