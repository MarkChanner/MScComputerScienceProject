package com.example.mark.msccomputerscienceproject.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class MockGameBoard implements GameBoard {

    private static final int ROWS = 7;
    private static final int COLUMNS = 8;
    private static final int ROW_START = 0;
    private static final int COLUMN_TOP = 0;
    private static final int COLUMN_BOTTOM = (ROWS - 1);
    private static final int X = 0;
    private static final int Y = 1;
    private static final String EMPTY = "EMPTY";
    private static final int frames = 32;

    private GamePiece[][] emoticons = new GamePiece[COLUMNS][ROWS];
    private static GameBoard instance;

    private MockGameBoard() {
    }

    public static synchronized GameBoard getInstance() {
        if (instance == null) {
            instance = new MockGameBoard();
        }
        return instance;
    }

    @Override
    public GamePiece getGamePiece(int x, int y) throws ArrayIndexOutOfBoundsException {
        if (x >= COLUMNS || y >= ROWS) {
            throw new ArrayIndexOutOfBoundsException("getGamePiece argument larger than board");
        }
        return emoticons[x][y];
    }

    @Override
    public void setGamePiece(int x, int y, GamePiece gamePiece) throws ArrayIndexOutOfBoundsException {
        if (x >= COLUMNS || y >= ROWS) {
            throw new ArrayIndexOutOfBoundsException("setGamePiece argument larger than board");
        }
        this.emoticons[x][y] = gamePiece;
    }

    @Override
    public void highlight(int x, int y) {
        emoticons[x][y].setHighlight(true);
    }

    @Override
    public void highlight(MatchContainer matchContainer) {
        highlightMatches(matchContainer.getMatchingX());
        highlightMatches(matchContainer.getMatchingY());
    }

    private void highlightMatches(ArrayList<LinkedList<GamePiece>> matches) {
        for (List<GamePiece> removeList : matches) {
            for (GamePiece emo : removeList) {
                emo.setHighlight(true);
            }
        }
    }

    @Override
    public void clearHighlights() {
        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                emoticons[x][y].setHighlight(false);
            }
        }
    }

    @Override
    public void swap(Selections selections) {
        int[] s01 = selections.getSelection01();
        int[] s02 = selections.getSelection02();

        int emo01X = emoticons[s01[X]][s01[Y]].getArrayX();
        int emo01Y = emoticons[s01[X]][s01[Y]].getArrayY();
        int emo02X = emoticons[s02[X]][s02[Y]].getArrayX();
        int emo02Y = emoticons[s02[X]][s02[Y]].getArrayY();
        GamePiece newEmo02 = emoticons[s01[X]][s01[Y]];

        emoticons[s01[X]][s01[Y]] = emoticons[s02[X]][s02[Y]];
        emoticons[s01[X]][s01[Y]].setArrayX(emo01X);
        emoticons[s01[X]][s01[Y]].setArrayY(emo01Y);

        emoticons[s02[X]][s02[Y]] = newEmo02;
        emoticons[s02[X]][s02[Y]].setArrayX(emo02X);
        emoticons[s02[X]][s02[Y]].setArrayY(emo02Y);
    }

    @Override
    public void swapBack(Selections selections) {
        swap(selections);
    }

    @Override
    public void removeFromBoard(MatchContainer matchContainer, GamePieceFactory factory) {
        remove(matchContainer.getMatchingX(), factory);
        remove(matchContainer.getMatchingY(), factory);
    }

    private void remove(ArrayList<LinkedList<GamePiece>> matches, GamePieceFactory factory) {
        for (List<GamePiece> removeList : matches) {
            for (GamePiece emo : removeList) {
                int x = emo.getArrayX();
                int y = emo.getArrayY();
                if (!emoticons[x][y].toString().equals(EMPTY)) {
                    emoticons[x][y] = factory.createBlankTile(x, y);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lowerGamePieces(GamePieceFactory factory) {
        int offScreenStartPosition;
        int runnerY;
        for (int x = ROW_START; x < COLUMNS; x++) {
            offScreenStartPosition = -1;
            for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
                if (emoticons[x][y].toString().equals(EMPTY)) {
                    runnerY = y;
                    while ((runnerY >= COLUMN_TOP) && emoticons[x][runnerY].toString().equals(EMPTY)) {
                        // Travel up the column and, if emoticon found, fill the empty tile with it
                        runnerY--;
                    }
                    if (runnerY >= COLUMN_TOP) {
                        int tempY = emoticons[x][y].getArrayY();
                        emoticons[x][y] = emoticons[x][runnerY];
                        emoticons[x][y].setArrayY(tempY);
                        emoticons[x][y].setDropping(true);
                        emoticons[x][runnerY] = factory.createBlankTile(x, runnerY);
                    } else {
                        emoticons[x][y] = factory.getRandomGamePiece(x, y, offScreenStartPosition);
                        offScreenStartPosition--;
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        emoticons = new GamePiece[COLUMNS][ROWS];
    }

    @Override
    public void update() {

    }

    @Override
    public void clearGamePieces() {
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < COLUMNS; x++) {
                emoticons[x][y].setArrayY(ROWS);
                emoticons[x][y].setPixelMovement(frames);
                emoticons[x][y].setDropping(true);
            }
        }
    }
}
