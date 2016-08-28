package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface LevelManager {

    int getLevel();

    void setGameLevel(int level);

    void incrementLevel();

    GamePieceFactory getGamePieceFactory();

}
