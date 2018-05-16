package com.cwprogramming.pacman.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.opengl.Visibility;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cwprogramming.pacman.game.Game;
import com.cwprogramming.pacman.R;
import com.cwprogramming.pacman.control.RepeatListener;
import com.cwprogramming.pacman.game.GameObservable;
import com.cwprogramming.pacman.game.GameState;
import com.cwprogramming.pacman.views.JoystickView;

import java.util.Observable;
import java.util.Observer;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, Observer{
    private Game game;
    private TextView pauseTV, cakesTV, levelTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game = findViewById(R.id.game);
        pauseTV = findViewById(R.id.pauseTV);
        cakesTV = findViewById(R.id.cakesTV);
        levelTV = findViewById(R.id.levelTV);
        pauseTV.setOnClickListener(this);
        pauseTV.setOnLongClickListener(this);
        game.setObserver(this);
        if(savedInstanceState != null)
            game.restoreGameState((GameState) savedInstanceState.getSerializable("game"));


        ((JoystickView)findViewById(R.id.joystickView)).setOnMoveListener(game.getPlayer());
        findViewById(R.id.rightBtn).setOnTouchListener(new RepeatListener(50, 50, game.getPlayer()));
        findViewById(R.id.downBtn).setOnTouchListener(new RepeatListener(50, 50, game.getPlayer()));
        findViewById(R.id.leftBtn).setOnTouchListener(new RepeatListener(50, 50, game.getPlayer()));
        findViewById(R.id.upBtn).setOnTouchListener(new RepeatListener(50, 50, game.getPlayer()));

        /*sets continuously updates direction*/

    }

    private void disableJoyStick(){
        ((JoystickView)findViewById(R.id.joystickView)).setOnMoveListener(null);
        findViewById(R.id.joystickView).setVisibility(View.GONE);
        findViewById(R.id.rightBtn).setVisibility(View.VISIBLE);
        findViewById(R.id.downBtn).setVisibility(View.VISIBLE);
        findViewById(R.id.leftBtn).setVisibility(View.VISIBLE);
        findViewById(R.id.upBtn).setVisibility(View.VISIBLE);
    }

    private void disableButtons(){
        ((JoystickView)findViewById(R.id.joystickView)).setOnMoveListener(game.getPlayer());    //so when the joystick is hidden it isnt calling the onMoveListener
        findViewById(R.id.joystickView).setVisibility(View.VISIBLE);
        findViewById(R.id.rightBtn).setVisibility(View.GONE);
        findViewById(R.id.downBtn).setVisibility(View.GONE);
        findViewById(R.id.leftBtn).setVisibility(View.GONE);
        findViewById(R.id.upBtn).setVisibility(View.GONE);
    }



    @Override
    protected void onPause() {
        super.onPause();
        game.setGameStopped(true);
        game.pauseGame();
    }

    @Override
    protected void onStop() {
        super.onStop();
        game.setGameStopped(true);
        game.pauseGame();
    }

    @Override
    public void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
        b.putSerializable("game",game.getGameState());
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.setGameStopped(false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        game.setInitialNumGhosts(sharedPref.getInt("init_ghosts", 2));
        game.setAdditionalGhosts(sharedPref.getInt("additional_ghosts", 2));

        if(sharedPref.getBoolean("joystick_conrol", false)) {    //if they want joystick control
            disableButtons();
        }
        else {
            disableJoyStick();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pauseTV:
                if(game.isPaused())
                    game.resumeGame();
                else
                    game.pauseGame();
                break;
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        GameObservable gameObservable = (GameObservable) observable;
        cakesTV.setText("Cakes left: " + gameObservable.getCakesRemaining());
        levelTV.setText("Level: " + gameObservable.getLevel());


        if(game.isGameOver())
            launchGameOverIntent(game.isPlayerWon());

    }


    private void launchGameOverIntent(boolean gameWon){
        Intent gameOverIntent = new Intent(this, GameOverActivity.class);
        gameOverIntent.putExtra("player_won", gameWon);
        gameOverIntent.putExtra("player_cheated", game.isPlayerCheated());
        finish();
        startActivity(gameOverIntent);
    }

    @Override
    public void onBackPressed(){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.mainLayout),"Confirm leave game? It will not be saved!", Snackbar.LENGTH_SHORT);
        snackbar.setAction("Confirm", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameActivity.super.onBackPressed();
            }
        });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    public boolean onLongClick(View view) {
        game.cheat();
        Toast.makeText(this,"So you gonna cheat?!? Just GIT GUD",Toast.LENGTH_SHORT).show();
        return true;
    }

    /*Menu setup and Events*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
