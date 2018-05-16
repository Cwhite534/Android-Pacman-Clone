package com.cwprogramming.pacman.game;


import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Casey on 3/16/2018.
 */

public class GameObservable extends Observable {
    private int playerLives, cakesRemaining, level;

    public GameObservable(){
    }


    public void setPlayerLives(int playerLives){
        this.playerLives = playerLives;
        setNotify();
    }


    public void setCakesRemaining(int cakesRemaining){
        this.cakesRemaining = cakesRemaining;
        setNotify();
    }


    public void setLevel(int level){
        this.level = level;
        setNotify();
    }

    public void setNotify(){
        this.setChanged();
        this.notifyObservers();
    }

    public int getPlayerLives() {
        return playerLives;
    }

    public int getCakesRemaining() {
        return cakesRemaining;
    }

    public int getLevel() {
        return level;
    }
}
