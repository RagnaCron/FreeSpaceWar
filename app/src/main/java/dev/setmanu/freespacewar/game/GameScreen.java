package dev.setmanu.freespacewar.game;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import dev.setmanu.freespacewar.R;


public final class GameScreen extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        FrameLayout frameLayout = new FrameLayout(this);
        View constraintLayout = LayoutInflater.from(this).inflate(R.layout.game_view, null);

        gameView = new GameView(this, constraintLayout, size.x, size.y);

        frameLayout.addView(gameView);
        frameLayout.addView(constraintLayout);
        constraintLayout.setOnClickListener(v -> gameView.onClick(v));
        setContentView(frameLayout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resumeGame();
    }

}
