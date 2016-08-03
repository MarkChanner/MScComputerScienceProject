package com.example.mark.msccomputerscienceproject;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface GameModel {

    void updateEmoticonSwapCoordinates();

    void updateEmoticonDropCoordinates();

    void handleSelection(int x, int y);

    void finishRound();

    void setToDrop();

    void dropEmoticons();

    void resetBoard();

}