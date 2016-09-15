package com.example.mark.msccomputerscienceproject.controller;

import android.view.MotionEvent;

import com.example.mark.msccomputerscienceproject.model.MatchContainer;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface GameActivity {

    void handleTouch(MotionEvent event);

    void updateModel();

    void updateScoreBoardView(int points);

    void controlGameBoardView(int second);

    void playSound(String sound);

    void playSound(MatchContainer matchContainer);

    boolean gameOver();

    void setGameEnded(boolean gameEnded);

}