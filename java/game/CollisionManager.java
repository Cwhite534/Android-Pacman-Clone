package com.cwprogramming.pacman.game;

import android.graphics.Rect;

import com.cwprogramming.pacman.game.characters.Ghost;
import com.cwprogramming.pacman.game.characters.MovableCharacter;
import com.cwprogramming.pacman.game.characters.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Casey on 3/15/2018.
 */

public class CollisionManager {
    private static CollisionManager thisInstance;
    private ArrayList<Ghost> ghosts;
    private Player player;
    private Map mapObj;


    public CollisionManager(){
        thisInstance = this;
    }



    /*returns true if the movable character has hit a wall*/
    public boolean hasMapCollision(MovableCharacter character){
        int[][] map = mapObj.getMap();
        Rect characterHitbox = character.getHitBox();
        int direction = character.getDirection();
        int xIndex = 0,yIndex = 0,xIndexLeft = 0,xIndexRight = 0,yIndexBottom = 0,yIndexTop = 0;

        switch (direction){
            case 0:
                return false;
            case 1: //right
                xIndex = characterHitbox.right / 100;
                yIndexTop = characterHitbox.top / 100;
                yIndexBottom = characterHitbox.bottom / 100;
                break;
            case 2: //down
                yIndex = characterHitbox.bottom / 100;
                xIndexLeft = characterHitbox.left / 100;
                xIndexRight = characterHitbox.right / 100;
                break;
            case 3: //left
                xIndex = characterHitbox.left / 100;
                yIndexTop = characterHitbox.top / 100;
                yIndexBottom = characterHitbox.bottom / 100;
                break;
            case 4: //up
                yIndex = characterHitbox.top / 100;
                xIndexLeft = characterHitbox.left / 100;
                xIndexRight = characterHitbox.right / 100;
                break;
        }

        /*if indexOut of bounds*/
        if(xIndex >= 14 || characterHitbox.left < 0 || yIndex >= 14 || characterHitbox.top < 0)
            return true;
            //else if moving ix x direction
        else if(direction == 1 || direction == 3)
            return ((map[yIndexTop][xIndex] == 1) || (map[yIndexBottom][xIndex] == 1));
        else
            return ((map[yIndex][xIndexLeft] == 1) || (map[yIndex][xIndexRight] == 1));
    }



    /*returns true if the character collides with a collectible*/
    public boolean hasCollectibleCollision(MovableCharacter character){
        int[][] collectibles = mapObj.getCollectables();
        int x = character.getHitBox().centerY() / 100;
        int y = character.getHitBox().centerX() / 100;
        boolean collision;
        if(collision = (collectibles[x][y] == 0))
            mapObj.consumeCollectible(x,y);

        return collision;
    }


    /*
    public void checkCollisions(){
        for (int i = 0; i < characters.size()-1; i++) {
            for(int j = i+1; j < characters.size(); j++){
                characters.get(i).checkCollision(characters.get(j));
            }
        }
    }*/


    public boolean hasPlayerGhostCollision(){
        for (Ghost nextGhost : ghosts) {
            if (player.hasCollision(nextGhost))
                return true;
        }
        return false;
    }

    public void setPlayer(Player player) {this.player = player;}
    public void setMap(Map mapObj) {this.mapObj = mapObj;}
    public void setGhosts(ArrayList<Ghost> ghosts) {this.ghosts = ghosts;}

    /*singleton pattern*/
    public static CollisionManager getInstance(){
        if(thisInstance == null)
            throw new IllegalStateException();
        return thisInstance;
    }
}
