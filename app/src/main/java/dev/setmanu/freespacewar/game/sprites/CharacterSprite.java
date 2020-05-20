package dev.setmanu.freespacewar.game.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public final class CharacterSprite extends BmpSprite {

    private boolean isBoosting;
    private final static int GRAVITY_X = 8;
    private final static int GRAVITY_Y = 7;
    private final static int MIN_SPEED = 3;
    private final static int MAX_SPEED = 12;
    private final static float VELOCITY = 0.3f;
    private boolean hasShot;
    private int movingVecX = 8;
    private int movingVecY = 5;
    private long lastDrawTime = -1;
    private long timeNow = 0;
    private boolean isMovingUp;
    private boolean isMovingDown;
    private boolean isBreaking;

    /**
     * Initialize the Sprite with a Bitmap.
     * You must always call super(bmp) in the sub class.
     * You have to initialize x and y, if you do not, you will have to expect a null pointer
     * exception.
     * The Speed of the Sprite. Is initial set to 0, so you will have to set it in
     * the initialization of your subclass.
     *
     * @param bmp The Good Player...
     * @param maxX The width
     * @param maxY The height
     * @param minX What you need it to be.
     * @param minY What you need it to be.
     */
    public CharacterSprite(Bitmap bmp, int maxX, int maxY, int minX, int minY) {
        super(bmp, maxX, maxY, minX, minY);
        this.minX = getWidth() * 2;
        x = maxX / 3;
        y = (maxY - bmp.getWidth()) / 2;
        speed = 1;
        isBoosting = false;
        rect = new Rect(x, y, bmp.getWidth(), bmp.getHeight());
        hasShot = false;
        isMovingUp = false;
        isMovingDown = false;
        isBreaking = false;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas, paint);
        lastDrawTime = System.nanoTime();
    }

    public void update() {
        timeNow = System.nanoTime();
        if (lastDrawTime == -1)
            lastDrawTime = timeNow;
        float distance = VELOCITY * deltaTime();
        double movingVector = Math.sqrt(movingVecX * movingVecX + movingVecY * movingVecY);
        if (isBoosting) {
            speed += 2;
        }
        else
            speed -= 5;

        if (isBreaking)
            x = x - (movingDistance(distance, movingVecX, movingVector) + GRAVITY_X);

        if (speed > MAX_SPEED)
            speed = MAX_SPEED;

        if (speed < MIN_SPEED)
            speed = MIN_SPEED;

        if (isMovingUp) {
            y = y - (movingDistance(distance, movingVecY, movingVector) + GRAVITY_Y);
        }

        if (isMovingDown){
            y = y + (movingDistance(distance, movingVecY, movingVector) + GRAVITY_Y);
        }

        x += speed - GRAVITY_X;

        if (x < minX) {
            x = minX;
            isBreaking = false;
            isBoosting = true;
        }
        if (x > maxX - bmp.getWidth())
            x = maxX - bmp.getWidth();

        if (y < minY) {
            y = minY;
        }
        if (y > maxY - bmp.getHeight())
            y = maxY - bmp.getHeight();

        updateRect();
    }

    public void setBoost(boolean boost) {
        isBoosting = boost;
    }

    public boolean hasShot() {
        return hasShot;
    }

    public void setHasShot(boolean shot) {
        hasShot = shot;
    }

    public void setMovingUp(boolean up) {
        isMovingUp = up;
    }

    public void setMovingDown(boolean down) {
        isMovingDown = down;
    }

    public void setIsBreaking(boolean breaking) {
        isBreaking = breaking;
    }

    private float deltaTime() {
        return (timeNow - lastDrawTime) / 1000000f;
    }

    private int movingDistance(float distance, int vec, double movingVec) {
        return (int) (distance * vec / movingVec);
    }


}
