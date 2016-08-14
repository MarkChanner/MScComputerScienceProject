package com.example.mark.msccomputerscienceproject.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class BoardManipulatorImpl implements BoardManipulator {

    public static final int X = 0;
    public static final int Y = 1;
    public static final int X_MAX = 8;
    public static final int Y_MAX = 7;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    public static final int COLUMN_BOTTOM = (Y_MAX - 1);
    public static final String EMPTY = "EMPTY";

    private volatile boolean animatingSwap = false;
    private volatile boolean animatingDrop = false;
    private final Object swapLock = new Object();
    private final Object dropLock = new Object();
    private GameBoard board;

    public BoardManipulatorImpl(GameBoard board) {
        this.board = board;
    }

    public void populateBoard(GamePieceFactory emoFactory) {
        GamePiece emoticon;
        int xMax = board.getWidth();
        int yMax = board.getHeight();
        for (int x = ROW_START; x < xMax; x++) {

            int dropGap = yMax * 2;

            for (int y = COLUMN_TOP; y < yMax; y++) {

                do {
                    emoticon = emoFactory.getRandomGamePiece(x, y, ((y - yMax) - dropGap));
                } while (emoticonCausesMatch(board, x, y, emoticon.getEmoType()));

                dropGap--;
                board.setGamePiece(x, y, emoticon);
            }
        }
    }

    private boolean emoticonCausesMatch(GameBoard board, int x, int y, String emoType) {
        if (y >= 2 && emoType.equals(board.getGamePiece(x, y - 1).getEmoType()) &&
                emoType.equals(board.getGamePiece(x, y - 2).getEmoType()))
            return true;
        else if (x >= 2 && emoType.equals(board.getGamePiece(x - 1, y).getEmoType()) &&
                emoType.equals(board.getGamePiece(x - 2, y).getEmoType())) {
            return true;
        }
        return false;
    }

    @Override
    public void unHighlightSelections() {
        for (int x = ROW_START; x < X_MAX; x++) {
            for (int y = COLUMN_TOP; y < Y_MAX; y++) {
                board.getGamePiece(x, y).setIsSelected(false);
            }
        }
    }

    @Override
    public void swapSelections(Selections selections) {
        int[] sel1 = selections.getSelection01();
        int[] sel2 = selections.getSelection02();

        int emo01X = board.getGamePiece(sel1[X], sel1[Y]).getArrayX();
        int emo01Y = board.getGamePiece(sel1[X], sel1[Y]).getArrayY();
        int emo02X = board.getGamePiece(sel2[X], sel2[Y]).getArrayX();
        int emo02Y = board.getGamePiece(sel2[X], sel2[Y]).getArrayY();
        GamePiece newEmo2 = board.getGamePiece(sel1[X], sel1[Y]);

        board.setGamePiece(sel1[X], sel1[Y], board.getGamePiece(sel2[X], sel2[Y]));
        board.getGamePiece(sel1[X], sel1[Y]).setArrayX(emo01X);
        board.getGamePiece(sel1[X], sel1[Y]).setArrayY(emo01Y);

        board.setGamePiece(sel2[X], sel2[Y], newEmo2);
        board.getGamePiece(sel2[X], sel2[Y]).setArrayX(emo02X);
        board.getGamePiece(sel2[X], sel2[Y]).setArrayY(emo02Y);

        GamePiece emo1 = board.getGamePiece(sel1[X], sel1[Y]);
        GamePiece emo2 = board.getGamePiece(sel2[X], sel2[Y]);
        setEmoAnimation(emo1, emo2);
    }

    public void swapBack(Selections selections) {
        swapSelections(selections);
    }

    private void setEmoAnimation(GamePiece emo1, GamePiece emo2) {
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
    public void dropEmoticons(GamePieceFactory gamePieceFactory) {
        int offScreenStartPosition;
        int runnerY;
        for (int x = ROW_START; x < X_MAX; x++) {
            offScreenStartPosition = -1;
            for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
                if (board.getGamePiece(x, y).getEmoType().equals(EMPTY)) {
                    runnerY = y;
                    while ((runnerY >= COLUMN_TOP) && (board.getGamePiece(x, runnerY).getEmoType().equals(EMPTY))) {
                        runnerY--;
                    }
                    if (runnerY >= COLUMN_TOP) {
                        int tempY = board.getGamePiece(x, y).getArrayY();
                        board.setGamePiece(x, y, board.getGamePiece(x, runnerY));
                        board.getGamePiece(x, y).setArrayY(tempY);
                        board.getGamePiece(x, y).setDropping(true);
                        board.setGamePiece(x, runnerY, gamePieceFactory.createBlankTile(x, runnerY));
                    } else {
                        board.setGamePiece(x, y, gamePieceFactory.getRandomGamePiece(x, y, offScreenStartPosition));
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
    public void updateEmoSwapCoordinates() {
        boolean emoticonsSwapping = false;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {
                if (board.getGamePiece(x, y).isSwapping()) {
                    emoticonsSwapping = true;
                    board.getGamePiece(x, y).updateSwapping();
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

    @Override
    public void updateEmoDropCoordinates() {
        boolean emoticonsDropping = false;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {
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

    @Override
    public void setToDrop() {
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {
                board.setToDrop(x, y);
            }
        }
    }

    @Override
    public void replaceMatches(ArrayList<LinkedList<GamePiece>> matchingX, ArrayList<LinkedList<GamePiece>> matchingY, GamePieceFactory gamePieceFactory) {
        replace(matchingX, gamePieceFactory);
        replace(matchingY, gamePieceFactory);
    }

    private void replace(ArrayList<LinkedList<GamePiece>> matches, GamePieceFactory gamePieceFactory) {
        for (List<GamePiece> removeList : matches) {
            for (GamePiece emo : removeList) {
                int x = emo.getArrayX();
                int y = emo.getArrayY();
                if (!board.getGamePiece(x, y).getEmoType().equals(EMPTY)) {
                    board.setGamePiece(x, y, gamePieceFactory.createBlankTile(x, y));
                }
            }
        }
    }
}
