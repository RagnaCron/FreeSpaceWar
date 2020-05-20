package dev.setmanu.spacewar.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import dev.setmanu.spacewar.R;
import dev.setmanu.spacewar.game.sprites.CharacterSprite;
import dev.setmanu.spacewar.game.sprites.EnemySprite;
import dev.setmanu.spacewar.game.sprites.ExplosionSprite;
import dev.setmanu.spacewar.game.sprites.LaserSprite;
import dev.setmanu.spacewar.game.sprites.StarsSprite;
import dev.setmanu.spacewar.gameOverScreen.GameOverScreen;

public final class GameView extends SurfaceView implements Runnable, View.OnClickListener {

    public final static int BLACK_COLOR = Color.BLACK;
    public final static int WHITE_COLOR = Color.WHITE;
    public final static int MAGENTA_COLOR = Color.MAGENTA;
    public final static int CYAN_COLOR = Color.CYAN;
    public final static int YELLOW_COLOR = Color.YELLOW;
    private final static int AMMUNITION_CAPACITY = 10;
    private final static int STARS_CAPACITY = 70;
    private final static int ENEMIES = 15;
    private final static int ZERO = 0;

    private Thread gameThread = null;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private boolean isRunning;
    public static Canvas canvas;

    private CharacterSprite player;
    private final ArrayList<StarsSprite> stars = new ArrayList<>(STARS_CAPACITY);
    private final ArrayList<EnemySprite> enemies = new ArrayList<>(ENEMIES);
    private final ArrayList<LaserSprite> ammunition = new ArrayList<>(AMMUNITION_CAPACITY);
    private ExplosionSprite enemyExplosion;

    private View overLay;

    private GameSoundPool soundPool;

    private long bulletsShot;
    private long enemyKills;
    private long missedEnemies;
    private boolean isGameOver;

    private SharedPreferences saveHighScore;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, View overLay, int screenX, int screenY) {
        this(context);
        this.overLay = overLay;
        surfaceHolder = getHolder();
        paint = new Paint();
        paint.setColor(WHITE_COLOR);

        soundPool = GameSoundPool.getInstance(context);

        Bitmap playerBMP = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        Bitmap enemyBMP = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        Bitmap explosionBMP = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion);
        Bitmap laserShot = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser);

        for (int i = ZERO; i < AMMUNITION_CAPACITY; i++)
            ammunition.add(new LaserSprite(laserShot, screenX, screenY, ZERO, ZERO));
        player = new CharacterSprite(playerBMP, screenX, screenY, ZERO, ZERO);
        for (int i = ZERO; i < STARS_CAPACITY; i++)
            stars.add(new StarsSprite(screenX, screenY, ZERO, ZERO));
        for (int i = ZERO; i < ENEMIES; i++)
            enemies.add(new EnemySprite(enemyBMP, screenX, screenY, ZERO, ZERO));
        enemyExplosion = new ExplosionSprite(explosionBMP, screenX, screenY, ZERO, ZERO);

        bulletsShot = ZERO;
        enemyKills = ZERO;
        missedEnemies = ZERO;
        isGameOver = false;

        initButtons();

        saveHighScore = getContext().getSharedPreferences("HIGH_SCORE_TABLE", Context.MODE_PRIVATE);
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        int targetFPS = 60;
        long targetTime = 1000 / targetFPS;

        while (isRunning) {
            startTime = System.nanoTime();
            update();
            drawUI();
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = (targetTime - timeMillis);
            frameRateControl(waitTime < ZERO ? waitTime * -1 : waitTime);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.a_button:
                player.setHasShot(true);
                break;
            case R.id.up_button:
                player.setMovingDown(false);
                player.setMovingUp(true);
                break;
            case R.id.down_button:
                player.setMovingUp(false);
                player.setMovingDown(true);
                break;
            case R.id.left_button:
                player.setBoost(false);
                player.setIsBreaking(true);
                break;
            case R.id.right_button:
                player.setIsBreaking(false);
                player.setBoost(true);
                break;
            default:
                break;
        }
        gameOver();
    }

    private void update() {
        player.update();
        hasPlayerShot();
        ammunition.forEach(laser -> laser.update(laser.getX(), laser.getY()));
        enemyExplosion.update(-200, -200);
        stars.forEach(star -> star.update(player.getSpeed()));
        enemies.forEach(enemy -> {
            enemy.update(player.getSpeed());
            ammunition.forEach(laser -> {
                if (laser.hasBeenShoot()) {
                    if (laser.isIntersecting(enemy.getRect())) {
                        enemyExplode(enemy);
                        laser.setHasBeenShoot(false);
                        laser.setPosition(-200, -200);
                        enemyKills++;
                    }
                }
            });
            if (enemy.isIntersecting(player.getRect())) {
                enemyExplode(enemy);
                isGameOver = true;
                isRunning = false;
            } else {
                if (enemy.isVisible()) {
                    if (player.getRect().exactCenterY() >= enemy.getRect().exactCenterY()) {
                        if (!enemy.isPassed()) {
                            missedEnemies++;
                            enemy.setPassed(true);
                        }
                    }
                }
            }
        });
    }

    private void drawUI() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(BLACK_COLOR);

            stars.forEach(s -> s.draw(canvas, paint));
            player.draw(canvas, paint);
            ammunition.forEach(a -> a.draw(canvas, paint));
            enemies.forEach(e -> e.draw(canvas, paint));
            enemyExplosion.draw(canvas, paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void frameRateControl(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pauseGame() {
        try {
            if (soundPool != null)
                soundPool.stopBackGroundSound();
            soundPool = null;
            isRunning = false;
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resumeGame() {
        soundPool = GameSoundPool.getInstance(getContext());
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
        soundPool.playBackGroundSound();
    }

    private void gameOver() {
        if (isGameOver) {
            SharedPreferences.Editor editor = saveHighScore.edit();
            editor.putLong("shots", bulletsShot);
            editor.putLong("missed", missedEnemies);
            editor.putLong("killed", enemyKills);
            editor.apply();
            pauseGame();
            missedEnemies = 0;
            bulletsShot = 0;
            enemyKills = 0;
            Intent intent = new Intent(getContext(), GameOverScreen.class);
            getContext().startActivity(intent);
        }
    }

    public void destroyGame() {
        soundPool.releaseSoundPool();
    }

    private void enemyExplode(EnemySprite e) {
        enemyExplosion.update(e.getX(), e.getY());
        soundPool.playExplosionSound();
        e.setY(e.getMinY() - 200);
    }

    private void hasPlayerShot() {
        if (player.hasShot()) {
            for (int i = ZERO; i < AMMUNITION_CAPACITY; i++) {
                LaserSprite laser = ammunition.get(i);
                if (!laser.hasBeenShoot()) {
                    laser.setHasBeenShoot(true);
                    laser.setPosition(player.getX() + player.getWidth(), player.getY() + player.getHeight() / 2);
                    soundPool.playLaserShotSound();
                    player.setHasShot(false);
                    bulletsShot++;
                    break;
                }
            }
        }
    }

    private void initButtons() {
        Button aButton = overLay.findViewById(R.id.a_button);
        Button upButton = overLay.findViewById(R.id.up_button);
        Button downButton = overLay.findViewById(R.id.down_button);
        Button leftButton = overLay.findViewById(R.id.left_button);
        Button rightButton = overLay.findViewById(R.id.right_button);
        aButton.setOnClickListener(this);
        upButton.setOnClickListener(this);
        downButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
    }

}
