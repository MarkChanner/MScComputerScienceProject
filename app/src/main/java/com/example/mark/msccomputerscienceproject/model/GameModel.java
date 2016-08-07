package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface GameModel {

    void updateEmoticonSwapCoordinates();

    void updateEmoticonDropCoordinates();

    void handleSelection(int x, int y);

    void setToDrop();

    void dropEmoticons();

    void finishRound();

    void resetBoard();

}