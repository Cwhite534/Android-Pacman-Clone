package com.cwprogramming.pacman.game;

/**
 * Created by Casey on 3/20/2018.
 */

public interface Memento {
    public GameState getGameState();
    public void restoreGameState(GameState loadedGameState);
}
