package com.example.mark.msccomputerscienceproject.TestPackage;

import com.example.mark.msccomputerscienceproject.controller.GameControllerImpl;
import com.example.mark.msccomputerscienceproject.model.*;

import android.graphics.Bitmap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class MatchHandlerTest {

    private static final int ROWS = GameControllerImpl.ROWS;
    private static final int COLUMNS = GameControllerImpl.COLUMNS;
    private static final int ROW_START = 0;
    private static final int COLUMN_TOP = 0;
    private final int emoWidth = 20;
    private final int emoHeight = 20;
    private final int offScreenStartPositionY = -10;

    private GameBoard board;
    private Bitmap bitmap = BitmapCreator.getInstance().getEmptyBitmap();
    private MatchHandlerImpl matchHandler;
    private ArrayList<LinkedList<GamePiece>> matchingX;
    private ArrayList<LinkedList<GamePiece>> matchingY;

    @Before
    public void setUp() throws Exception {
        this.board = GameBoardImpl.getInstance();
        this.matchHandler = new MatchHandlerImpl();
        GamePiece emo;
        int emoType = 0;
        for (int x = ROW_START; x < COLUMNS; x++) {
            for (int y = COLUMN_TOP; y < ROWS; y++) {
                emo = new Emoticon(x, y, emoWidth, emoHeight, bitmap, "" + emoType, offScreenStartPositionY);
                board.setGamePiece(x, y, emo);
                emoType++;
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        board.resetBoard();
        matchHandler = null;
        matchingX = null;
        matchingY = null;
    }

    @Test
    public void testFindVerticalMatches() throws Exception {
        final String HAPPY = "HAPPY";
        GamePiece emo01 = new Emoticon(2, 1, emoWidth, emoHeight, bitmap, HAPPY, offScreenStartPositionY);
        board.setGamePiece(2, 1, emo01);

        GamePiece emo02 = new Emoticon(2, 2, emoWidth, emoHeight, bitmap, HAPPY, offScreenStartPositionY);
        board.setGamePiece(2, 2, emo02);

        GamePiece emo03 = new Emoticon(2, 3, emoWidth, emoHeight, bitmap, HAPPY, offScreenStartPositionY);
        board.setGamePiece(2, 3, emo03);

        matchingX = matchHandler.findVerticalMatches(board);
        assertEquals(1, matchingX.size());
        assertEquals(3, matchingX.get(0).size());
        assertEquals("HAPPY", matchingX.get(0).get(0).getEmoType());
        assertEquals("HAPPY", matchingX.get(0).get(1).getEmoType());
        assertEquals("HAPPY", matchingX.get(0).get(2).getEmoType());
    }

    @Test
    public void testFindHorizontalMatches() throws Exception {
        final String SURPRISED = "SURPRISED";

        GamePiece emo01 = new Emoticon(3, 3, emoWidth, emoHeight, bitmap, SURPRISED, offScreenStartPositionY);
        board.setGamePiece(3, 3, emo01);

        GamePiece emo02 = new Emoticon(4, 3, emoWidth, emoHeight, bitmap, SURPRISED, offScreenStartPositionY);
        board.setGamePiece(4, 3, emo02);

        GamePiece emo03 = new Emoticon(5, 3, emoWidth, emoHeight, bitmap, SURPRISED, offScreenStartPositionY);
        board.setGamePiece(5, 3, emo03);

        GamePiece emo04 = new Emoticon(6, 3, emoWidth, emoHeight, bitmap, SURPRISED, offScreenStartPositionY);
        board.setGamePiece(6, 3, emo04);

        matchingY = matchHandler.findHorizontalMatches(board);
        assertEquals(1, matchingY.size());
        assertEquals(4, matchingY.get(0).size());
        assertEquals("SURPRISED", matchingY.get(0).get(0).getEmoType());
        assertEquals("SURPRISED", matchingY.get(0).get(1).getEmoType());
        assertEquals("SURPRISED", matchingY.get(0).get(2).getEmoType());
        assertEquals("SURPRISED", matchingY.get(0).get(3).getEmoType());
    }

    @Test
    public void testAnotherVerticalMatchPossible() throws Exception {
        final String HAPPY = "HAPPY";
        assertTrue(matchHandler.noFurtherMatchesPossible(board));

        GamePiece emo01 = new Emoticon(2, 1, emoWidth, emoHeight, bitmap, HAPPY, offScreenStartPositionY);
        board.setGamePiece(2, 1, emo01);

        GamePiece emo02 = new Emoticon(2, 2, emoWidth, emoHeight, bitmap, HAPPY, offScreenStartPositionY);
        board.setGamePiece(2, 2, emo02);

        GamePiece emo03 = new Emoticon(2, 4, emoWidth, emoHeight, bitmap, HAPPY, offScreenStartPositionY);
        board.setGamePiece(2, 4, emo03);

        assertFalse(matchHandler.noFurtherMatchesPossible(board));
    }

    @Test
    public void testAnotherHorizontalMatchPossible() throws Exception {
        final String SURPRISED = "SURPRISED";
        assertTrue(matchHandler.noFurtherMatchesPossible(board));

        GamePiece emo01 = new Emoticon(3, 3, emoWidth, emoHeight, bitmap, SURPRISED, offScreenStartPositionY);
        board.setGamePiece(3, 3, emo01);

        GamePiece emo02 = new Emoticon(5, 3, emoWidth, emoHeight, bitmap, SURPRISED, offScreenStartPositionY);
        board.setGamePiece(5, 3, emo02);

        GamePiece emo03 = new Emoticon(6, 3, emoWidth, emoHeight, bitmap, SURPRISED, offScreenStartPositionY);
        board.setGamePiece(6, 3, emo03);

        assertFalse(matchHandler.noFurtherMatchesPossible(board));
    }
}
