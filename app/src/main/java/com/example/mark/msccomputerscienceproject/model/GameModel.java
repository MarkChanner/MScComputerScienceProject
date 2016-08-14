package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface GameModel {

    void updateLogic();

    void handleSelection(int x, int y);

    void resetGame();

}