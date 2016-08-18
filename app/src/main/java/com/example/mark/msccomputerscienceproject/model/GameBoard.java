package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface GameBoard {

    GamePiece getGamePiece(int x, int y);

    void setGamePiece(int x, int y, GamePiece gamePiece);

    void setToDrop(int x, int y);

    void resetBoard();

}

