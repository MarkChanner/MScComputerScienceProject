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
public class BoardManipulatorImplTest {

    private static final int EMO_WIDTH = 20;
    private static final int EMO_HEIGHT = 20;
    private static final int START_POSITION_Y = 0;
    private static final String ANGRY = "ANGRY";
    private static final String HAPPY = "HAPPY";
    private static final String EMBARRASSED = "EMBARRASSED";
    private static final String SAD = "SAD";
    private static final String SURPRISED = "SURPRISED";
    private static final String EMPTY = "EMPTY";

    private GamePieceFactory factory;
    private MatchContainer matchContainer;
    private GameBoard gameBoard;
    private BoardManipulator manipulator;

    @Mock
    private Bitmap bitmap;

    @Before
    public void setUp() throws Exception {
        this.factory = new EmoticonFactoryLevel01(EMO_WIDTH, EMO_HEIGHT);
        this.matchContainer = new MatchContainerImpl();
        this.gameBoard = GameBoardImpl.getInstance();
        // Uses MockBoardManipulator and MockBoardPopulator to enable tests
        this.manipulator = new MockBoardManipulator(gameBoard);
        BoardPopulator populator = new MockBoardPopulator(gameBoard);
        populator.populate(factory);
    }

    @After
    public void tearDown() throws Exception {
        gameBoard.reset();
        matchContainer = null;
    }

    @Test
    public void testSwap() throws Exception {
        gameBoard.setGamePiece(0, 1, new Emoticon(0, 1, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, 0));
        gameBoard.setGamePiece(0, 2, new Emoticon(0, 2, EMO_WIDTH, EMO_HEIGHT, bitmap, EMBARRASSED, 0));

        assertEquals(HAPPY, gameBoard.getGamePiece(0, 1).toString());
        assertEquals(EMBARRASSED, gameBoard.getGamePiece(0, 2).toString());

        Selections selections = new SelectionsImpl();
        selections.setSelection01(0, 1);
        selections.setSelection02(0, 2);

        manipulator.swap(selections);
        assertEquals(EMBARRASSED, gameBoard.getGamePiece(0, 1).toString());
        assertEquals(HAPPY, gameBoard.getGamePiece(0, 2).toString());
    }

    @Test
    public void testRemoveFromBoard() throws Exception {
        LinkedList<GamePiece> consecutiveEmoticons = new LinkedList<>();
        ArrayList<LinkedList<GamePiece>> bigList = new ArrayList<>();
        ArrayList<LinkedList<GamePiece>> emptyList = new ArrayList<>();

        gameBoard.setGamePiece(0, 1, new Emoticon(0, 1, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));
        gameBoard.setGamePiece(0, 2, new Emoticon(0, 2, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));
        gameBoard.setGamePiece(0, 3, new Emoticon(0, 3, EMO_WIDTH, EMO_HEIGHT, bitmap, EMBARRASSED, START_POSITION_Y));

        consecutiveEmoticons.add(gameBoard.getGamePiece(0, 1));
        consecutiveEmoticons.add(gameBoard.getGamePiece(0, 2));
        consecutiveEmoticons.add(gameBoard.getGamePiece(0, 3));
        bigList.add(consecutiveEmoticons);
        matchContainer.addMatchingX(bigList);
        matchContainer.addMatchingY(emptyList);
        manipulator.removeFromBoard(matchContainer, factory);

        assertEquals(EMPTY, gameBoard.getGamePiece(0, 1).toString());
        assertEquals(EMPTY, gameBoard.getGamePiece(0, 2).toString());
        assertEquals(EMPTY, gameBoard.getGamePiece(0, 3).toString());

        // Checks that no error if given coordinates already contains a BlankTile
        manipulator.removeFromBoard(matchContainer, factory);
        assertEquals(EMPTY, gameBoard.getGamePiece(0, 1).toString());
        assertEquals(EMPTY, gameBoard.getGamePiece(0, 2).toString());
        assertEquals(EMPTY, gameBoard.getGamePiece(0, 3).toString());
    }

    @Test
    public void testLowerGamePieces() throws Exception {
        gameBoard.setGamePiece(0, 1, new Emoticon(0, 1, EMO_WIDTH, EMO_HEIGHT, bitmap, HAPPY, START_POSITION_Y));
        gameBoard.setGamePiece(0, 2, new Emoticon(0, 2, EMO_WIDTH, EMO_HEIGHT, bitmap, SAD, START_POSITION_Y));
        gameBoard.setGamePiece(0, 3, new Emoticon(0, 3, EMO_WIDTH, EMO_HEIGHT, bitmap, SURPRISED, START_POSITION_Y));
        // BlankTile
        gameBoard.setGamePiece(0, 4, new BlankTile(0, 4, EMO_WIDTH, EMO_HEIGHT, bitmap));
        gameBoard.setGamePiece(0, 5, new Emoticon(0, 5, EMO_WIDTH, EMO_HEIGHT, bitmap, ANGRY, START_POSITION_Y));

        // Tests emoticons above BlankTile are shifted down to fill it
        manipulator.updateBoard(factory);
        assertEquals(ANGRY, gameBoard.getGamePiece(0, 5).toString());
        assertEquals(SURPRISED, gameBoard.getGamePiece(0, 4).toString());
        assertEquals(SAD, gameBoard.getGamePiece(0, 3).toString());
        assertEquals(HAPPY, gameBoard.getGamePiece(0, 2).toString());
    }
}