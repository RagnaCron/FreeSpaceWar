package dev.setmanu.spacewar.game.sprites;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;

import dev.setmanu.spacewar.R;

public final class ExplosionSprite extends BmpSprite {

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
    public ExplosionSprite(Bitmap bmp, int maxX, int maxY, int minX, int minY) {
        super(bmp, maxX, maxY, minX, minY);
    }

    public void update(int positionX, int positionY) {
        x = positionX;
        y = positionY;
        updateRect();
    }

}
