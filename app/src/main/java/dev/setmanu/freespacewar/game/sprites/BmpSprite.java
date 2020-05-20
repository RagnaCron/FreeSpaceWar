package dev.setmanu.freespacewar.game.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class BmpSprite extends Sprite {

    protected Bitmap bmp;
    protected Rect rect;

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
     * @param bmp The Sprite to show.
     * @param maxX The width
     * @param maxY The height
     * @param minX What you need it to be.
     * @param minY What you need it to be.
     */
    BmpSprite(Bitmap bmp, int maxX, int maxY, int minX, int minY) {
        super(maxX, maxY, minX, minY);
        this.bmp = bmp;
        rect = new Rect();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bmp, x, y, paint);
    }

    /**
     * To be called to update the rect to the new Bounds of the BmpSprite.
     */
    void updateRect() {
        rect.left = x;
        rect.top = y;
        rect.right = x + bmp.getWidth();
        rect.bottom = y + bmp.getHeight();
    }

    /**
     * Get the current rect.
     * @return The rect.
     */
    public Rect getRect() {
        return rect;
    }

    public boolean isIntersecting(Rect otherRect) {
        return rect.intersect(otherRect);
    }

    public int getWidth() {
        return bmp.getWidth();
    }

    public int getHeight() {
        return bmp.getHeight();
    }

}
