package com.example.mark.msccomputerscienceproject.TestPackage;

import com.example.mark.msccomputerscienceproject.controller.GameControllerImpl;
import com.example.mark.msccomputerscienceproject.mocks.MockEmoticon;
import com.example.mark.msccomputerscienceproject.mocks.MockGamePiece;
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
public class MatchFinderImpl implements MatchFinder {

    private static final String TAG = "MatchFinderImpl";
    private static final int ROWS = GameControllerImpl.ROWS;
    private static final int COLUMNS = GameControllerImpl.COLUMNS;
    private static final int ROW_START = 0;
    private static final int ROW_END = (COLUMNS - 1);
    private static final int COLUMN_TOP = 0;
    private static final int COLUMN_BOTTOM = (ROWS - 1);
    private static final String EMPTY = "EMPTY";


    @Override
    public int getMatchPoints(ArrayList<LinkedList<GamePiece>> matchingX, ArrayList<LinkedList<GamePiece>> matchingY) {
        int points = 0;
        points += matchPoints(matchingX);
        points += matchPoints(matchingY);
        return points;
    }

    private int matchPoints(ArrayList<LinkedList<GamePiece>> matches) {
        int points = 0;
        for (List<GamePiece> removeList : matches) {
            points += (removeList.size() * 10);
        }
        return points;
    }

    @Override
    public void highlightMatches(ArrayList<LinkedList<GamePiece>> matchingX, ArrayList<LinkedList<GamePiece>> matchingY) {
        highlight(matchingX);
        highlight(matchingY);
    }

    private void highlight(ArrayList<LinkedList<GamePiece>> matches) {
        for (List<GamePiece> removeList : matches) {
            for (GamePiece emo : removeList) {
                emo.setIsPartOfMatch(true);
            }
        }
    }

    @Override
    public ArrayList<LinkedList<GamePiece>> findVerticalMatches(GameBoard gameBoard) {
        LinkedList<GamePiece> consecutiveEmoticons = new LinkedList<>();
        ArrayList<LinkedList<GamePiece>> bigList = new ArrayList<>();
        GamePiece emo;
        for (int x = ROW_START; x < COLUMNS; x++) {
            consecutiveEmoticons.add(gameBoard.getGamePiece(x, COLUMN_BOTTOM));


            for (int y = (COLUMN_BOTTOM - 1); y >= COLUMN_TOP; y--) {
                emo = gameBoard.getGamePiece(x, y);
                if (!emo.getEmoType().equals(consecutiveEmoticons.getLast().getEmoType())) {
                    examineList(consecutiveEmoticons, bigList);
                    consecutiveEmoticons = new LinkedList<>();
                }
                consecutiveEmoticons.add(emo);
                if (y == COLUMN_TOP) {
                    examineList(consecutiveEmoticons, bigList);
                    consecutiveEmoticons = new LinkedList<>();
                }
            }
        }
        return bigList;
    }

