package com.example.mark.msccomputerscienceproject;

import android.graphics.Canvas;

public interface GameBoardView {

    void control(int ms);

    void pause();

    void resume();

    void drawBoard(Canvas canvas);

}
