package com.example.mark.msccomputerscienceproject;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.LinkedList;

public interface GameController {

    void handleEvent(MotionEvent event);

    void updateEmoticonCoordinates();

    void highlightSelectionRequest(int x, int y);

    void unHighlightSelectionRequest();

    void incrementScoreRequest(int points);

    void controlRequest(int second);

    void playSound(String sound);

    void playSound(ArrayList<LinkedList<Emoticon>> matchingX, ArrayList<LinkedList<Emoticon>> matchingY);

    //Emoticon[][] getEmoticons();

    boolean isGameEnded();

    void setGameEnded(boolean gameEnded);

}