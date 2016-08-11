package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface GameBoard {

    int getWidth();

    int getHeight();

    GamePiece getGamePiece(int x, int y);

    void setGamePiece(int x, int y, GamePiece gamePiece);

    void setToDrop(int x, int y);

    void resetBoard();

}

