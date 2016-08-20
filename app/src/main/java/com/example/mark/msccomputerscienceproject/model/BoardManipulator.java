package com.example.mark.msccomputerscienceproject.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface BoardManipulator {

    void populateBoard(GamePieceFactory factory);

    void swap(Selections selections);

    void swapBack(Selections selections);

    void unHighlightSelections();

    void dropGamePieces(GamePieceFactory factory);

    void updateGamePieceSwapCoordinates();

    void updateGamePieceDropCoordinates();

    void setToDrop();

    void replaceGamePieces(ArrayList<LinkedList<GamePiece>> matchingX, ArrayList<LinkedList<GamePiece>> matchingY, GamePieceFactory factory);
}