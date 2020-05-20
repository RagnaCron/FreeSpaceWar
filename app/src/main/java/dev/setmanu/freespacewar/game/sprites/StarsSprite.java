package dev.setmanu.freespacewar.game.sprites;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

import dev.setmanu.freespacewar.game.GameView;

public final class StarsSprite extends Sprite {

    private int[] colors;

    public StarsSprite(int screenX, int screenY, int minX, int minY) {
        super(screenX, screenY, minX, minY);
        speed = rand(10, 0);
        x = rand(maxX,0);
        y = rand(maxY, 0);
        colors = new int[4];
        colors[0] = GameView.WHITE_COLOR;
        colors[1] = GameView.MAGENTA_COLOR;
        colors[2] = GameView.CYAN_COLOR;
        colors[3] = GameView.YELLOW_COLOR;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setStrokeWidth(createStarWidth());
        paint.setColor(colors[rand(3, 0)]);
        canvas.drawPoint(x, y, paint);
    }

    private float createStarWidth() {
        float minSize = 1.0f;
        float maxSize = 5.5f;
        return new Random().nextFloat() * (maxSize - minSize) + minSize;
    }

    public void update(int playerSpeed) {
        x -= playerSpeed;
        x -= speed;
        if (x < 0) {
            x = maxX;
            speed = rand(15, 0);
        }
    }

}
