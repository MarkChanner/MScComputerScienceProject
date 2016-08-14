package com.example.mark.msccomputerscienceproject.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface BoardManipulator {

    void populateBoard(GamePieceFactory gamePieceFactory);

    void swapSelections(Selections selections);

    void swapBack(Selections selections);

    void unHighlightSelections();

    void dropEmoticons(GamePieceFactory gamePieceFactory);

    void updateEmoSwapCoordinates();

    void updateEmoDropCoordinates();

    void setToDrop();

    void replaceMatches(ArrayList<LinkedList<GamePiece>> matchingX, ArrayList<LinkedList<GamePiece>> matchingY, GamePieceFactory gamePieceFactory);
}