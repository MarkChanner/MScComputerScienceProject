package com.example.mark.msccomputerscienceproject.model;

import android.graphics.Bitmap;

import com.example.mark.msccomputerscienceproject.controller.GameActivityImpl;

import org.mockito.Mock;

/**
 *  Populates a GameBoard witH emoticons so that no matches are initially
 *  possible. This is done by assigning each emoticon type a unique number
 *  to be output when toString() is called.
 *
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class MockBoardPopulator implements BoardPopulator {

    public static final int ROWS = GameActivityImpl.MAX_ROWS;
    public static final int COLUMNS = GameActivityImpl.MAX_COLUMNS;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    private static final int TILE_WIDTH = 20;
    private static final int TILE_HEIGHT = 20;
    private GameBoard gameBoard;

    @Mock
    Bitmap bitmap;

    public MockBoardPopulator(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void populate(GamePieceFactory factory) {
        int offScreenStartPositionY = 0;
        int counter = 0;
        GamePiece emoticon;
        for (int x = ROW_START; x < COLUMNS; x++) {
            for (int y = COLUMN_TOP; y < ROWS; y++) {
                String type = counter <= 9 ? "0" + counter : "" + counter;
                emoticon = new Emoticon(x, y, TILE_WIDTH, TILE_HEIGHT, bitmap, type, offScreenStartPositionY);
                gameBoard.setGamePiece(x, y, emoticon);
                counter++;
            }
        }
    }
}