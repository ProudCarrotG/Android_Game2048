package com.proudcarrotmzh.game2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameView gm = (GameView) findViewById(R.id.gameview);
        Button btStartGame = (Button) findViewById(R.id.startGame);
        btStartGame.setOnClickListener(view -> gm.startGame());

    }
}