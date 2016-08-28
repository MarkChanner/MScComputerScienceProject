package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameActivityImpl;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class BoardPopulatorImpl implements BoardPopulator {

    private static final int ROWS = GameActivityImpl.ROWS;
    private static final int COLUMNS = GameActivityImpl.COLUMNS;
    private static final int ROW_START = 0;
    private static final int COLUMN_TOP = 0;

    @Override
    public void populate(GameBoard board, GamePieceFactory factory) {
        GamePiece emoticon;
        for (int x = ROW_START; x < COLUMNS; x++) {

            int dropGap = ROWS * 2;

            for (int y = COLUMN_TOP; y < ROWS; y++) {

                do {
                    emoticon = factory.getRandomGamePiece(x, y, ((y - ROWS) - dropGap));
                } while (gamePieceTypeCausesMatch(emoticon, board, x, y));

                dropGap--;
                board.setGamePiece(x, y, emoticon);
            }
        }
    }

    private boolean gamePieceTypeCausesMatch(GamePiece emoticon, GameBoard board, int x, int y) {
        String emoType = emoticon.getEmoType();
        if (y >= 2 && emoType.equals(board.getGamePiece(x, y - 1).getEmoType()) &&
                emoType.equals(board.getGamePiece(x, y - 2).getEmoType()))
            return true;
        else if (x >= 2 && emoType.equals(board.getGamePiece(x - 1, y).getEmoType()) &&
                emoType.equals(board.getGamePiece(x - 2, y).getEmoType())) {
            return true;
        }
        return false;
    }
}
