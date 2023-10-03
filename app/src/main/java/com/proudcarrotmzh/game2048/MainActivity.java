package com.proudcarrotmzh.game2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvScore = findViewById(R.id.tvScore);
        GameView gv = findViewById(R.id.gameview);
        gv.setOnScoreChangeListener(new GameView.OnScoreChangedListener() {
            @Override
            public void onScoreChanged(int score) {
                tvScore.setText(score + "");
            }
        });
    }

}