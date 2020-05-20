package dev.setmanu.spacewar.loadScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import dev.setmanu.spacewar.menuScreen.MenuScreen;
import dev.setmanu.spacewar.R;

public final class LoadScreen extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        findViewById(R.id.game_is_loading_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.game_is_loading_button) {
            menuActivity();
        }
    }



    private void menuActivity() {
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }

}
