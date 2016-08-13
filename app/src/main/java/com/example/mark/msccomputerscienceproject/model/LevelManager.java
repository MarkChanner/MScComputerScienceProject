package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface LevelManager {

    int getGameLevel();

    void setGameLevel(int level);

    void incrementLevel();

    GamePieceFactory getGamePieceFactory();

}
