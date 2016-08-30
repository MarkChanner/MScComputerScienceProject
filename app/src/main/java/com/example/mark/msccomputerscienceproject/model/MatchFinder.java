package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface MatchFinder {

    boolean getTrue();

    /**
     * Returns a MatchContainer wrapper that contains a List/Lists of matching GamePieces if
     * they were present on the GameBoard. If no matches were found, the returned ArrayList
     * will hold an empty List.
     *
     * @param board the GameBoard implementation to be checked
     * @return An MatchContainer wrapper object
     */
    MatchContainer findMatches(Board board);

    /**
     * Scans the passed GameBoard implementation and checks if it is possible to perform
     * a swap that would yield a match.
     *
     * @param board the GameBoard implementation to be checked
     * @return a boolean value indicating whether further matches are possible
     */
    boolean furtherMatchesPossible(Board board);

}
