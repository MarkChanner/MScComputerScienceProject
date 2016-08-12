package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameController;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class GameModel implements Model {

    private static final String TAG = "GameModel";
    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;
    public static final int X_MAX = 8;
    public static final int Y_MAX = 7;
    public static final int X = 0;
    public static final int Y = 1;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    public static final int COLUMN_BOTTOM = (Y_MAX - 1);
    public static final String EMPTY = "EMPTY";
    public static final String INVALID_MOVE = "INVALID_MOVE";
    public static final int ONE_SECOND = 1000;
    public static final int MAX_GAME_LEVELS = 2;

    private volatile boolean animatingSwap = false;
    private volatile boolean animatingDrop = false;
    private final Object swapLock = new Object();
    private final Object dropLock = new Object();

    private GameController controller;
    private GameBoard board;
    private BoardPopulatorImpl populator;
    private GamePieceFactory emoFactory;
    private Selections selections;
    private MatchFinder matchFinder;
    int emoWidth;
    int emoHeight;
    private int level;
    private int currentLevelScore;

    public GameModel(GameController controller, int emoWidth, int emoHeight, int level) {
        this.controller = controller;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
        this.level = level;
        initializeGameModel();
    }

    private void initializeGameModel() {
        this.currentLevelScore = 0;
        setEmoFactory(level);
        this.board = MixedEmotionsBoard.getInstance();
        this.selections = new SelectionsImpl();
        this.matchFinder = new MatchFinderImpl();
        this.populator = new BoardPopulatorImpl();
        populator.populateBoard(board, emoFactory);
    }

    private void setEmoFactory(int level) {
        if (level == LEVEL_ONE) {
            emoFactory = new EmoticonFactoryLevel01(emoWidth, emoHeight);
        } else if (level == LEVEL_TWO) {
            emoFactory = new EmoticonFactoryLevel02(emoWidth, emoHeight);
        } else {
            emoFactory = new EmoticonFactoryLevel02(emoWidth, emoHeight);
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
    public void handleSelection(int x, int y) {
        Log.d(TAG, "handleSelection(int, int)");
        if (!board.getGamePiece(x, y).isDropping()) {
            board.getGamePiece(x, y).setIsSelected(true);
            if (!selections.selection01Made()) {
                selections.setSelection01(x, y);
            } else {
                selections.setSelection02(x, y);
                checkValidSelections(x, y);
            }
        }
    }

    private void checkValidSelections(int x, int y) {
        if (!selections.sameSelectionMadeTwice()) {
            if (selections.adjacentSelections()) {
                processSelections(selections.getSelection01(), selections.getSelection02());
            } else {
                unHighlightSelections();
                board.getGamePiece(x, y).setIsSelected(true);
                selections.secondSelectionBecomesFirstSelection();
            }
        } else {
            unHighlightSelections();
            selections.resetUserSelections();
        }
    }

    private void processSelections(int[] sel1, int[] sel2) {
        unHighlightSelections();
        swapSelections(sel1, sel2);

        ArrayList<LinkedList<GamePiece>> matchingX = matchFinder.findVerticalMatches(board);
        ArrayList<LinkedList<GamePiece>> matchingY = matchFinder.findHorizontalMatches(board);

        if (matchesFound(matchingX, matchingY)) {
            modifyBoard(matchingX, matchingY);
        } else {
            controller.playSound(INVALID_MOVE);
            swapBack(sel1, sel2);
        }
        selections.resetUserSelections();
    }

    private void swapSelections(int[] sel1, int[] sel2) {
        Log.d(TAG, "swapSelections(int[] int[])");
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

    private void swapBack(int[] sel1, int[] sel2) {
        Log.d(TAG, "swapBack(int[] int[])");
        swapSelections(sel1, sel2);
    }

    private void unHighlightSelections() {
        for (int x = ROW_START; x < X_MAX; x++) {
            for (int y = COLUMN_TOP; y < Y_MAX; y++) {
                board.getGamePiece(x, y).setIsSelected(false);
            }
        }
    }

    private boolean matchesFound(ArrayList<LinkedList<GamePiece>> matchingX, ArrayList<LinkedList<GamePiece>> matchingY) {
        Log.d(TAG, "matchesFound method");
        return (!(matchingX.isEmpty() && matchingY.isEmpty()));
    }

    private void modifyBoard(ArrayList<LinkedList<GamePiece>> matchingX, ArrayList<LinkedList<GamePiece>> matchingY) {
        Log.d(TAG, "modifyBoard method");
        do {
            highlightMatches(matchingX);
            highlightMatches(matchingY);
            controller.playSound(matchingX, matchingY);
            controller.controlGameBoardView(ONE_SECOND);
            remove(matchingX);
            remove(matchingY);
            dropEmoticons();
            matchingX = matchFinder.findVerticalMatches(board);
            matchingY = matchFinder.findHorizontalMatches(board);
        } while (matchesFound(matchingX, matchingY));

        if (currentLevelScore >= 90) {
            loadNextLevel();
        } else if (!matchFinder.anotherMatchPossible(board)) {
            Log.d(TAG, "NO MATCHES AVAILABLE - END GAME CONDITION ENTERED");
            finishRound();
        }
    }

    private void loadNextLevel() {
        Log.d(TAG, "loadNextLevel()");
        unHighlightSelections();
        setToDrop();
        dropEmoticons();
        currentLevelScore = 0;
        if (level < MAX_GAME_LEVELS) {
            level++;
            setEmoFactory(level);
        }
        populator.populateBoard(board, emoFactory);
    }

    @Override
    public void setToDrop() {
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {
                board.setToDrop(x, y);
            }
        }
    }

    private void highlightMatches(ArrayList<LinkedList<GamePiece>> matches) {
        int points = 0;
        for (List<GamePiece> removeList : matches) {
            for (GamePiece emo : removeList) {
                emo.setIsPartOfMatch(true);
            }
            points += (removeList.size() * 10);
        }
        controller.updateScoreBoardView(points);
        currentLevelScore += points;
    }

    private void remove(ArrayList<LinkedList<GamePiece>> matches) {
        Log.d(TAG, "remove(ArrayList<LinkedList<Emoticon>>)");
        for (List<GamePiece> removeList : matches) {
            for (GamePiece emo : removeList) {
                int x = emo.getArrayX();
                int y = emo.getArrayY();
                if (!board.getGamePiece(x, y).getEmoType().equals(EMPTY)) {
                    board.setGamePiece(x, y, emoFactory.createBlankTile(x, y));
                }
            }
        }
    }

    @Override
    public void dropEmoticons() {
        Log.d(TAG, "dropEmoticons()");
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
                        board.setGamePiece(x, runnerY, emoFactory.createBlankTile(x, runnerY));
                    } else {
                        board.setGamePiece(x, y, emoFactory.getRandomGamePiece(x, y, offScreenStartPosition));
                        offScreenStartPosition--;
                    }
                }
            }
        }
        waitForDropAnimationToComplete();
    }

    private void waitForDropAnimationToComplete() {
        Log.d(TAG, "waitForDropAnimationToComplete()");
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
    public void resetGame() {
        selections.resetUserSelections();
        level = 1;
        setEmoFactory(level);
        board.resetBoard();
        populator.populateBoard(board, emoFactory);
    }

    @Override
    public void finishRound() {
        unHighlightSelections();
        setToDrop();
        dropEmoticons();
        controller.setGameEnded(true);
    }
}