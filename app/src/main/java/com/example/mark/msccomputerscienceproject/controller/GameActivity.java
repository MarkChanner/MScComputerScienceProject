package com.example.mark.msccomputerscienceproject.controller;

import android.view.MotionEvent;

import com.example.mark.msccomputerscienceproject.model.GamePiece;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface GameController {

    void handleSelection(MotionEvent event);

    void updateModel();

    void updateScoreBoardView(int points);

    void controlGameBoardView(int second);

    void playSound(String sound);

    void playSound(ArrayList<LinkedList<GamePiece>> matchingX, ArrayList<LinkedList<GamePiece>> matchingY);

    boolean gameOver();

    void setGameEnded(boolean gameEnded);

}