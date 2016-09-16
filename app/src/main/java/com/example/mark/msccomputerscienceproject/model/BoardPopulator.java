package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface BoardPopulator {

    /**
     * Populates a given GameBoard with games pieces in the given factory
     *
     * @param factory the GamePieceFactory to populate the board with
     */
    void populate(GamePieceFactory factory);

}