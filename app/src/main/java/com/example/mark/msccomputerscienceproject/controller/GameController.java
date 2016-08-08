package com.example.mark.msccomputerscienceproject.controller;

import com.example.mark.msccomputerscienceproject.model.Emoticon;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface GameController {

    void handle(MotionEvent event);

    void updateModel();

    void updateScoreBoardView(int points);

    void controlGameBoardView(int second);

    void playSound(String sound);

    void playSound(ArrayList<LinkedList<Emoticon>> matchingX, ArrayList<LinkedList<Emoticon>> matchingY);

    boolean isGameEnded();

    void setGameEnded(boolean gameEnded);

}