package com.cwprogramming.pacman.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.cwprogramming.pacman.R;

public class MainMenuActivity extends AppCompatActivity {

    TableLayout mapTable;
    EditText[][] map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }






    public void startGameBtnClick(View view){
        Intent gameActivity = new Intent(this, GameActivity.class);
        startActivity(gameActivity);
    }

    public void settingsBtnClick(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void aboutBtnClick(View view){
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        finish();
        startActivity(aboutIntent);
    }
}
