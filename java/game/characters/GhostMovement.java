package com.cwprogramming.pacman.game.characters;

/**
 * Created by Casey on 3/9/2018.
 */

public class GhostMovement implements IGhostBehavior{

    public GhostMovement(){}

    @Override
    public void move(Ghost ghost) {
        int direction, rand = (int)(Math.random() * 100);
        if(rand > 20) {
            rand = (int)(Math.random() * 100);
            if (ghost.direction == 1 || ghost.direction == 3) {
                if (rand > 50)
                    direction = 2;
                else
                    direction = 4;
            } else {
                if (rand > 50)
                    direction = 1;
                else
                    direction = 3;
            }
            ghost.setDirection(direction);
        }
        else
            ghost.setDirection( (((int) (100*Math.random()) % 4) +1));

    }

    @Override
    public void mapCollision(Ghost ghost) {
        int direction, rand = (int)(Math.random() * 100);
        if(rand > 1) {
            rand = (int)(Math.random() * 100);
            if (ghost.direction == 1 || ghost.direction == 3) {
                if (rand > 50)
                    direction = 2;
                else
                    direction = 4;
            } else {
                if (rand > 50)
                    direction = 1;
                else
                    direction = 3;
            }
            ghost.setDirection(direction);
        }
        else
            ghost.setDirection( (((int) (100*Math.random()) % 4) +1));
    }



/*

    public static IGhostBehavior getMovementBehavior(int difficulty){
        switch (difficulty){
             case 0:
                return new IGhostBehavior() {
                    @Override
                    public void move(Ghost ghost) {
                        int direction, rand = (int)(Math.random() * 100);
                        if(rand > 20) {
                            rand = (int)(Math.random() * 100);
                            if (ghost.direction == 1 || ghost.direction == 3) {
                                if (rand > 50)
                                    direction = 2;
                                else
                                    direction = 4;
                            } else {
                                if (rand > 50)
                                    direction = 1;
                                else
                                    direction = 3;
                            }
                            ghost.setDirection(direction);
                        }
                        else
                            ghost.setDirection(((int) (100*Math.random())) % 4);

                    }

                    @Override
                    public void mapCollision(Ghost ghost) {
                        int direction, rand = (int)(Math.random() * 100);
                        if(rand > 20) {
                            if (ghost.direction == 1 || ghost.direction == 3) {
                                if (rand > 50)
                                    direction = 2;
                                else
                                    direction = 4;
                            } else {
                                if (rand > 50)
                                    direction = 1;
                                else
                                    direction = 3;
                            }
                            ghost.setDirection(direction);
                        }
                        else
                            ghost.setDirection(((int) (100*Math.random())) % 4);
                    }

                };


            default:
                return new IGhostBehavior() {
                    @Override
                    public void move(Ghost ghost) {
                        int rand = 1 + (int)((Math.random() * 100));
                        if (rand > 20){
                            ghost.setDirection(4);
                        }
                        else
                            ghost.setDirection(2);
                    }

                    @Override
                    public void mapCollision(Ghost ghost) {
                        int rand = 1 + (int)((Math.random() * 100) % 4);
                        ghost.setDirection(rand);
                    }
                };


        }
    }


*/

}
