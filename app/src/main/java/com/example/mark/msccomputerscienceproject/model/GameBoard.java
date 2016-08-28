package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface GameBoard {

    GamePiece getGamePiece(int x, int y) throws ArrayIndexOutOfBoundsException;

    void setGamePiece(int x, int y, GamePiece gamePiece) throws ArrayIndexOutOfBoundsException;

    void highlight(int x, int y);

    void highlight(MatchContainer matchContainer);

    void clearHighlights();

    void swap(Selections selections);

    void swapBack(Selections selections);

    /**
     * Shifts game pieces down the board
     */
    void lowerGamePieces(GamePieceFactory factory);

    void removeFromBoard(MatchContainer matchContainer, GamePieceFactory factory);

    void update();

    void clearGamePieces();

    void reset();

}

