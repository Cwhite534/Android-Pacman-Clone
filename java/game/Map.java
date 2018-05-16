package com.cwprogramming.pacman.game;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.cwprogramming.pacman.game.characters.MovableCharacter;


/**
 * Created by Casey on 3/7/2018.
 */

public class Map implements IDrawable{
    /*1400*1400 logical coordinate system
    * types:
    *   0: path
    *   1: wall*
    *  */

    private int[][] map;
    private int[][] collectables;
    private int remainingCakes;
    private static Map thisMap;
    private static final int lastLevel = 2;
    private int level;
    private Rect drawingRect;

    public Map(int level){
        this.level = level;
        map = loadMap(level);
        remainingCakes = 0;
        collectables = loadCollectibles();
        drawingRect = new Rect(0,0,100,100);
        thisMap = this;

    }

    private int[][] loadMap(int level){
        int[][] newMap;
switch (level) {
    case 1: newMap = new int[][]{
            {0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0}, //0
            {0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0}, //1
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0}, //2
            {0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1}, //3
            {0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, //4
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0}, //5
            {0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0}, //6
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, //7
            {0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0}, //8
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, //9
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1}, //10
            {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0}, //11
            {0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0}, //12
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};//13
        break;
    case 2:
        newMap = new int[][]{
                {0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, //0
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0}, //1
                {0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0}, //2
                {0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1}, //3
                {1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0}, //4
                {0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, //5
                {0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0}, //6
                {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0}, //7
                {0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0}, //8
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, //9
                {1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1}, //10
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0}, //11
                {0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0}, //12
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}};//13
        break;
        default:
            newMap = new int[][]{
                    {0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0}, //0
                    {0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0}, //1
                    {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0}, //2
                    {0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1}, //3
                    {0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, //4
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0}, //5
                    {0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0}, //6
                    {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, //7
                    {0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0}, //8
                    {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, //9
                    {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1}, //10
                    {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0}, //11
                    {0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0}, //12
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};//13
            break;
}
        return newMap;
    }

    private int[][] loadCollectibles(){
        int[][] collectibles = new int[14][14];
        for(int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if(map[row][col] == 0) {
                    collectibles[row][col] = 0;
                    remainingCakes++;
                }
                else
                    collectibles[row][col] = -1;

            }
        }
        return collectibles;
    }


    public void consumeCollectible(int x, int y){
        if(collectables[x][y] == 0)
            remainingCakes--;
        collectables[x][y] = -1;
    }

    public boolean hasCakesRemaining(){
        return (remainingCakes != 0);
    }



    /*draws the map*/
    public void onDraw(Canvas canvas, Paint paint){
        int type;
        for(int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                type = map[row][col];
                int x =  col * 100;
                int y =  row * 100;
                drawingRect.set( x,  y, x + 100,y + 100);
                canvas.drawBitmap(BitmapManager.getInstance().getMapBitmaps()[type], null  ,drawingRect, paint);

                if (collectables[row][col] != -1)
                    drawCollectible(canvas,paint,x+40,y+40,collectables[row][col]);

            }
        }
    }

    private void drawCollectible(Canvas canvas, Paint paint, int x, int y, int type){
        drawingRect.set(x,y,x+25,y+25);
        canvas.drawBitmap(BitmapManager.getInstance().getCollectibleBitmaps()[type], null  ,drawingRect, paint);
    }

    public static Map getInstance(){
        return thisMap;
    }

    public int getRemainingCakes() {
        return remainingCakes;
    }

    public int[][] getMap() {
        return map;
    }

    public void setCollectables(int[][] collectables) {
        this.collectables = collectables;
    }

    public void setRemainingCakes(int remainingCakes) {
        this.remainingCakes = remainingCakes;
    }

    public int[][] getCollectables() {
        return collectables;
    }

    public void cheat(){
        remainingCakes = 0;

        collectables = new int[14][14];
        for(int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if(map[row][col] == 0 && row == 7 && col == 7) {
                    collectables[row][col] = 0;
                    remainingCakes++;
                }
                else
                    collectables[row][col] = -1;

            }
        }
    }

    public static int getLastLevel() {return lastLevel;}
}
