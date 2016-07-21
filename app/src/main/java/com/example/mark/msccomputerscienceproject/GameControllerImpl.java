package com.example.mark.msccomputerscienceproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameControllerImpl extends Activity implements GameController {

    public static final int X_MAX = 8;
    public static final int Y_MAX = 7;

    /**
     * @author Mark Channer for Birkbeck MSc Computer Science project
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void handleEvent(MotionEvent event) {

    }

    @Override
    public void updateEmoticonCoordinates() {

    }

    @Override
    public void highlightSelectionRequest(int x, int y) {

    }

    @Override
    public void unHighlightSelectionRequest() {

    }

    @Override
    public void incrementScoreRequest(int points) {

    }

    @Override
    public void controlRequest(int second) {

    }

    @Override
    public void playMatchSound(ArrayList<LinkedList<Emoticon>> matchingX, ArrayList<LinkedList<Emoticon>> matchingY) {

    }

    @Override
    public void playSound(String sound) {

    }

    @Override
    public Emoticon[][] getEmoticons() {
        return new AbstractEmoticon[X_MAX][Y_MAX]; // temp!!
    }

    @Override
    public void setGameEnded(boolean gameEnded) {

    }

    @Override
    public boolean isGameEnded() {
        return false;
    }

    @Override
    public int getEmoWidth() {
        return -1;
    }

    @Override
    public int getEmoHeight() {
        return -1;
    }
}
