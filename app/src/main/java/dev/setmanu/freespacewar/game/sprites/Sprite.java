package dev.setmanu.freespacewar.game.sprites;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * The abstract Sprite class will be the basis for the game.
 * It is meant to be subclassed for enemies and Player or what you see fit -> instances.
 */
public abstract class Sprite {

    protected int x, y = 0;
    protected int speed;
    protected int maxX;
    protected int maxY;
    protected int minX;
    protected int minY;

    /**
     * You must always call super(bmp) in the sub class.
     * You have to initialize x and y, if you do not, you will have to expect an
     * exception. There is a protected Rect property, if you want to use it, you will
     * have to initialize it, as it is set to null initially.
     * The Speed of the Sprite. Is initial set to 0, so you will have to set it in
     * the initialization of your subclass.
     *
     * @param maxX The width
     * @param maxY The height
     * @param minX What you need it to be.
     * @param minY What you need it to be.
     */
    Sprite(int maxX, int maxY, int minX, int minY) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.minY = minY;
        this.minX = minX;
        speed = 0;
    }


    /**
     * We provide a basic implementation, but you can gang it to your liking.
     * there is no need to call super if you don't want to.
     * @param canvas The canvas to draw you bitmap to.
     */
    public abstract void draw(Canvas canvas, Paint paint);

    /**
     * Generate some Random number.
     * @param bound How high to go?
     * @param extra Add some extra..
     * @return The random number.
     */
    protected int rand(int bound, int extra) {
        return new Random().nextInt(bound) + extra;
    }
    /**
     * Returns the X Coordinate.
     * @return The x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Set the X Coordinate.
     * @param x To be set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the Y Coordinate.
     * @return The y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Set y coordinate.
     * @param y To be set.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * The Speed of the Sprite. Is initial set to 0, so you will have to set it in
     * the initialization of your subclass.
     * @return The speed of the sprite.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * The max width of the Screen.
     *
     * !! Be cautious it may be that thous are inverted with maxY. !!
     * @return max width
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * The max height of the Screen.
     *
     * !! Be cautious it may be that thous are inverted with maxX. !!
     * @return max height
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * The min width.
     * Just set it to 0, or what ever.
     * @return What you need it to be.
     */
    public int getMinX() {
        return minX;
    }

    /**
     * The min height.
     * Just set it to 0, or what ever.
     * @return What you need it to be.
     */
    public int getMinY() {
        return minY;
    }
}
