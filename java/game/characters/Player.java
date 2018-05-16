package com.cwprogramming.pacman.game.characters;



import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.cwprogramming.pacman.R;
import com.cwprogramming.pacman.game.BitmapManager;
import com.cwprogramming.pacman.game.CollisionManager;
import com.cwprogramming.pacman.game.Sound;
import com.cwprogramming.pacman.views.JoystickView;

import java.io.Serializable;

/**
 * Created by Casey on 3/7/2018.
 */

public class Player extends MovableCharacter implements View.OnClickListener, JoystickView.OnMoveListener{

    private int cakesEaten;

    public Player(int x, int y) {
        super(x,y,10);
        super.setSpriteInfo(3,420);
        this.cakesEaten = 0;
    }

    @Override
    public void update(){
        super.update();
        if(CollisionManager.getInstance().hasCollectibleCollision(this)){
            onCollectibleEaten();
        }
    }

    private void onCollectibleEaten(){
        cakesEaten++;
        Sound.getInstance().playClip(R.raw.pacman_chomp);
    }

    @Override
    public void onMapCollision(){
       setDirection(0);
    }

    @Override
    public void onCharacterCollision() {
        direction = 0;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint){
        int x = bitmapSrcSize * frameNumber, y =  (bitmapSrcSize  * (direction-1));

        if(direction == 0) y = 0;
        bitmapSrc.set(x,y,x+bitmapSrcSize,y+bitmapSrcSize);
        canvas.drawBitmap(BitmapManager.getInstance().getPacmanBitmap(), bitmapSrc,hitBox,paint);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.rightBtn:
                setDirection(1);
                break;
            case R.id.downBtn:
                setDirection(2);
                break;
            case R.id.leftBtn:
                setDirection(3);
                break;
            case R.id.upBtn:
                setDirection(4);
                break;
        }
    }

    @Override
    public void onMove(int angle, int strength) {
        if(strength > 10) {
            if (angle >= 315 || angle < 45)
                setDirection(1);
            else if(angle >= 225 && angle < 315)
                setDirection(2);
            else if(angle >= 135 && angle < 225)
                setDirection(3);
            else if(angle >= 45 && angle < 135)
                setDirection(4);
            else setDirection(0);
        }
        else
            setDirection(getDirection());
    }
}




