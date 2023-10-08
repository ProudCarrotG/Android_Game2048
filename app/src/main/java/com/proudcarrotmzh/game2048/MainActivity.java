package com.proudcarrotmzh.game2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvScore = findViewById(R.id.tvScore);

//        游戏开始
        GameView gm = (GameView) findViewById(R.id.gameview);
        Button btStartGame = (Button) findViewById(R.id.startGame);
        btStartGame.setOnClickListener(view -> gm.startGame());

//        游戏分数更新
        GameView gv = findViewById(R.id.gameview);
        gv.setOnScoreChangeListener(new GameView.OnScoreChangedListener() {
            @Override
            public void onScoreChanged(int score) {
                tvScore.setText(score + "");
            }
        });

//        排行榜
        Button charts = (Button) findViewById(R.id.charts);
        charts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("do it!!!!!!!!");
                goToActivityCharts(view);
            }
        });

//        手动记录分数
        Button fin = (Button) findViewById(R.id.fin);
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableInsert();
            }
        });
    }

//    activity跳转
    public void goToActivityCharts(View view){
        Intent intent = new Intent(this,ChartsActivity.class);
        startActivity(intent);
    }

//    数据库数据插入
    public void tableInsert(){
        SQL dbsqLiteOpenHelper = new SQL(getApplicationContext(),"scoreTable.db",null,1);
        SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("time",Singletion.getCurrentTime());
        values.put("score",Singletion.getScore());

        db.insert("scoreTable",null,values);

        Toast.makeText(getApplicationContext(), "当前分数已保存", Toast.LENGTH_SHORT).show();

    }

}