package com.proudcarrotmzh.game2048;

import android.widget.TextView;

import java.util.HashMap;

public class Singletion {

    private static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>() {{
        put(0, 0x33ffffff);
        put(2, 0xffeee4da);
        put(4, 0xffeee1c9);
        put(8, 0xfff3b27a);
        put(16, 0xfff69664);
    }};

    public static HashMap<Integer,Integer> getMap(){
        return map;
    }
}
