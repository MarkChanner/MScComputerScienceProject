package com.example.mark.msccomputerscienceproject.model;

import android.graphics.Bitmap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class MatchFinderImplTest {

    private static final int EMO_WIDTH = 20;
    private static final int EMO_HEIGHT = 20;
    private static final int START_POSITION_Y = 0;
    private static final String ANGRY = "ANGRY";
    private static final String HAPPY = "HAPPY";
    private static final String EMBARRASSED = "EMBARRASSED";
    private static final String SURPRISED = "SURPRISED";
    private static final String SAD = "SAD";

    private GameBoard gameBoard;
    private MatchFinder matchFinder;
    private MatchContainer matchContainer;
    private GamePieceFactory factory;
    private BoardPopulator populator;
    private ArrayList<LinkedList<GamePiece>> matchingX;
    private ArrayList<LinkedList<GamePiece>> matchingY;

    @Mock
    private Bitmap bitmap;

    @Before
    public void setUp() throws Exception {
        this.gameBoard = GameBoardImpl.getInstance();
        this.matchFinder = new MatchFinderImpl(gameBoard);
        this.factory = new EmoticonFactoryLevel01(EMO_WIDTH, EMO_HEIGHT);
        this.populator = new MockBoardPopulator(gameBoard);
        populator.populate(factory);
    }

    @After
    public void tearDown() throws Exception {
        gameBoard.reset();
        matchFinder = null;
        matchingX = null;
        matchingY = null;
    }

    @Test
    public void testVerticalAndHorizontalMatches() throws Exception {
        // Set up a vertical match
        gameBoard.setGamePiece(0, 1, new Emoticon(0, 1, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));
        gameBoard.setGamePiece(0, 2, new Emoticon(0, 2, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));
        gameBoard.setGamePiece(0, 3, new Emoticon(0, 3, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));
        gameBoard.setGamePiece(0, 4, new Emoticon(0, 4, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));

        // Set up a horizontal match
        gameBoard.setGamePiece(3, 2, new Emoticon(3, 2, EMO_WIDTH, EMO_HEIGHT, bitmap, SURPRISED, START_POSITION_Y));
        gameBoard.setGamePiece(4, 2, new Emoticon(4, 2, EMO_WIDTH, EMO_HEIGHT, bitmap, SURPRISED, START_POSITION_Y));
        gameBoard.setGamePiece(5, 2, new Emoticon(5, 2, EMO_WIDTH, EMO_HEIGHT, bitmap, SURPRISED, START_POSITION_Y));

        // Sets up just two emoticons to confirm that they are not considered to be a match
        gameBoard.setGamePiece(6, 0, new Emoticon(6, 0, EMO_WIDTH, EMO_HEIGHT, bitmap, ANGRY, START_POSITION_Y));
        gameBoard.setGamePiece(6, 1, new Emoticon(6, 1, EMO_WIDTH, EMO_HEIGHT, bitmap, ANGRY, START_POSITION_Y));

        matchContainer = matchFinder.findMatches();
        matchingX = matchContainer.getMatchingX();
        matchingY = matchContainer.getMatchingY();

        assertEquals(1, matchingX.size());
        assertEquals(4, matchingX.get(0).size());
        assertEquals(HAPPY, matchingX.get(0).get(0).toString());
        assertEquals(HAPPY, matchingX.get(0).get(1).toString());
        assertEquals(HAPPY, matchingX.get(0).get(2).toString());
        assertEquals(HAPPY, matchingX.get(0).get(3).toString());

        assertEquals(1, matchingY.size());
        assertEquals(3, matchingY.get(0).size());
        assertEquals(SURPRISED, matchingY.get(0).get(0).toString());
        assertEquals(SURPRISED, matchingY.get(0).get(1).toString());
        assertEquals(SURPRISED, matchingY.get(0).get(2).toString());
    }

    @Test
    public void testTwoVerticalMatches() throws Exception {
        gameBoard.setGamePiece(2, 1, new Emoticon(2, 1, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));
        gameBoard.setGamePiece(2, 2, new Emoticon(2, 2, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));
        gameBoard.setGamePiece(2, 3, new Emoticon(2, 3, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));

        gameBoard.setGamePiece(4, 4, new Emoticon(4, 4, EMO_WIDTH, EMO_HEIGHT, bitmap, ANGRY, START_POSITION_Y));
        gameBoard.setGamePiece(4, 5, new Emoticon(4, 5, EMO_WIDTH, EMO_HEIGHT, bitmap, ANGRY, START_POSITION_Y));
        gameBoard.setGamePiece(4, 6, new Emoticon(4, 6, EMO_WIDTH, EMO_HEIGHT, bitmap, ANGRY, START_POSITION_Y));

        matchContainer = matchFinder.findMatches();
        matchingX = matchContainer.getMatchingX();
        matchingY = matchContainer.getMatchingY();

        assertEquals(2, matchingX.size());
        assertEquals(3, matchingX.get(0).size());
        assertEquals(3, matchingX.get(1).size());
        assertEquals(HAPPY, matchingX.get(0).get(0).toString());
        assertEquals(HAPPY, matchingX.get(0).get(1).toString());
        assertEquals(HAPPY, matchingX.get(0).get(2).toString());

        assertEquals(3, matchingX.get(1).size());
        assertEquals(ANGRY, matchingX.get(1).get(0).toString());
        assertEquals(ANGRY, matchingX.get(1).get(1).toString());
        assertEquals(ANGRY, matchingX.get(1).get(2).toString());
    }

    @Test
    public void testBaseCaseMatches() throws Exception {
        // Sets 4 vertical matches
        gameBoard.setGamePiece(0, 0, new Emoticon(0, 0, EMO_WIDTH, EMO_HEIGHT, bitmap, ANGRY, START_POSITION_Y));
        gameBoard.setGamePiece(0, 1, new Emoticon(0, 1, EMO_WIDTH, EMO_HEIGHT, bitmap, ANGRY, START_POSITION_Y));
        gameBoard.setGamePiece(0, 2, new Emoticon(0, 2, EMO_WIDTH, EMO_HEIGHT, bitmap, ANGRY, START_POSITION_Y));

        gameBoard.setGamePiece(0, 4, new Emoticon(0, 4, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));
        gameBoard.setGamePiece(0, 5, new Emoticon(0, 5, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));
        gameBoard.setGamePiece(0, 6, new Emoticon(0, 6, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));

        gameBoard.setGamePiece(7, 0, new Emoticon(7, 0, EMO_WIDTH, EMO_HEIGHT, bitmap, EMBARRASSED, START_POSITION_Y));
        gameBoard.setGamePiece(7, 1, new Emoticon(7, 1, EMO_WIDTH, EMO_HEIGHT, bitmap, EMBARRASSED, START_POSITION_Y));
        gameBoard.setGamePiece(7, 2, new Emoticon(7, 2, EMO_WIDTH, EMO_HEIGHT, bitmap, EMBARRASSED, START_POSITION_Y));

        gameBoard.setGamePiece(7, 4, new Emoticon(7, 4, EMO_WIDTH, EMO_HEIGHT, bitmap, SAD, START_POSITION_Y));
        gameBoard.setGamePiece(7, 5, new Emoticon(7, 5, EMO_WIDTH, EMO_HEIGHT, bitmap, SAD, START_POSITION_Y));
        gameBoard.setGamePiece(7, 6, new Emoticon(7, 6, EMO_WIDTH, EMO_HEIGHT, bitmap, SAD, START_POSITION_Y));

        // Sets 3 horizontal matches
        gameBoard.setGamePiece(2, 0, new Emoticon(2, 0, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));
        gameBoard.setGamePiece(3, 0, new Emoticon(3, 0, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));
        gameBoard.setGamePiece(4, 0, new Emoticon(4, 0, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));

        gameBoard.setGamePiece(3, 2, new Emoticon(3, 2, EMO_WIDTH, EMO_HEIGHT, bitmap, EMBARRASSED, START_POSITION_Y));
        gameBoard.setGamePiece(4, 2, new Emoticon(4, 2, EMO_WIDTH, EMO_HEIGHT, bitmap, EMBARRASSED, START_POSITION_Y));
        gameBoard.setGamePiece(5, 2, new Emoticon(5, 2, EMO_WIDTH, EMO_HEIGHT, bitmap, EMBARRASSED, START_POSITION_Y));

        gameBoard.setGamePiece(2, 6, new Emoticon(2, 6, EMO_WIDTH, EMO_HEIGHT, bitmap, ANGRY, START_POSITION_Y));
        gameBoard.setGamePiece(3, 6, new Emoticon(3, 6, EMO_WIDTH, EMO_HEIGHT, bitmap, ANGRY, START_POSITION_Y));
        gameBoard.setGamePiece(4, 6, new Emoticon(4, 6, EMO_WIDTH, EMO_HEIGHT, bitmap, ANGRY, START_POSITION_Y));

        matchContainer = matchFinder.findMatches();
        matchingX = matchContainer.getMatchingX();
        matchingY = matchContainer.getMatchingY();

        // check vertical matches found
        assertEquals(4, matchingX.size());
        assertEquals(HAPPY, matchingX.get(0).get(0).toString());
        assertEquals(ANGRY, matchingX.get(1).get(0).toString());
        assertEquals(SAD, matchingX.get(2).get(0).toString());
        assertEquals(EMBARRASSED, matchingX.get(3).get(0).toString());

        // check horizontal matches found
        assertEquals(3, matchingY.size());
        assertEquals(ANGRY, matchingY.get(0).get(0).toString());
        assertEquals(EMBARRASSED, matchingY.get(1).get(0).toString());
        assertEquals(HAPPY, matchingY.get(2).get(0).toString());
    }
}