    @Override
    public ArrayList<LinkedList<GamePiece>> findHorizontalMatches(GameBoard gameBoard) {
        LinkedList<GamePiece> consecutiveEmoticons = new LinkedList<>();
        ArrayList<LinkedList<GamePiece>> bigList = new ArrayList<>();
        GamePiece emo;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            consecutiveEmoticons.add(gameBoard.getGamePiece(ROW_START, y));

            for (int x = (ROW_START + 1); x < COLUMNS; x++) {
                emo = gameBoard.getGamePiece(x, y);
                if (!(emo.getEmoType().equals(consecutiveEmoticons.getLast().getEmoType()))) {
                    examineList(consecutiveEmoticons, bigList);
                    consecutiveEmoticons = new LinkedList<>();
                }
                consecutiveEmoticons.add(emo);
                if (x == ROW_END) {
                    examineList(consecutiveEmoticons, bigList);
                    consecutiveEmoticons = new LinkedList<>();
                }
            }
        }
        return bigList;
    }

    private void examineList(LinkedList<GamePiece> consecutiveEmotions, ArrayList<LinkedList<GamePiece>> bigList) {
        if ((consecutiveEmotions.size() >= 3) && (allSameType(consecutiveEmotions))) {
            bigList.add(consecutiveEmotions);
        }
    }

    private boolean allSameType(LinkedList<GamePiece> consecutiveEmoticons) {
        String previousEmo = consecutiveEmoticons.getFirst().getEmoType();
        String nextEmo;
        for (int i = 1; i < consecutiveEmoticons.size(); i++) {
            nextEmo = consecutiveEmoticons.get(i).getEmoType();
            if (nextEmo.equals(EMPTY) || (!(nextEmo.equals(previousEmo)))) {
                return false;
            } else {
                previousEmo = nextEmo;
            }
        }
        return true;
    }


    public class MatchFinderTest {

        private static final int ROWS = GameControllerImpl.ROWS;
        private static final int COLUMNS = GameControllerImpl.COLUMNS;
        private static final int ROW_START = 0;
        private static final int ROW_END = (COLUMNS - 1);
        private static final int COLUMN_TOP = 0;
        private static final int COLUMN_BOTTOM = (ROWS - 1);
        private static final String EMPTY = "EMPTY";

        private GameBoard board;
        private Bitmap bitmap = BitmapCreator.getInstance().getEmptyBitmap();
        private MatchFinderImpl matchFinder;
        private ArrayList<LinkedList<Emoticon>> matchingX;
        private ArrayList<LinkedList<Emoticon>> matchingY;

        @Before
        public void setUp() throws Exception {
            this.board = GameBoardImpl.getInstance();
            MockGamePiece emoticon;
            int counter = 0;
            for (int x = ROW_START; x < COLUMNS; x++) {
                for (int y = COLUMN_TOP; y < ROWS; y++) {
                    board.setGamePiece(x, y, emoticon);
                }
                    emoticon[x][y] = new MockEmoticon(x, y, 20, 20, bitmap, "" + counter);
                    counter++;

                }
            }
            this.matchFinder = new MatchFinderImpl();
        }

    /* FROM BOARD MANIPULATOR CLASS
        public void populateBoard(GamePieceFactory emoFactory) {
        GamePiece emoticon;
        for (int x = ROW_START; x < COLUMNS; x++) {

            for (int y = COLUMN_TOP; y < ROWS; y++) {

                if ( {
                    emoticon = emoFactory.getRandomGamePiece(x, y, ((y - ROWS) - dropGap));
                } while (emoticonCausesMatch(board, x, y, emoticon.getEmoType()));

                dropGap--;
                board.setGamePiece(x, y, emoticon);
            }
        }
    }
     */

        @After
        public void tearDown() throws Exception {
            emoticons = null;
            matchFinder = null;
            matchingX = null;
            matchingY = null;
        }

        @Test
        public void testFindVerticalMatches() throws Exception {
            emoticons[2][1] = new MockEmoticon(2, 1, 20, 20, bitmap, "HAPPY");
            emoticons[2][2] = new MockEmoticon(2, 2, 20, 20, bitmap, "HAPPY");
            emoticons[2][3] = new MockEmoticon(2, 3, 20, 20, bitmap, "HAPPY");
            matchingX = matchFinder.findVerticalMatches(emoticons);

            assertEquals(1, matchingX.size());
            assertEquals(3, matchingX.get(0).size());
            assertEquals("HAPPY", matchingX.get(0).get(0).getEmoType());
            assertEquals("HAPPY", matchingX.get(0).get(1).getEmoType());
            assertEquals("HAPPY", matchingX.get(0).get(2).getEmoType());
        }

        @Test
        public void testFindHorizontalMatches() throws Exception {
            emoticons[3][3] = new MockEmoticon(3, 3, 20, 20, bitmap, "SURPRISED");
            emoticons[4][3] = new MockEmoticon(4, 3, 20, 20, bitmap, "SURPRISED");
            emoticons[5][3] = new MockEmoticon(5, 3, 20, 20, bitmap, "SURPRISED");
            emoticons[6][3] = new MockEmoticon(6, 3, 20, 20, bitmap, "SURPRISED");
            matchingY = matchFinder.findHorizontalMatches(emoticons);

            assertEquals(1, matchingY.size());
            assertEquals(4, matchingY.get(0).size());
            assertEquals("SURPRISED", matchingY.get(0).get(0).getEmoType());
            assertEquals("SURPRISED", matchingY.get(0).get(1).getEmoType());
            assertEquals("SURPRISED", matchingY.get(0).get(2).getEmoType());
            assertEquals("SURPRISED", matchingY.get(0).get(3).getEmoType());
        }

        @Test
        public void testAnotherVerticalMatchPossible() throws Exception {
            assertFalse(matchFinder.noFurtherMatchesPossible(emoticons));
            emoticons[2][1] = new MockEmoticon(2, 1, 20, 20, bitmap, "HAPPY");
            emoticons[2][2] = new MockEmoticon(2, 2, 20, 20, bitmap, "HAPPY");
            emoticons[2][4] = new MockEmoticon(2, 4, 20, 20, bitmap, "HAPPY");
            assertTrue(matchFinder.noFurtherMatchesPossible(emoticons));
        }

        @Test
        public void testAnotherHorizontalMatchPossible() throws Exception {
            emoticons[3][3] = new MockEmoticon(3, 3, 20, 20, bitmap, "SURPRISED");
            emoticons[5][3] = new MockEmoticon(5, 3, 20, 20, bitmap, "SURPRISED");
            emoticons[6][3] = new MockEmoticon(6, 3, 20, 20, bitmap, "SURPRISED");
            assertTrue(matchFinder.noFurtherMatchesPossible(emoticons));
        }
    }
