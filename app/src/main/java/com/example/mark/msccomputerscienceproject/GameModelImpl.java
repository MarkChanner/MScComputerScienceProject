package com.example.mark.msccomputerscienceproject;

import android.content.Context;
import android.util.Log;

import com.example.mark.msccomputerscienceproject.emoticon_populator.*;

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
    private Emoticon[][] emoticons;
    private Selections selections;
    private MatchFinder matchFinder;
    private EmoticonCreatorFactory emoCreatorFactory;
    private AbstractGameBoard gameBoard;
    private int level;
    private int currentLevelScore = 0;

    public GameModelImpl(GameController controller, Emoticon[][] emoticons, int emoWidth, int emoHeight) {
        this.controller = controller;
        this.emoticons = emoticons;
        this.selections = new SelectionsImpl();
        this.matchFinder = new MatchFinder();
        this.level = 1;
        Context context = (Context) controller;
        initializeBoard(context, emoWidth, emoHeight);
    }

    private void initializeBoard(Context context, int emoWidth, int emoHeight) {
        BitmapCreator bitmapCreator = BitmapCreator.getInstance();
        bitmapCreator.prepareScaledBitmaps(context, emoWidth, emoHeight);
        this.emoCreatorFactory = new EmoticonCreatorFactory(bitmapCreator, emoWidth, emoHeight);
        this.gameBoard = new GameBoardImpl(emoCreatorFactory.getEmoticonCreator(level));
        this.gameBoard.populate(emoticons);
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
        Log.d(TAG, "in handleSelection(int, int)");
        if (!emoticons[x][y].isDropping()) {
            emoticons[x][y].setIsSelected(true);
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
                emoticons[x][y].setIsSelected(true);
                selections.secondSelectionBecomesFirstSelection();
            }
        } else {
            unHighlightSelections();
            selections.resetUserSelections();
        }
    }

    private void processSelections(int[] sel1, int[] sel2) {
        //Log.d(TAG, "in processSelections(Selections)");
        unHighlightSelections();
        swapSelections(sel1, sel2);

        ArrayList<LinkedList<Emoticon>> matchingX = matchFinder.findVerticalMatches(emoticons);
        ArrayList<LinkedList<Emoticon>> matchingY = matchFinder.findHorizontalMatches(emoticons);

        if (matchesFound(matchingX, matchingY)) {
            modifyBoard(matchingX, matchingY);
        } else {
            controller.playSound(INVALID_MOVE);
            swapBack(sel1, sel2);
        }
        selections.resetUserSelections();
    }

    private void swapSelections(int[] sel1, int[] sel2) {
        //Log.d(TAG, "in swapSelections(int[] int[])");
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
        //Log.d(TAG, "in waitForSwapAnimationToFinish()");
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
        //Log.d(TAG, "in swapBack(int[] int[])");
        swapSelections(sel1, sel2);
    }

    private void unHighlightSelections() {
        for (int x = ROW_START; x < X_MAX; x++) {
            for (int y = COLUMN_TOP; y < Y_MAX; y++) {
                emoticons[x][y].setIsSelected(false);
            }
        }
    }

    private boolean matchesFound(ArrayList<LinkedList<Emoticon>> matchingX, ArrayList<LinkedList<Emoticon>> matchingY) {
        //Log.d(TAG, "in matchesFound method");
        return (!(matchingX.isEmpty() && matchingY.isEmpty()));
    }

    private void modifyBoard(ArrayList<LinkedList<Emoticon>> matchingX, ArrayList<LinkedList<Emoticon>> matchingY) {
        //Log.d(TAG, "in modifyBoard method");
        do {
            highlightMatches(matchingX);
            highlightMatches(matchingY);
            controller.playSound(matchingX, matchingY);
            controller.controlGameBoardView(ONE_SECOND);
            remove(matchingX);
            remove(matchingY);
            dropEmoticons();
            matchingX = matchFinder.findVerticalMatches(emoticons);
            matchingY = matchFinder.findHorizontalMatches(emoticons);
        } while (matchesFound(matchingX, matchingY));

        if (currentLevelScore >= 500) {
            loadNextLevel();
        } else if (!matchFinder.anotherMatchPossible(emoticons)) {
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
        }
        gameBoard.setEmoticonFactory(emoCreatorFactory.getEmoticonCreator(level));
        gameBoard.populate(emoticons);
    }

    @Override
    public void setToDrop() {
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {
                emoticons[x][y].setArrayY(Y_MAX);
                emoticons[x][y].setPixelMovement(32);
                emoticons[x][y].setDropping(true);
            }
        }
    }

    private void highlightMatches(ArrayList<LinkedList<Emoticon>> matches) {
        int points = 0;
        for (List<Emoticon> removeList : matches) {
            for (Emoticon e : removeList) {
                e.setIsPartOfMatch(true);
            }
            points += (removeList.size() * 10);
        }
        controller.updateScoreBoardView(points);
        currentLevelScore += points;
    }

    private void remove(ArrayList<LinkedList<Emoticon>> matches) {
        //  Log.d(TAG, "in remove(ArrayList<LinkedList<Emoticon>>)");
        for (List<Emoticon> removeList : matches) {
            for (Emoticon e : removeList) {
                int x = e.getArrayX();
                int y = e.getArrayY();
                if (!(emoticons[x][y].getEmoticonType().equals(EMPTY))) {
                    emoticons[x][y] = gameBoard.getEmptyEmoticon(x, y);
                }
            }
        }
    }

    @Override
    public void dropEmoticons() {
        //Log.d(TAG, "in dropEmoticons()");
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
                        emoticons[x][runnerY] = gameBoard.getEmptyEmoticon(x, runnerY);
                    } else {
                        emoticons[x][y] = gameBoard.getRandomEmoticon(x, y, offScreenStartPosition);
                        offScreenStartPosition--;
                    }
                }
            }
        }
        waitForDropAnimationToComplete();
    }

    private void waitForDropAnimationToComplete() {
        // Log.d(TAG, "in waitForDropAnimationToComplete()");
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
        emoticons = new AbstractEmoticon[X_MAX][Y_MAX];
        gameBoard.populate(emoticons);
    }

    @Override
    public void finishRound() {
        unHighlightSelections();
        setToDrop();
        dropEmoticons();
        controller.setGameEnded(true);
    }
}