package com.cwprogramming.pacman.game.characters;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.cwprogramming.pacman.game.BitmapManager;
import com.cwprogramming.pacman.game.CollisionManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Casey on 3/8/2018.
 */

public class Ghost extends MovableCharacter{
    private static int initialNumGhosts = 1, additionalGhosts = 1, currentNumberOfGhosts;
    private int ghostNumber, spriteNumber;
    private int moveFrequencty;
    private IGhostBehavior movement;

    public Ghost(int x, int y, int ghostNumber, int level) {
        super(x, y, 5);
        this.setSpriteInfo(2,150);
        this.ghostNumber = ghostNumber;
        this.spriteNumber = (ghostNumber < 6) ? ghostNumber : ghostNumber%6;
        moveFrequencty = 0;
        movement = new GhostMovement();
        setDirection(1 + (int)(Math.random() * ((4 - 1) + 1)));

        if(level == 1)
            this.setSpeed(5);
        else
            this.setSpeed(10);
    }



    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        int x = bitmapSrcSize * spriteNumber, y = ((bitmapSrcSize * numberOfFrames) * (direction - 1)) + (bitmapSrcSize * frameNumber);
        bitmapSrc.set(x, y, x + bitmapSrcSize, y + bitmapSrcSize);
        canvas.drawBitmap(BitmapManager.getInstance().getGhostsBitmap(), bitmapSrc, hitBox, paint);

    }

    @Override
    public void update(){
        super.update();

        if(moveFrequencty++ > 50){
            movement.move(this);
            moveFrequencty = 0;
            int r = ((int) Math.random() * 100);
            if(r > 50)
                moveFrequencty = r;
        }
    }

    public static ArrayList<Ghost> initializeGhosts(int level){
        ArrayList<Ghost> ghosts = new ArrayList<>();
        int numGhosts = (level == 2) ? initialNumGhosts+additionalGhosts : initialNumGhosts;
        int initialX = 650 - (100 * ((numGhosts-1)/2));
        for (int i = 0; i < numGhosts; i++)
            ghosts.add(new Ghost(initialX + (i*100),750, i, level));

        CollisionManager.getInstance().setGhosts(ghosts);
        currentNumberOfGhosts = ghosts.size();
        return ghosts;
    }

    @Override
    public void onMapCollision() {
        movement.mapCollision(this);
    }

    @Override
    public void onCharacterCollision() {
        reverseDirection();
    }


    public static int getCurrentNumberOfGhosts() {return currentNumberOfGhosts;}
    public static int getInitialNumGhosts() {return initialNumGhosts;}
    public static int getAdditionalGhosts() {return additionalGhosts;}
    public static void setInitialNumGhosts(int initialNumGhosts) {Ghost.initialNumGhosts = initialNumGhosts;}
    public static void setAdditionalGhosts(int additionalGhosts) {Ghost.additionalGhosts = additionalGhosts;}
}

