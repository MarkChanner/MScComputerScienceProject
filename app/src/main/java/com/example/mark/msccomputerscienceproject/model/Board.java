package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface Board {

    /**
     * Returns the GamePiece located at the given coordinates
     *
     * @param x the x value of the GamePiece to retrieve
     * @param y the y value of the GamePiece to retrieve
     * @return GamePiece
     * @throws ArrayIndexOutOfBoundsException
     */
    GamePiece getGamePiece(int x, int y) throws ArrayIndexOutOfBoundsException;

    /**
     * Sets the given GamePiece at the position given by the x and y values
     *
     * @param x         the x value on the board to position the GamePiece
     * @param y         the y value on the board to position the GamePiece
     * @param gamePiece the GamePiece object to be positioned on the board
     * @throws ArrayIndexOutOfBoundsException
     */
    void setGamePiece(int x, int y, GamePiece gamePiece) throws ArrayIndexOutOfBoundsException;

    /**
     * Sets the GamePiece at the given coordinates to be highlighted in the view
     *
     * @param x the x value on the board to be highlighted
     * @param y the y value on the board to be highlighted
     */
    void highlight(int x, int y);

    /**
     * Sets GamePiece objects in the matchContainer to be highlighted in the view
     *
     * @param matchContainer an object that holds matches found on the board
     */
    void highlight(MatchContainer matchContainer);

    /**
     * Sets all GamePiece objects to no longer be highlighted
     */
    void clearHighlights();

    /**
     * Sets the y value of all GamePieces to be below the board. This
     * lets them drop below the screen.
     */
    void clearGamePieces();

    /**
     * Sets the array of GamePieces to null, ready to be repopulated
     */
    void reset();

}

