package com.proudcarrotmzh.game2048;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

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
        if (getNum() == 0)
            label.setBackgroundColor(0x33ffffff);
        if (getNum() == 2)
            label.setBackgroundColor(0xffeee4da);
        if (getNum() == 4)
            label.setBackgroundColor(0xffeee1c9);
        if (getNum() == 8)
            label.setBackgroundColor(0xfff3b27a);
        if (getNum() == 16)
            label.setBackgroundColor(0xfff69664);

    }

    public boolean equals(Card o) {
        return getNum() == o.getNum();
    }
}
