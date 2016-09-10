package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameActivityImpl;

/**
 * Implementation of the BoardPopulator interface that populates a Board with emoticons
 * at random. As this class is used for a matching game where the objective is to match
 * three consecutive emoticons, it ensures that the board is not populated with three
 * of them in a row
 *
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class BoardPopulatorImpl implements BoardPopulator {

    public static final int ROWS = GameActivityImpl.MAX_ROWS;
    public static final int COLUMNS = GameActivityImpl.MAX_COLUMNS;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;

    private Board board;

    public BoardPopulatorImpl(Board board) {
        this.board = board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void populate(GamePieceFactory factory) {
        GamePiece emoticon;
        for (int x = ROW_START; x < COLUMNS; x++) {

            int dropGap = ROWS * 2;

            for (int y = COLUMN_TOP; y < ROWS; y++) {

                do {
                    emoticon = factory.getRandomGamePiece(x, y, ((y - ROWS) - dropGap));
                } while (gamePieceTypeCausesMatch(emoticon));

                dropGap--;
                board.setGamePiece(x, y, emoticon);
            }
        }
    }

    /**
     * Ensures that the passed emoticon would not cause a
     * a vertical or horizontal match of three in a row
     *
     * @param emoticon the emoticon to be checked
     * @return boolean true if placing the passed emoticon would
     * cause three or more horizontally or vertically matching emoticons
     */
    private boolean gamePieceTypeCausesMatch(GamePiece emoticon) {
        int x = emoticon.getArrayX();
        int y = emoticon.getArrayY();
        String emoType = emoticon.toString();
        if (y >= 2 && emoType.equals(board.getGamePiece(x, y - 1).toString()) &&
                emoType.equals(board.getGamePiece(x, y - 2).toString()))
            return true;
        else if (x >= 2 && emoType.equals(board.getGamePiece(x - 1, y).toString()) &&
                emoType.equals(board.getGamePiece(x - 2, y).toString())) {
            return true;
        }
        return false;
    }
}
