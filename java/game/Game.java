package com.cwprogramming.pacman.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.cwprogramming.pacman.R;
import com.cwprogramming.pacman.game.characters.Ghost;
import com.cwprogramming.pacman.game.characters.Player;

import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by Casey on 3/7/2018.
 */

public class Game extends View implements Memento{
    private static final int mLogicalWidth = 1400;
    private static final int mLogicalHeight = 1400;

    private static final int mTimerDelay = 33;
    private Handler clockHandler;
    private Runnable clockTimer;

    private Paint paint;

    private int level;
    private Map map;
    private Player player;
    private ArrayList<Ghost> ghosts;
    private CollisionManager collisionManager;
    private boolean isPaused, skippedCutscene, gameOver, playerCheated, playerWon, gameStopped;
    private GameObservable gameObservable;
    private GameState gameState;


    public Game(Context context) {
        super(context);
        initializeGame();

    }

    public Game(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeGame();
    }

    public Game(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeGame();
    }

    private void initializeGame(){
        new Sound(getContext());
        new BitmapManager(getContext());
        level = 0;
        player = new Player(450,1150);
        collisionManager = new CollisionManager();
        collisionManager.setPlayer(player);
        paint = new Paint();
        isPaused = true;
        gameOver = false;
        gameStopped = false;
        skippedCutscene = false;
        playerWon = false;
        playerCheated = false;
        gameObservable = new GameObservable();
        initializeNextLevel();
        Sound.getInstance().playClip(R.raw.pacman_beginning);
        initializeGameLoop();
        delayResume(4500);

    }

    private void initializeNextLevel(){
        level++;
        skippedCutscene = false;
        map = new Map(level);
        collisionManager.setMap(map);
        player.setPositionX(450);
        player.setPositionY(1150);
        ghosts = Ghost.initializeGhosts(level);
        gameState = new GameState(level);
        updateGameState();
    }

    private void initializeGameLoop(){
        clockHandler = new Handler();
        clockTimer = new Runnable() {
            @Override
            public void run() {
                if(!isPaused && !gameStopped) {
                    gameLoop();
                    clockHandler.postDelayed(this, mTimerDelay);
                }
            }
        } ;
    }




    private void gameLoop(){
        player.update();
        for(Ghost g : ghosts)
            g.update();

        gameObservable.setLevel(level);
        gameObservable.setCakesRemaining(map.getRemainingCakes());

        if(!map.hasCakesRemaining())
            onLevelCompletion();


        if(collisionManager.hasPlayerGhostCollision())
            onGameOver(false);

        updateGameState();
        invalidate();
    }

    private void updateGameState(){
        gameState.setPlayerX(player.getPositionX());
        gameState.setPlayerY(player.getPositionY());
        gameState.setPlayerDirection(player.getDirection());
        gameState.setCollectibles(map.getCollectables());
        gameState.setCakesRemaining(map.getRemainingCakes());
        int i = 0;
        for (Ghost ghost : ghosts) {
            gameState.setGhostPosX(i,ghost.getPositionX());
            gameState.setGhostPosY(i,ghost.getPositionY());
            i++;
        }
    }

    private void onLevelCompletion(){
        pauseGame();
        if(level == Map.getLastLevel())
            onGameOver(true);
        else {
            Toast.makeText(getContext(), "Level " + level + " completed!", Toast.LENGTH_SHORT).show();
            Sound.getInstance().playClip(R.raw.pacman_intermission);
            delayResume(5000);
            initializeNextLevel();
        }
    }



