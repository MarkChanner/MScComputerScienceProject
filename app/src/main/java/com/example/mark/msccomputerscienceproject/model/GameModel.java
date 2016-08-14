package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface Model {

    void updateLogic();

    void handleSelection(int x, int y);

    void resetGame();

}