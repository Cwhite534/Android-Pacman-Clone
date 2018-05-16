package com.cwprogramming.pacman.game.characters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.cwprogramming.pacman.game.CollisionManager;
import com.cwprogramming.pacman.game.IDrawable;

/**
 * Created by Casey on 3/8/2018.
 */

public abstract class MovableCharacter implements IDrawable {


    private static final int blockSize = 85;
    protected int frameNumber, numberOfFrames, bitmapSrcSize;
    protected int speed;
    protected Rect hitBox;
    protected Rect bitmapSrc;
    protected int direction;  //stop, right, down, left, up

    public MovableCharacter(int x, int y, int speed) {
        this.speed = speed;
        this.hitBox = new Rect(x - blockSize / 2, y - blockSize / 2, x + blockSize / 2, y + blockSize / 2);
        this.direction = 1;
        this.frameNumber = 0;
        this.bitmapSrc = new Rect(0,0,0,0);
    }


    public void update() {
        updatePosition();
        updateAnimation();

    }

    private void updatePosition(){
        moveTick();
        if (CollisionManager.getInstance().hasMapCollision(this)) {
            reverseDirection();
            moveTick();
            onMapCollision();
        }
    }


    private void moveTick(){
        if (direction != 0) {
            int newX = hitBox.left, newY = hitBox.top;
            switch (direction) {
                case 1://right
                    newX += speed;
                    break;
                case 2://down
                    newY += speed;
                    break;
                case 3://left
                    newX -= speed;
                    break;
                case 4://up
                    newY -= speed;
                    break;
            }
            hitBox.set(newX, newY, newX + blockSize, newY + blockSize);
        }
    }

    public void reverseDirection(){
        if(direction == 1)
            direction = 3;
        else if(direction == 2 )
            direction = 4;
        else if(direction == 3 )
            direction = 1;
        else if(direction == 4 )
            direction = 2;
    }


    private void updateAnimation(){
        frameNumber = (++frameNumber >= numberOfFrames) ? 0 : frameNumber;
    }

    public void setSpriteInfo(int numberOfFrames, int bitmapSrcSize){
        this.bitmapSrcSize = bitmapSrcSize;
        this.numberOfFrames = numberOfFrames;
    }

    public boolean hasCollision(MovableCharacter otherCharacter){
        return this.hitBox.intersect(otherCharacter.hitBox);
    }

    private void reverseTick(){
        reverseDirection();
        moveTick();
        reverseDirection();
    }

    @Override
    public abstract void onDraw(Canvas canvas, Paint paint);
    public abstract void onMapCollision();
    public abstract void onCharacterCollision();


    public int getPositionX() {
        return hitBox.centerX();
    }
    public int getPositionY() {
        return hitBox.centerY();
    }
    public void setPositionX(int x) {
        //hitBox.set(x, hitBox.top, x + blockSize, hitBox.bottom);
        this.hitBox = new Rect(x - blockSize / 2, hitBox.top, x + blockSize / 2, hitBox.bottom);
    }
    public void setPositionY(int y) {
        //hitBox.set(hitBox.left, y, hitBox.right, y + blockSize);
        this.hitBox = new Rect(hitBox.left, y - blockSize / 2, hitBox.right, y + blockSize / 2);
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed){this.speed = speed;}
    public int getDirection() {return direction;}
    public Rect getHitBox() {
        return hitBox;
    }

    public void setDirection(int direction) {
        int oldDirection = this.direction;
        this.direction = direction;

        moveTick();
        if(CollisionManager.getInstance().hasMapCollision(this)) {
            reverseDirection();
            moveTick();
            this.direction = oldDirection;
        }
        else {
            reverseTick();
        }

    }


}

