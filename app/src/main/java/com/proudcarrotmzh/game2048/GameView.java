package com.proudcarrotmzh.game2048;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameView extends GridLayout {
    Button startGame = findViewById(R.id.startGame);
    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();

//    private int score = 0;
    private OnScoreChangedListener onScoreChangedListener;


    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    private void initGameView() {

        setColumnCount(4);
        setBackgroundColor(0XffBAB09E);
//        186,176,158
        addCards(getCardWitch(), getCardWitch());


        resetScore();

        setOnTouchListener(new OnTouchListener() {

            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = motionEvent.getX() - startX;
                        offsetY = motionEvent.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                System.out.println("left");
                                swipeLeft();
                            } else if (offsetX > 5) {
                                System.out.println("right");
                                swipeRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                System.out.println("up");
                                swipeUp();
                            } else if (offsetY > 5) {
                                System.out.println("down");
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });


    }

    private int getCardWitch() {
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int cardWitch;
        cardWitch = displayMetrics.widthPixels;
        return (cardWitch - 10) / 4;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startGame();
//        addCards(cardWidth, cardWidth);

//        startGame.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                startGame();
//            }
//        });
    }

    private void addCards(int cardWidth, int cardHeight) {

        Card c;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardWidth, cardHeight);

                cardsMap[x][y] = c;
                System.out.println("build card");
            }
        }
    }

    public void startGame() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }

        resetScore();

        addRandomNum();
        addRandomNum();

//        addRandomNum();
//        addRandomNum();
//        addRandomNum();
//        addRandomNum();
    }

    private void addRandomNum() {

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }

        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
        emptyPoints.clear();
    }

    public void setOnScoreChangeListener(OnScoreChangedListener listener) {
        this.onScoreChangedListener = listener;
    }

    private void resetScore() {
//        score = 0;
        Singletion.setScore(0);

        if (onScoreChangedListener != null) {
//            onScoreChangedListener.onScoreChanged(score);
            onScoreChangedListener.onScoreChanged(Singletion.getScore());
        }
    }

    private void updateScore(int x) {
        // 统计你的分数逻辑代码
//        score += x;
        Singletion.setScore(Singletion.getScore()+x);
        if (onScoreChangedListener != null) {
//            onScoreChangedListener.onScoreChanged(score);
            onScoreChangedListener.onScoreChanged(Singletion.getScore());
        }
    }

    private void swipeLeft() {
        System.out.println("do left!!!");

        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardsMap[x1][y].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            System.out.println("num<=0!!!");
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            merge = true;
                            x--;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {

                            updateScore(cardsMap[x][y].getNum());

                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (merge) addRandomNum();
        checkGameState();

    }

    private void checkGameState() {

        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};

        boolean num = false, near = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cardsMap[i][j].getNum() == 0)
                    return;

                for (int k = 0; k < 4; k++) {
                    int nx = i + dx[k];
                    int ny = j + dy[k];
                    if (0 <= nx && nx < 4 && 0 <= ny && ny < 4) {
                        if (cardsMap[i][j].equals(cardsMap[nx][ny])) {
                            return;
                        }
                    }
                }
            }
        }

        Toast.makeText(getContext().getApplicationContext(), "无法继续操作，游戏结束！可在排行榜中查看排名！", Toast.LENGTH_SHORT).show();

        tableInsert();

        return;
    }

    // 获取当前系统时间的方法
//    public String getCurrentTime() {
//        // 创建一个 Date 对象，它包含当前的日期和时间
//        Date currentDate = new Date();
//
//        // 使用 SimpleDateFormat 格式化日期和时间
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        // 将当前日期时间格式化为字符串并返回
//        String formattedDate = dateFormat.format(currentDate);
//
//        return formattedDate;
//    }

    public void tableInsert(){
        SQL dbsqLiteOpenHelper = new SQL(getContext().getApplicationContext(),"scoreTable.db",null,1);
        SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("time",Singletion.getCurrentTime());
        values.put("score",Singletion.getScore());

        db.insert("scoreTable",null,values);
    }

    private void swipeRight() {
        System.out.println("do right!!!");
        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            System.out.println("num<=0!!!");
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            merge = true;
                            x++;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {

                            updateScore(cardsMap[x][y].getNum());

                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) addRandomNum();
        checkGameState();
    }

    private void swipeUp() {
        System.out.println("do up!!!");
        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            System.out.println("num<=0!!!");
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            merge = true;

                            y--;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {

                            updateScore(cardsMap[x][y].getNum());

                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            merge = true;

                        }
                        break;
                    }
                }
            }
        }
        if (merge) addRandomNum();
        checkGameState();

    }

    private void swipeDown() {
        System.out.println("do down!!!");
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            System.out.println("num<=0!!!");
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            merge = true;
                            y++;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {

                            updateScore(cardsMap[x][y].getNum());

                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) addRandomNum();
        checkGameState();

    }

    public interface OnScoreChangedListener {
        void onScoreChanged(int score);
    }
}
