package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface GameBoard {

    GamePiece getGamePiece(int x, int y) throws ArrayIndexOutOfBoundsException;

    void setGamePiece(int x, int y, GamePiece gamePiece) throws ArrayIndexOutOfBoundsException;

    void setToDrop(int x, int y) throws ArrayIndexOutOfBoundsException;

    void resetBoard();

}

