package com.proudcarrotmzh.game2048;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChartsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        init();

    }

    private void init() {

        SQL dbsqLiteOpenHelper = new SQL(getApplicationContext(), "scoreTable.db", null, 1);
        SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();

        Cursor cursor;
//        cursor = db.rawQuery("SELECT time,score from scoreTable order by score DESC",null);
        cursor = db.query("scoreTable", null, null, null, null, null, "score DESC");

        ArrayList<String> notes = new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String __time = cursor.getString(Math.max(cursor.getColumnIndex("time"), 0));
            int __score = cursor.getInt(Math.max(cursor.getColumnIndex("score"), 0));

            String ss = "Time: " + __time + ", Score: " + __score;
            notes.add(ss);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);


        ListView listView = findViewById(R.id.listView); // 替换为您的 ListView ID
        listView.setAdapter(adapter);
    }
}
