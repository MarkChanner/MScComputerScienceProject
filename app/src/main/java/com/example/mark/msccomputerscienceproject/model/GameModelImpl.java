package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameController;
import com.example.mark.msccomputerscienceproject.controller.GameControllerImpl;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class GameModelImpl implements GameModel {

    private static final String TAG = "GameModelImpl";

    public static final int X_MAX = GameControllerImpl.X_MAX;
    public static final int Y_MAX = GameControllerImpl.Y_MAX;
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
    private GameBoard gameBoard;
    private Selections selections;
    private MatchFinder matchFinder;
    private int level;
    private int currentLevelScore = 0;

    public GameModelImpl(GameController controller, GameBoard gameBoard) {
        this.controller = controller;
        this.gameBoard = gameBoard;
        initializeGame();
    }

    private void initializeGame() {
        level = 1;
        selections = new SelectionsImpl();
        matchFinder = new MatchFinder();
        gameBoard.populate();
    }


    @Override
    public void updateEmoSwapCoordinates() {
        boolean emoticonsSwapping = false;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {
                if (gameBoard.getGamePiece(x, y).isSwapping()) {
                    emoticonsSwapping = true;
                    gameBoard.getGamePiece(x, y).updateSwapping();
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
                if (gameBoard.getGamePiece(x, y).isDropping()) {
                    emoticonsDropping = true;
                    gameBoard.getGamePiece(x, y).updateDropping();
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
        if (!gameBoard.getGamePiece(x, y).isDropping()) {
            gameBoard.getGamePiece(x, y).setIsSelected(true);
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
                gameBoard.getGamePiece(x, y).setIsSelected(true);
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

        ArrayList<LinkedList<GamePiece>> matchingX = matchFinder.findVerticalMatches(gameBoard);
        ArrayList<LinkedList<GamePiece>> matchingY = matchFinder.findHorizontalMatches(gameBoard);

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
        int emo01X = gameBoard.getGamePiece(sel1[X], sel1[Y]).getArrayX();
        int emo01Y = gameBoard.getGamePiece(sel1[X], sel1[Y]).getArrayY();
        int emo02X = gameBoard.getGamePiece(sel2[X], sel2[Y]).getArrayX();
        int emo02Y = gameBoard.getGamePiece(sel2[X], sel2[Y]).getArrayY();
        GamePiece newEmo2 = gameBoard.getGamePiece(sel1[X], sel1[Y]);

        gameBoard.setGamePiece(sel1[X], sel1[Y], gameBoard.getGamePiece(sel2[X], sel2[Y]));
        gameBoard.getGamePiece(sel1[X], sel1[Y]).setArrayX(emo01X);
        gameBoard.getGamePiece(sel1[X], sel1[Y]).setArrayY(emo01Y);

        gameBoard.setGamePiece(sel2[X], sel2[Y], newEmo2);
        gameBoard.getGamePiece(sel2[X], sel2[Y]).setArrayX(emo02X);
        gameBoard.getGamePiece(sel2[X], sel2[Y]).setArrayY(emo02Y);

        GamePiece emo1 = gameBoard.getGamePiece(sel1[X], sel1[Y]);
        GamePiece emo2 = gameBoard.getGamePiece(sel2[X], sel2[Y]);
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
                gameBoard.getGamePiece(x, y).setIsSelected(false);
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
            matchingX = matchFinder.findVerticalMatches(gameBoard);
            matchingY = matchFinder.findHorizontalMatches(gameBoard);
        } while (matchesFound(matchingX, matchingY));

        if (currentLevelScore >= 500) {
            loadNextLevel();
        } else if (!matchFinder.anotherMatchPossible(gameBoard)) {
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
        if (this.level < MAX_GAME_LEVELS) {
            level++;
            gameBoard.setEmoFactory(level);
        }
        gameBoard.populate();
    }

    @Override
    public void setToDrop() {
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {
                gameBoard.setToDrop(x, y);
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
                if (!gameBoard.getGamePiece(x, y).getEmoType().equals(EMPTY)) {
                    gameBoard.setBlankGamePiece(x, y);
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
                if (gameBoard.getGamePiece(x, y).getEmoType().equals(EMPTY)) {
                    runnerY = y;
                    while ((runnerY >= COLUMN_TOP) && (gameBoard.getGamePiece(x, runnerY).getEmoType().equals(EMPTY))) {
                        runnerY--;
                    }
                    if (runnerY >= COLUMN_TOP) {
                        int tempY = gameBoard.getGamePiece(x, y).getArrayY();
                        gameBoard.setGamePiece(x, y, gameBoard.getGamePiece(x, runnerY));
                        gameBoard.getGamePiece(x, y).setArrayY(tempY);
                        gameBoard.getGamePiece(x, y).setDropping(true);
                        gameBoard.setBlankGamePiece(x, runnerY);
                    } else {
                        gameBoard.setRandomGamePiece(x, y, offScreenStartPosition);
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
    public void resetBoard() {
        selections.resetUserSelections();
        gameBoard.resetBoard();
    }

    @Override
    public void finishRound() {
        unHighlightSelections();
        setToDrop();
        dropEmoticons();
        controller.setGameEnded(true);
    }
}