package com.proudcarrotmzh.game2048;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {
    Button startGame = findViewById(R.id.startGame);
    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();

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
    }


}
