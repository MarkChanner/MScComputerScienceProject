package com.example.mark.msccomputerscienceproject.model;

import android.graphics.Bitmap;

import com.example.mark.msccomputerscienceproject.controller.GameActivityImpl;

import org.mockito.Mock;

/**
 * Populates the board with emoticons. So that no matches are
 * initially possible, each emoticon type is a unique number.
 *
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class MockBoardPopulator extends BoardPopulatorImpl implements BoardPopulator {

    @Mock
    Bitmap bitmap;

    private static final int ROW_START = 0;
    private static final int COLUMN_TOP = 0;
    private static final int ROWS = GameActivityImpl.ROWS;
    private static final int COLS = GameActivityImpl.COLUMNS;
    private static final int EMO_WIDTH = 20;
    private static final int EMO_HEIGHT = 20;

    @Override
    public void populate(GameBoard board, GamePieceFactory factory) {
        int offScreenStartPositionY = 0;
        int counter = 0;
        GamePiece emoticon;
        for (int x = ROW_START; x < COLS; x++) {
            for (int y = COLUMN_TOP; y < ROWS; y++) {
                String type = counter <= 9 ? "0" + counter : "" + counter;
                emoticon = new Emoticon(x, y, EMO_WIDTH, EMO_HEIGHT, bitmap, type, offScreenStartPositionY);
                board.setGamePiece(x, y, emoticon);
                counter++;
            }
        }
    }
}