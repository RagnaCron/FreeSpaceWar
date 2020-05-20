package dev.setmanu.freespacewar.game.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public final class LaserSprite extends BmpSprite {

    private boolean hasBeenShoot;

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
     * @param bmp  Pew Pew Pew...
     * @param maxX The width
     * @param maxY The height
     * @param minX What you need it to be.
     * @param minY What you need it to be.
     */
    public LaserSprite(Bitmap bmp, int maxX, int maxY, int minX, int minY) {
        super(bmp, maxX, maxY, minX, minY);
        speed = 30;
        x = -50;
        y = -50;
        hasBeenShoot = false;
        updateRect();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (hasBeenShoot)
            super.draw(canvas, paint);
    }

    public void update(int positionX, int positionY) {
        if (hasBeenShoot) {
            x = positionX + speed;
            y = positionY;
            if (x > maxX - bmp.getWidth()) {
                setPosition(-50, -50);
                setHasBeenShoot(false);
            }
            updateRect();
        }

    }

    public void setHasBeenShoot(boolean shot) {
        this.hasBeenShoot = shot;
    }

    public boolean hasBeenShoot() {
        return hasBeenShoot;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
