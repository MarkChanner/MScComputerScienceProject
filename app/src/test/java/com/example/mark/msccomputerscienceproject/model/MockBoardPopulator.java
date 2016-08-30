package com.example.mark.msccomputerscienceproject.model;

import android.graphics.Bitmap;

import org.mockito.Mock;

/**
 * Extension of BoardPopulatorImpl for testing purposes. Populates a Board with
 * emoticons so that no matches are initially possible. This is done by assigning
 * each emoticon type a unique number to be output when toString() is called.
 *
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class MockBoardPopulator extends BoardPopulatorImpl implements BoardPopulator {

    private static final int EMO_WIDTH = 20;
    private static final int EMO_HEIGHT = 20;
    private Board board;

    @Mock
    Bitmap bitmap;

    public MockBoardPopulator(Board board) {
        super(board);
        this.board = board;
    }

    @Override
    public void populate(GamePieceFactory factory) {
        int offScreenStartPositionY = 0;
        int counter = 0;
        GamePiece emoticon;
        for (int x = ROW_START; x < COLUMNS; x++) {
            for (int y = COLUMN_TOP; y < ROWS; y++) {
                String type = counter <= 9 ? "0" + counter : "" + counter;
                emoticon = new Emoticon(x, y, EMO_WIDTH, EMO_HEIGHT, bitmap, type, offScreenStartPositionY);
                board.setGamePiece(x, y, emoticon);
                counter++;
            }
        }
    }
}