    private void onGameOver(boolean playerWon){
        this.playerWon = playerWon;
        pauseGame();
        int delay = 0;
        if(!playerWon) {
            Sound.getInstance().playClip(R.raw.game_loss);
            delay = 1600;
        }
        else{
            Sound.getInstance().playClip(R.raw.game_won);
            delay = 1000;
        }
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            gameOver = true;
            gameObservable.setNotify();
        }, delay);

    }



    public void pauseGame(){
        Sound.getInstance().pausePlayer();
        if(!isPaused) {
            clockHandler.removeCallbacks(clockTimer);
            isPaused = true;
        }
    }

    //delays start of game by about 4 seconds for game sound to finish
    private void delayResume(int delay){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if(!skippedCutscene && !gameStopped)
                resumeGame();
            else
                skippedCutscene = false;

            }, delay);
    }

    public void resumeGame(){
        if(isPaused && !gameOver) {
            clockHandler.post(clockTimer);
            isPaused = false;
            skippedCutscene = true;
        }
    }


    /*Draws all the elements in the game*/
    @Override
    protected void onDraw(Canvas canvas){
        canvas.scale(((float) canvas.getWidth() / mLogicalWidth), ((float) canvas.getHeight() / mLogicalHeight));
        map.onDraw(canvas,paint);
        for(Ghost g : ghosts)
            g.onDraw(canvas,paint);
        player.onDraw(canvas,paint);
    }



    /*Forces 1:1 aspect ratio*/
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);

        int height = mLogicalHeight, width = mLogicalWidth;
        float aspectRatio = ((float) mLogicalWidth) / mLogicalHeight;

        if(originalWidth == 0 && originalHeight != 0){
            height = originalHeight;
            width = (int)(height * aspectRatio);
        }
        else if(originalWidth != 0 && originalHeight == 0){
            width = originalWidth;
            height = (int)(width / aspectRatio);
        }
        else if(originalHeight != 0 && originalWidth != 0){
            width = Math.min(originalWidth, (int)(originalHeight*aspectRatio));
            height = Math.min(originalHeight, (int)(originalWidth / aspectRatio));
        }

        setMeasuredDimension(width, height);
    }

    public void cheat(){
        playerCheated = true;
        map.cheat();
    }

    public boolean isPlayerWon() { return playerWon;}
    public boolean isPlayerCheated() {return playerCheated;    }
    public boolean isGameOver() {return gameOver;}
    public void setPlayerDirection(int direction){
        player.setDirection(direction);
    }
    public Player getPlayer(){return player;}
    public boolean isPaused() {
        return isPaused;
    }
    public void setObserver(Observer observer) {gameObservable.addObserver(observer);}
    public boolean isGameStopped() {return gameStopped;}
    public void setGameStopped(boolean gameStopped) {this.gameStopped = gameStopped;}

    public void setInitialNumGhosts(int initialNumGhosts) {
        if(Ghost.getInitialNumGhosts() != initialNumGhosts) {
            Ghost.setInitialNumGhosts(initialNumGhosts);
            ghosts = Ghost.initializeGhosts(level);
        }
    }
    public void setAdditionalGhosts(int additionalGhosts) {
        if(Ghost.getAdditionalGhosts() != additionalGhosts) {
            Ghost.setAdditionalGhosts(additionalGhosts);
            if (level == 2) ghosts = Ghost.initializeGhosts(level);
        }
    }

    @Override
    public GameState getGameState() {
        return (GameState) gameState.clone();
    }

    @Override
    public void restoreGameState(GameState loadedGameState) {
        Sound.getInstance().pausePlayer();
        resumeGame();
        pauseGame();
        gameState = loadedGameState;
        player.setPositionY(gameState.getPlayerY());
        player.setPositionX(gameState.getPlayerX());
        player.setDirection(gameState.getPlayerDirection());

        level = gameState.getLevel();
        map = new Map(level);
        map.setRemainingCakes(gameState.getCakesRemaining());
        map.setCollectables(gameState.getCollectibles());

        int[] ghostX = gameState.getGhostX();
        int[] ghostY = gameState.getGhostY();
        for(int i = 0; i < ghostX.length; i++){
            ghosts.get(i).setPositionX(ghostX[i]);
            ghosts.get(i).setPositionY(ghostY[i]);
        }

    }
}
