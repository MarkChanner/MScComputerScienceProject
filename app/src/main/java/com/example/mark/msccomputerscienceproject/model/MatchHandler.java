package com.example.mark.msccomputerscienceproject.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface MatchHandler {

    int getMatchPoints(ArrayList<LinkedList<GamePiece>> matchingX, ArrayList<LinkedList<GamePiece>> matchingY);

    void highlightMatches(ArrayList<LinkedList<GamePiece>> matchingX, ArrayList<LinkedList<GamePiece>> matchingY);

    ArrayList<LinkedList<GamePiece>> findVerticalMatches(GameBoard board);

    ArrayList<LinkedList<GamePiece>> findHorizontalMatches(GameBoard board);

    boolean noFurtherMatchesPossible(GameBoard board);

}
