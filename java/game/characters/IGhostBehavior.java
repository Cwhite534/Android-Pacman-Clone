package com.cwprogramming.pacman.game.characters;

/**
 * Created by Casey on 3/9/2018.
 */

public interface IGhostBehavior {
    public void move(Ghost ghost);
    public void mapCollision(Ghost ghost);
}
