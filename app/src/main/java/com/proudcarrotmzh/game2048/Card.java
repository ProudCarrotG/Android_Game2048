package com.proudcarrotmzh.game2048;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Random;

public class Card extends FrameLayout {
    private int num = 0;
    private TextView label;

    public Card(@NonNull Context context) {
        super(context);
        label = new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0x33ffffff);
        label.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(30, 30, 0, 0);
        addView(label, lp);
        setNum(0);
    }

    public int getRandomColorCode() {
        Random rnd = new Random();
        int r = rnd.nextInt(256);
        int g = rnd.nextInt(256);
        int b = rnd.nextInt(256);
        return Color.argb(125, r, g, b);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num <= 0) {
            label.setText("");
        } else {
            label.setText(num + "");
        }
        colorchange();


    }

    private void colorchange() {

        System.out.println(Singletion.getMap().get(getNum()));

        Singletion.getMap().put(1, 1);

        if (!Singletion.getMap().containsKey(getNum())) {
            Singletion.getMap().put(getNum(), getRandomColorCode());
        }
        int key = getNum();
        label.setBackgroundColor( Singletion.getMap().get(key) );
    }

    public boolean equals(Card o) {
        return getNum() == o.getNum();
    }
}
