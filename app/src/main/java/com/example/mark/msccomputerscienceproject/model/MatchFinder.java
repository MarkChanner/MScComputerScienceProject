package com.example.mark.msccomputerscienceproject.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface MatchFinder {

    ArrayList<LinkedList<GamePiece>> findVerticalMatches(GameBoard gameBoard);

    ArrayList<LinkedList<GamePiece>> findHorizontalMatches(GameBoard gameBoard);

    boolean anotherMatchPossible(GameBoard gameBoard);

}
