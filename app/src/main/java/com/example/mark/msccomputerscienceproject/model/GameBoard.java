package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface GameBoard {

    void setEmoticonCreator(int level);

    Emoticon getRandomGamePiece(int x, int y, int offScreenStartPosition);

    void setRandomGamePiece(int x, int y, int offScreenStartPosition);

    void setBlankGamePiece(int x, int y);

    Emoticon getEmptyGamePiece(int x, int y);

    Emoticon getGamePiece(int x, int y);

    void setGamePiece(int x, int y, Emoticon emoticon);

    void setToDrop(int x, int y);

    void populate();

    void resetBoard();
}

