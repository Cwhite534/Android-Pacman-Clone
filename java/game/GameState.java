package com.cwprogramming.pacman.game;

import android.location.Location;

import com.cwprogramming.pacman.game.characters.Ghost;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Casey on 3/20/2018.
 */

public class GameState implements Serializable, Cloneable {
    private int playerX, playerY, playerDirection, cakesRemaining,level;
    private int[] ghostX, ghostY;
    private int[][] collectibles;
    //private static LoadSave thisInstance;

    public GameState(int level){
        this.level = level;
        int numGhosts = 8;//Ghost.getInitialNumGhosts() + Ghost.getAdditionalGhosts();
        ghostX = new int[numGhosts];
        ghostY = new int[numGhosts];
    }

    public int getPlayerX() {
        return playerX;
    }
    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }
    public int getCakesRemaining() {return cakesRemaining;}
    public void setCakesRemaining(int cakesRemaining) {this.cakesRemaining = cakesRemaining;}
    public int getPlayerY() {
        return playerY;
    }
    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }
    public void setGhostPosX(int pos, int x){
        ghostX[pos] = x;}
    public void setGhostPosY(int pos, int x){
        ghostY[pos] = x;}
    public void setGhostX(int[] ghostX) {
        this.ghostX = ghostX;
    }
    public void setGhostY(int[] ghostY) {
        this.ghostY = ghostY;
    }
    public int[][] getCollectibles() {
        return collectibles;
    }
    public void setCollectibles(int[][] collectibles) {
        this.collectibles = collectibles;
    }

    public int getPlayerDirection() {
        return playerDirection;
    }

    public void setPlayerDirection(int playerDirection) {
        this.playerDirection = playerDirection;
    }

    public int[] getGhostX() {
        return ghostX;
    }

    public int[] getGhostY() {
        return ghostY;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public Object clone(){

            GameState gameState = new GameState(ghostX.length);
            for (int i = 0; i < ghostY.length;i++) {
                gameState.setGhostPosX(i,ghostX[i]);
                gameState.setGhostPosY(i,ghostY[i]);
            }

            int[][] newCollectible = new int[14][14];
            for (int i = 0; i < collectibles.length; i++) {
                for (int j = 0; j < collectibles[i].length; j++) {
                    newCollectible[i][j] = collectibles[i][j];
                }
            }
             gameState.setCollectibles(newCollectible);

            gameState.setPlayerY(playerY);
            gameState.setPlayerX(playerX);
            gameState.setCakesRemaining(cakesRemaining);
            gameState.setLevel(level);
            return gameState;

    }
    /*
    public static LoadSave getInstance(){
        if(thisInstance == null)
            thisInstance = new LoadSave();
        return thisInstance;
    }*/
}
