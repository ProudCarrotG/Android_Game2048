package com.proudcarrotmzh.game2048;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Singletion {

    private static int score;
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

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Singletion.score = score;
    }

    public static String getCurrentTime() {
        // 创建一个 Date 对象，它包含当前的日期和时间
        Date currentDate = new Date();

        // 使用 SimpleDateFormat 格式化日期和时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 将当前日期时间格式化为字符串并返回
        String formattedDate = dateFormat.format(currentDate);

        return formattedDate;
    }

}
