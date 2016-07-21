package com.example.mark.msccomputerscienceproject;

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

    private volatile boolean animatingSwap = false;
    private volatile boolean animatingDrop = false;
    private final Object swapLock = new Object();
    private final Object dropLock = new Object();

    private GameController gameController;
    private GamePopulator gamePopulator;
    private EmoticonCreator emoCreator;
    private Selection selections;
    private Emoticon[][] emoticons;

    private EmoticonMatchFinder emoticonMatchFinder;

    public GameModelImpl(GameController controller, GamePopulator populator, EmoticonCreator emoCreator, Emoticon[][] emoticons) {
        this.gameController = controller;
        this.gamePopulator = populator;
        this.emoCreator = emoCreator;
        this.emoticons = emoticons;
        this.selections = new SelectionImpl();
        this.emoticonMatchFinder = new EmoticonMatchFinder(emoticons);
    }

    @Override
    public void updateEmoticonSwapCoordinates() {
        //Log.d(TAG, "FROM RUN(): in updateEmoticonSwapCoordinates()");
        boolean emoticonsSwapping = false;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {
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

    @Override
    public void updateEmoticonDropCoordinates() {
        //Log.d(TAG, "FROM RUN(): in updateEmoticonDropCoordinates");
        boolean emoticonsDropping = false;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {
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
    public void handleSelection(int x, int y) {
        if (!(selections.selection01Made())) {
            selections.setSelection01(x, y);
        } else {
            selections.setSelection02(x, y);
            gameController.unHighlightSelectionRequest();
            checkValidSelections(x, y);
        }
    }

    private void checkValidSelections(int x, int y) {
        if (!(selections.sameSelectionMadeTwice())) {
            if (selections.adjacentSelections()) {
                processSelections(selections.getSelection01(), selections.getSelection02());
            } else {
                gameController.highlightSelectionRequest(x, y);
                selections.secondSelectionBecomesFirstSelection();
            }
        } else {
            selections.resetUserSelections();
        }
    }

    @Override
    public void processSelections(int[] sel1, int[] sel2) {
        Log.d(TAG, "in processSelections(Selection)");
        swapSelections(sel1, sel2);

        ArrayList<LinkedList<Emoticon>> matchingX = emoticonMatchFinder.findVerticalMatches();
        ArrayList<LinkedList<Emoticon>> matchingY = emoticonMatchFinder.findHorizontalMatches();

        if (matchesFound(matchingX, matchingY)) {
            modifyBoard(matchingX, matchingY);
        } else {
            gameController.playSound(INVALID_MOVE);
            swapBack(sel1, sel2);
        }
        selections.resetUserSelections();
    }

    private void swapSelections(int[] sel1, int[] sel2) {
        Log.d(TAG, "in swapSelections(int[] int[])");
        int emo01X = emoticons[sel1[X]][sel1[Y]].getArrayX();
        int emo01Y = emoticons[sel1[X]][sel1[Y]].getArrayY();
        int emo02X = emoticons[sel2[X]][sel2[Y]].getArrayX();
        int emo02Y = emoticons[sel2[X]][sel2[Y]].getArrayY();
        Emoticon newEmoticon2 = emoticons[sel1[X]][sel1[Y]];

        emoticons[sel1[X]][sel1[Y]] = emoticons[sel2[X]][sel2[Y]];
        emoticons[sel1[X]][sel1[Y]].setArrayX(emo01X);
        emoticons[sel1[X]][sel1[Y]].setArrayY(emo01Y);

        emoticons[sel2[X]][sel2[Y]] = newEmoticon2;
        emoticons[sel2[X]][sel2[Y]].setArrayX(emo02X);
        emoticons[sel2[X]][sel2[Y]].setArrayY(emo02Y);

        Emoticon e1 = emoticons[sel1[X]][sel1[Y]];
        Emoticon e2 = emoticons[sel2[X]][sel2[Y]];
        setEmoticonAnimation(e1, e2);
    }

    private void setEmoticonAnimation(Emoticon e1, Emoticon e2) {
        if (e1.getArrayX() == e2.getArrayX()) {
            if (e1.getArrayY() < e2.getArrayY()) {
                e1.setSwappingUp(true);
                e2.setSwappingDown(true);
            } else {
                e2.setSwappingUp(true);
                e1.setSwappingDown(true);
            }
        } else if (e1.getArrayY() == e2.getArrayY()) {
            if (e1.getArrayX() < e2.getArrayX()) {
                e1.setSwappingLeft(true);
                e2.setSwappingRight(true);
            } else {
                e2.setSwappingLeft(true);
                e1.setSwappingRight(true);
            }
        }
        waitForSwapAnimationToFinish();
    }

    private void waitForSwapAnimationToFinish() {
        Log.d(TAG, "in waitForSwapAnimationToFinish()");
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
        Log.d(TAG, "in swapBack(int[] int[])");
        swapSelections(sel1, sel2);
    }

    private boolean matchesFound(ArrayList<LinkedList<Emoticon>> matchingX, ArrayList<LinkedList<Emoticon>> matchingY) {
        Log.d(TAG, "in matchesFound method");
        return (!(matchingX.isEmpty() && matchingY.isEmpty()));
    }

    private void modifyBoard(ArrayList<LinkedList<Emoticon>> matchingX, ArrayList<LinkedList<Emoticon>> matchingY) {
        Log.d(TAG, "in modifyBoard method");
        do {
            highlightMatches(matchingX);
            highlightMatches(matchingY);
            gameController.playMatchSound(matchingX, matchingY);
            gameController.controlRequest(ONE_SECOND);
            removeFromBoard(matchingX);
            removeFromBoard(matchingY);
            dropEmoticons();
            matchingX = emoticonMatchFinder.findVerticalMatches();
            matchingY = emoticonMatchFinder.findHorizontalMatches();
        } while (matchesFound(matchingX, matchingY));

        if (!emoticonMatchFinder.anotherMatchPossible()) {
            Log.d(TAG, "NO MATCHES AVAILABLE - END GAME CONDITION ENTERED");
            setToDrop();
            dropEmoticons();
            gameController.setGameEnded(true);
        }
    }

    private void setToDrop() {
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {
                emoticons[x][y].setArrayY(Y_MAX);
                emoticons[x][y].setPixelMovement(32);
                emoticons[x][y].setDropping(true);
            }
        }
    }

    private void highlightMatches(ArrayList<LinkedList<Emoticon>> matches) {
        for (List<Emoticon> removeList : matches) {
            gameController.incrementScoreRequest(removeList.size() * 10);
            for (Emoticon e : removeList) {
                e.setIsPartOfMatch(true);
            }
        }
    }

    private void removeFromBoard(ArrayList<LinkedList<Emoticon>> matches) {
        Log.d(TAG, "in removeFromBoard(ArrayList<LinkedList<Emoticon>>)");
        for (List<Emoticon> removeList : matches) {
            for (Emoticon e : removeList) {
                int x = e.getArrayX();
                int y = e.getArrayY();
                if (!(emoticons[x][y].getEmoticonType().equals(EMPTY))) {
                    emoticons[x][y] = emoCreator.getEmptyEmoticon(x, y);
                }
            }
        }
    }

    private void dropEmoticons() {
        Log.d(TAG, "in dropEmoticons()");
        int offScreenStartPosition;
        int runnerY;
        for (int x = ROW_START; x < X_MAX; x++) {
            offScreenStartPosition = -1;
            for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
                if (emoticons[x][y].getEmoticonType().equals(EMPTY)) {
                    runnerY = y;
                    while ((runnerY >= COLUMN_TOP) && (emoticons[x][runnerY].getEmoticonType().equals(EMPTY))) {
                        runnerY--;
                    }
                    if (runnerY >= COLUMN_TOP) {
                        int tempY = emoticons[x][y].getArrayY();
                        emoticons[x][y] = emoticons[x][runnerY];
                        emoticons[x][y].setArrayY(tempY);
                        emoticons[x][y].setDropping(true);
                        emoticons[x][runnerY] = emoCreator.getEmptyEmoticon(x, runnerY);
                    } else {
                        emoticons[x][y] = emoCreator.generateEmoticon(x, y, offScreenStartPosition);
                        offScreenStartPosition--;
                    }
                }
            }
        }
        waitForDropAnimationToComplete();
    }

    private void waitForDropAnimationToComplete() {
        Log.d(TAG, "in waitForDropAnimationToComplete()");
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
        gamePopulator.populateBoard(emoCreator, emoticons);
    }
}