package com.cwprogramming.pacman.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.cwprogramming.pacman.R;

/**
 * Created by Casey on 3/8/2018.
 */

public class BitmapManager {
    private static BitmapManager thisBitmapManager;
    private Context context;
    private Bitmap ghostBitmap;
    private Bitmap pacmanBitmap;
    private Bitmap[] collectibleBitmaps;
    private Bitmap[] mapBitmaps;

    public BitmapManager(Context context){
        this.context = context;
        mapBitmaps = initializeGridBitmaps();
        collectibleBitmaps = initializeCollectibleBitmaps();
        pacmanBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacman);
        ghostBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ghosts);
        thisBitmapManager = this;
    }

    private Bitmap[] initializeGridBitmaps(){
        Bitmap[] bitmapArray = new Bitmap[2];
        bitmapArray[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.path);//Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.path),blockSize,blockSize,true);
        bitmapArray[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.wall);
        return bitmapArray;
    }

    private Bitmap[] initializeCollectibleBitmaps(){
        Bitmap[] bitmapArray = new Bitmap[1];
        bitmapArray[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cake);
        return bitmapArray;
    }



    public Bitmap getGhostsBitmap(){return ghostBitmap; }
    public Bitmap getPacmanBitmap(){return pacmanBitmap;}
    public Bitmap[] getMapBitmaps(){return mapBitmaps; }
    public Bitmap[] getCollectibleBitmaps(){return collectibleBitmaps;}



    /*singleton Pattern*/
    public static BitmapManager getInstance(){
        if(thisBitmapManager == null)
            throw new IllegalStateException("This BitmapManager has not been initialized.");
        return thisBitmapManager;
    }
}
