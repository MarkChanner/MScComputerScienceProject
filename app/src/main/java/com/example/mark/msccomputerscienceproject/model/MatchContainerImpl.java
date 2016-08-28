package com.example.mark.msccomputerscienceproject.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class MatchContainerImpl implements MatchContainer {

    private ArrayList<LinkedList<GamePiece>> matchingX;
    private ArrayList<LinkedList<GamePiece>> matchingY;
    private int points = 0;

    @Override
    public void addMatchingX(ArrayList<LinkedList<GamePiece>> matchingX) {
        this.matchingX = matchingX;
        incrementPoints(matchingX);
    }

    @Override
    public void addMatchingY(ArrayList<LinkedList<GamePiece>> matchingY) {
        this.matchingY = matchingY;
        incrementPoints(matchingY);
    }

    @Override
    public ArrayList<LinkedList<GamePiece>> getMatchingX() throws NullPointerException {
        if (this.matchingX == null) throw new NullPointerException();
        return matchingX;
    }

    @Override
    public ArrayList<LinkedList<GamePiece>> getMatchingY() throws NullPointerException {
        if (this.matchingY == null) throw new NullPointerException();
        return matchingY;
    }

    @Override
    public boolean hasMatches() {
        return !(matchingX.isEmpty() && matchingY.isEmpty());
    }

    private void incrementPoints(ArrayList<LinkedList<GamePiece>> matches) {
        for (List<GamePiece> removeList : matches) {
            points += (removeList.size() * 10);
        }
    }

    @Override
    public int getMatchPoints() {
        return points;
    }

}