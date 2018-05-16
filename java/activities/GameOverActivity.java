package com.cwprogramming.pacman.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cwprogramming.pacman.R;

public class GameOverActivity extends AppCompatActivity {
    private boolean playerWon, playerCheated;
    private TextView header, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        header = findViewById(R.id.headerTV);
        content = findViewById(R.id.contentTV);

        playerWon = getIntent().getBooleanExtra("player_won", false);
        playerCheated = getIntent().getBooleanExtra("player_cheated", false);

        String headerText, contentText;
        if(playerWon) {
             headerText = "You Won!!!";
             if(playerCheated)
                contentText = "Congratulations! You beat all levels, But you did cheat. You are a big man huh? Why don't you hit that play again button and play the game like a real competitor! " +
                        "Just kidding In all honestly I appreciate you playing and I hope it was a fun experience. But why don't you try again?";
             else
                 contentText = "Congratulations! you beat all levels! You are the real deal.  Thank you for playing this Pacman clone created by Casey White. I hope to see you in my next app.";
        }
        else{
            headerText = "You Lost!!!";
            if(playerCheated)
                contentText = "You died and you cheated!?! I coded this into the game but I never actually believed someone would suck bad enough " +
                        "to actually trigger this! Are you okay? Are you actually legally blind?" +
                        "\nJust kidding! In all honestly I appreciate you playing and I hope it was a fun experience. But why don't you try again?";
            else
                contentText = "You died! Sucks to suck. But why don't you give it another go and actually try to avoid the ghosts this time!";
        }

        header.setText(headerText);
        content.setText(contentText);

    }

    public void onPlayAgainBtnClick(View view){
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        finish();
        startActivity(gameIntent);
    }

    public void onMainMenuBtnClick(View view){
        Intent menuIntent = new Intent(this, MainMenuActivity.class);
        menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(menuIntent);
    }
}
