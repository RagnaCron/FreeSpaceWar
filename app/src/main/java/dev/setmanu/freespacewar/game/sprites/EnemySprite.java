package dev.setmanu.freespacewar.game.sprites;

import android.graphics.Bitmap;
import android.graphics.Rect;


public class EnemySprite extends BmpSprite {

    private final static int BOUND_SPEED = 5;
    private final static int EXTRA_SPEED = 7;
    private boolean isVisible;
    private boolean isPassed;

    /**
     * Initialize the Sprite with a Bitmap.
     * You must always call super(bmp) in the sub class.
     * You have to initialize x and y, if you do not, you will have to expect a null pointer
     * exception. There is a protected Rect property, if you want to use it, you will
     * have to initialize it, as it is set to null initially.
     * The Speed of the Sprite. Is initial set to 0, so you will have to set it in
     * the initialization of your subclass.
     * The protected Rect property is set but you will have to call updateRect() after you
     * have set x and y.
     *
     * @param bmp The Bad Enemy...
     * @param maxX The width
     * @param maxY The height
     * @param minX What you need it to be.
     * @param minY What you need it to be.
     */
    public EnemySprite(Bitmap bmp, int maxX, int maxY, int minX, int minY) {
        super(bmp, maxX, maxY, minX, minY);
        speed = rand(BOUND_SPEED, EXTRA_SPEED);
        x = rand(maxX, 0);
        y = maxY + rand(maxY, bmp.getHeight());
        rect = new Rect(x, y, bmp.getWidth(), bmp.getHeight());
        isVisible = false;
        isPassed = false;
    }

    public void update(int playerSpeed) {
        isVisible = (x > minX && x < maxX);
        x -= playerSpeed;
        x -= speed;
        if (x < minY - bmp.getHeight()) {
            speed = rand(BOUND_SPEED, EXTRA_SPEED);
            y = rand(maxY, 0);
            if (y > maxY - bmp.getWidth())
                y -= bmp.getWidth();
            x = maxX + rand(maxX,0);
            isPassed = false;
        }
        updateRect();
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setPassed(boolean is) {
        isPassed = is;
    }

    public boolean isPassed() {
        return isPassed;
    }

}
