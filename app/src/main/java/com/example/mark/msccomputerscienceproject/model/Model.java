package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface Model {

    void updateEmoSwapCoordinates();

    void updateEmoDropCoordinates();

    void handleSelection(int x, int y);

    void setToDrop();

    void dropEmoticons();

    void finishRound();

    void resetBoard();

}