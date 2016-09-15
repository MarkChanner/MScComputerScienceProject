package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science final project
 */
public interface BoardManipulator {

    void swap(Selections selections);

    void swapBack(Selections selections);

    void removeFromBoard(MatchContainer matchContainer, GamePieceFactory factory);

    /**
     * Shifts game pieces down the board
     */
    void updateBoard(GamePieceFactory factory);

    void update();
}
