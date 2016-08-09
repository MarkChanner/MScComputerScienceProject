package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface GameBoard {

    int LEVEL_ONE = 1;
    int LEVEL_TWO = 2;

    void setEmoFactory(int level);

    GamePiece getRandomGamePiece(int x, int y, int offScreenStartPosition);

    void setRandomGamePiece(int x, int y, int offScreenStartPosition);

    void setBlankGamePiece(int x, int y);

    GamePiece getEmptyGamePiece(int x, int y);

    GamePiece getGamePiece(int x, int y);

    void setGamePiece(int x, int y, GamePiece emo);

    void setToDrop(int x, int y);

    void populate();

    void resetBoard();
}

