package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameActivityImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science final project
 */
public class BoardManipulatorImpl implements BoardManipulator {

    private static final int ROWS = GameActivityImpl.MAX_ROWS;
    private static final int COLUMNS = GameActivityImpl.MAX_COLUMNS;
    private static final int ROW_START = 0;
    private static final int COLUMN_TOP = 0;
    private static final int COLUMN_BOTTOM = (ROWS - 1);
    private static final int X = 0;
    private static final int Y = 1;
    private static final String EMPTY = "EMPTY";

    private final Object lock = new Object();
    private final Object dropLock = new Object();
    private volatile boolean animatingSwap = false;
    private volatile boolean animatingDrop = false;

    private Board board;

    public BoardManipulatorImpl(Board board) {
        this.board = board;
    }

    @Override
    public void swap(Selections selections) {
        int[] s01 = selections.getSelection01();
        int[] s02 = selections.getSelection02();

        int emo01X = board.getGamePiece(s01[X], s01[Y]).getArrayX();
        int emo01Y = board.getGamePiece(s01[X], s01[Y]).getArrayY();
        int emo02X = board.getGamePiece(s02[X], s02[Y]).getArrayX();
        int emo02Y = board.getGamePiece(s02[X], s02[Y]).getArrayY();
        GamePiece newEmo02 = board.getGamePiece(s01[X], s01[Y]);

        board.setGamePiece(s01[X], s01[Y], board.getGamePiece(s02[X], s02[Y]));
        board.getGamePiece(s01[X], s01[Y]).setArrayX(emo01X);
        board.getGamePiece(s01[X], s01[Y]).setArrayY(emo01Y);

        board.setGamePiece(s02[X], s02[Y], newEmo02);
        board.getGamePiece(s02[X], s02[Y]).setArrayX(emo02X);
        board.getGamePiece(s02[X], s02[Y]).setArrayY(emo02Y);

        GamePiece emo01 = board.getGamePiece(s01[X], s01[Y]);
        GamePiece emo02 = board.getGamePiece(s02[X], s02[Y]);
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
        synchronized (lock) {
            animatingSwap = true;
            while (animatingSwap) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void removeFromBoard(MatchContainer matchContainer, GamePieceFactory factory) {
        remove(board, matchContainer.getMatchingX(), factory);
        remove(board, matchContainer.getMatchingY(), factory);
    }

    private void remove(Board board, ArrayList<LinkedList<GamePiece>> matches, GamePieceFactory factory) {
        for (List<GamePiece> removeList : matches) {
            for (GamePiece emo : removeList) {
                int x = emo.getArrayX();
                int y = emo.getArrayY();
                if (!board.getGamePiece(x, y).toString().equals(EMPTY)) {
                    board.setGamePiece(x, y, factory.createBlankTile(x, y));
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
                if (board.getGamePiece(x, y).toString().equals(EMPTY)) {
                    runnerY = y;
                    while ((runnerY >= COLUMN_TOP) && board.getGamePiece(x, runnerY).toString().equals(EMPTY)) {
                        // Travel up the column and, if emoticon found, fill the empty tile with it
                        runnerY--;
                    }
                    if (runnerY >= COLUMN_TOP) {
                        int tempY = board.getGamePiece(x, y).getArrayY();
                        board.setGamePiece(x, y, board.getGamePiece(x, runnerY));
                        board.getGamePiece(x, y).setArrayY(tempY);
                        board.getGamePiece(x, y).setDropping(true);
                        board.setGamePiece(x, runnerY, factory.createBlankTile(x, runnerY));
                    } else {
                        board.setGamePiece(x, y, factory.getRandomGamePiece(x, y, offScreenStartPosition));
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
    public void update() {
        incrementSwapCoordinates();
        incrementDropCoordinates();
    }

    private void incrementSwapCoordinates() {
        boolean stillAnimating = false;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < COLUMNS; x++) {
                if (board.getGamePiece(x, y).isBeingAnimated()) {
                    stillAnimating = true;
                    board.getGamePiece(x, y).incrementCoordinates();
                }
            }
        }
        if (!stillAnimating) {
            synchronized (lock) {
                if (animatingSwap) {
                    animatingSwap = false;
                    lock.notifyAll();
                }
            }
        }
    }

    private void incrementDropCoordinates() {
        boolean emoticonsDropping = false;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < COLUMNS; x++) {
                if (board.getGamePiece(x, y).isDropping()) {
                    emoticonsDropping = true;
                    board.getGamePiece(x, y).updateDropping();
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

}
