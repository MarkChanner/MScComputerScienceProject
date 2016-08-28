package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameActivityImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class GameBoardImpl implements GameBoard {

    private static final int ROWS = GameActivityImpl.ROWS;
    private static final int COLUMNS = GameActivityImpl.COLUMNS;
    private static final int ROW_START = 0;
    private static final int COLUMN_TOP = 0;
    private static final int COLUMN_BOTTOM = (ROWS - 1);
    private static final int X = 0;
    private static final int Y = 1;
    private static final String EMPTY = "EMPTY";
    private static final int frames = 32;

    private final Object swapLock = new Object();
    private final Object dropLock = new Object();
    private volatile boolean animatingSwap = false;
    private volatile boolean animatingDrop = false;

    private GamePiece[][] emoticons = new GamePiece[COLUMNS][ROWS];
    private static GameBoard instance;

    private GameBoardImpl() {
    }

    public static synchronized GameBoard getInstance() {
        if (instance == null) {
            instance = new GameBoardImpl();
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

        GamePiece emo01 = emoticons[s01[X]][s01[Y]];
        GamePiece emo02 = emoticons[s02[X]][s02[Y]];
        setToAnimate(emo01, emo02);
    }

    @Override
    public void swapBack(Selections selections) {
        swap(selections);
    }

    private void setToAnimate(GamePiece emo1, GamePiece emo2) {
        if (emo1.getArrayX() == emo2.getArrayX()) {
            if (emo1.getArrayY() < emo2.getArrayY()) {
                emo1.setSwappingUp(true);
                emo2.setSwappingDown(true);
            } else {
                emo2.setSwappingUp(true);
                emo1.setSwappingDown(true);
            }
        } else if (emo1.getArrayY() == emo2.getArrayY()) {
            if (emo1.getArrayX() < emo2.getArrayX()) {
                emo1.setSwappingLeft(true);
                emo2.setSwappingRight(true);
            } else {
                emo2.setSwappingLeft(true);
                emo1.setSwappingRight(true);
            }
        }
        waitForSwapAnimationToFinish();
    }

    private void waitForSwapAnimationToFinish() {
        synchronized (swapLock) {
            animatingSwap = true;
            while (animatingSwap) {
                try {
                    swapLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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
                if (!emoticons[x][y].getEmoType().equals(EMPTY)) {
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
                if (emoticons[x][y].getEmoType().equals(EMPTY)) {
                    runnerY = y;
                    while ((runnerY >= COLUMN_TOP) && emoticons[x][runnerY].getEmoType().equals(EMPTY)) {
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
        waitForDropAnimationToComplete();
    }

    private void waitForDropAnimationToComplete() {
        synchronized (dropLock) {
            animatingDrop = true;
            while (animatingDrop) {
                try {
                    dropLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
        updateGamePieceSwapCoordinates();
        updateGamePieceDropCoordinates();
    }

    private void updateGamePieceSwapCoordinates() {
        boolean emoticonsSwapping = false;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < COLUMNS; x++) {
                if (emoticons[x][y].isSwapping()) {
                    emoticonsSwapping = true;
                    emoticons[x][y].updateSwapping();
                }
            }
        }
        if (!emoticonsSwapping) {
            synchronized (swapLock) {
                if (animatingSwap) {
                    animatingSwap = false;
                    swapLock.notifyAll();
                }
            }
        }
    }

    private void updateGamePieceDropCoordinates() {
        boolean emoticonsDropping = false;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < COLUMNS; x++) {
                if (emoticons[x][y].isDropping()) {
                    emoticonsDropping = true;
                    emoticons[x][y].updateDropping();
                }
            }
        }
        if (!emoticonsDropping) {
            synchronized (dropLock) {
                if (animatingDrop) {
                    animatingDrop = false;
                    dropLock.notifyAll();
                }
            }
        }
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
