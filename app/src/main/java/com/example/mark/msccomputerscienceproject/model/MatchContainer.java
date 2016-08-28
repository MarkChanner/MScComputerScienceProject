package com.example.mark.msccomputerscienceproject.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface MatchContainer {

    void addMatchingX(ArrayList<LinkedList<GamePiece>> matchingX);

    void addMatchingY(ArrayList<LinkedList<GamePiece>> matchingY);

    ArrayList<LinkedList<GamePiece>> getMatchingX();

    ArrayList<LinkedList<GamePiece>> getMatchingY();

    boolean hasMatches();

    int getMatchPoints();

}