package com.example.mark.msccomputerscienceproject;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.LinkedList;

public interface GameController {

    void handle(MotionEvent event);

    void updateEmoticonCoordinates();

    void incrementScoreView(int points);

    void control(int second);

    void playSound(String sound);

    void playSound(ArrayList<LinkedList<Emoticon>> matchingX, ArrayList<LinkedList<Emoticon>> matchingY);

    boolean isGameEnded();

    void setGameEnded(boolean gameEnded);

}