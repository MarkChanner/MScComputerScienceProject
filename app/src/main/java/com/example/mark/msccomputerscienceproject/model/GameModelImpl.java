package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameController;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class GameModelImpl implements GameModel {

    private static final String TAG = "GameModel";
    private static final String INVALID_MOVE = "INVALID_MOVE";
    private static final int ONE_SECOND = 1000;

    private static final int SCORE_TARGET_PER_LEVEL = 300;
    private static final int GAME_LEVELS = 2;
    private int currentLevelScore;
    private GameController controller;
    private LevelManager levelManager;
    private Selections selections;
    private MatchFinder matchHandler;
    private GameBoard board;
    private BoardManipulator boardController;

    public GameModelImpl(GameController controller, int emoWidth, int emoHeight, int level) {
        initializeGameModel(controller, emoWidth, emoHeight, level);
    }

    private void initializeGameModel(GameController controller, int emoWidth, int emoHeight, int level) {
        this.currentLevelScore = 0;
        this.controller = controller;
        this.levelManager = new LevelManagerImpl(emoWidth, emoHeight, level);
        this.selections = new SelectionsImpl();
        this.matchHandler = new MatchFinderImpl();
        this.board = GameBoardImpl.getInstance();
        this.boardController = new BoardManipulatorImpl(board);
        boardController.populateBoard(levelManager.getGamePieceFactory());
    }

    @Override
    public void updateLogic() {
        boardController.updateGamePieceSwapCoordinates();
        boardController.updateGamePieceDropCoordinates();
    }

    @Override
    public void handleSelection(int x, int y) {
        Log.d(TAG, "handleSelection(int, int)");
        if (!board.getGamePiece(x, y).isDropping()) {
            if (!selections.selection01Made()) {
                selections.setSelection01(x, y);
                board.getGamePiece(x, y).setIsSelected(true);
            } else {
                handleSecondSelection(x, y);
            }
        }
    }

    private void handleSecondSelection(int x, int y) {
        selections.setSelection02(x, y);
        boardController.unHighlightSelections();
        if (selections.sameSelectionMadeTwice()) {
            selections.resetUserSelections();
        } else if (selections.areNotAdjacent()) {
            selections.secondSelectionBecomesFirstSelection();
            board.getGamePiece(x, y).setIsSelected(true);
        } else {
            boardController.swap(selections);
            checkForMatches(selections);
            selections.resetUserSelections();
        }
    }

    private void checkForMatches(Selections selections) {
        ArrayList<LinkedList<GamePiece>> matchingX = matchHandler.findVerticalMatches(board);
        ArrayList<LinkedList<GamePiece>> matchingY = matchHandler.findHorizontalMatches(board);
        if (matchesFound(matchingX, matchingY)) {
            handleMatches(matchingX, matchingY);
        } else {
            controller.playSound(INVALID_MOVE);
            boardController.swapBack(selections);
        }
    }

    /**
     * This method handles the bulk of the requirements for handling a match on the board. It
     * does this within a loop until the manipulated board no longer contains matches.
     *
     * @param matchingX An ArrayList containing a LinkedList of matching vertical GamePieces
     * @param matchingY An ArrayList containing a LinkedList of matching horizontal GamePieces
     */
    private void handleMatches(ArrayList<LinkedList<GamePiece>> matchingX, ArrayList<LinkedList<GamePiece>> matchingY) {
        Log.d(TAG, "handleMatches method");
        GamePieceFactory gamePieceFactory = levelManager.getGamePieceFactory();
        int points;
        do {
            points = matchHandler.getMatchPoints(matchingX, matchingY);
            currentLevelScore += points;
            controller.updateScoreBoardView(points);
            matchHandler.highlightMatches(matchingX, matchingY);
            controller.playSound(matchingX, matchingY);
            controller.controlGameBoardView(ONE_SECOND);
            boardController.replaceGamePieces(matchingX, matchingY, gamePieceFactory);
            boardController.dropGamePieces(gamePieceFactory);
            matchingX = matchHandler.findVerticalMatches(board);
            matchingY = matchHandler.findHorizontalMatches(board);
        } while (matchesFound(matchingX, matchingY));
        checkForLevelUp();
    }

    private boolean matchesFound(ArrayList<LinkedList<GamePiece>> matchingX, ArrayList<LinkedList<GamePiece>> matchingY) {
        return (!(matchingX.isEmpty() && matchingY.isEmpty()));
    }

    private void checkForLevelUp() {
        if (currentLevelScore >= SCORE_TARGET_PER_LEVEL) {
            loadNextLevel();
        } else if (matchHandler.noFurtherMatchesPossible(board)) {
            Log.d(TAG, "checkForLevelUp() entered condition to call finishRound()");
            finishRound();
        }
    }

    private void loadNextLevel() {
        Log.d(TAG, "loadNextLevel()");
        boardController.unHighlightSelections();
        boardController.setToDrop();
        boardController.dropGamePieces(levelManager.getGamePieceFactory());
        currentLevelScore = 0;
        if (levelManager.getGameLevel() < GAME_LEVELS) {
            levelManager.incrementLevel();
        }
        boardController.populateBoard(levelManager.getGamePieceFactory());
    }

    private void finishRound() {
        boardController.unHighlightSelections();
        boardController.setToDrop();
        boardController.dropGamePieces(levelManager.getGamePieceFactory());
        controller.setGameEnded(true);
    }

    /**
     * Called from GameController
     */
    @Override
    public void resetGame() {
        selections.resetUserSelections();
        levelManager.setGameLevel(1);
        board.resetBoard();
        boardController.populateBoard(levelManager.getGamePieceFactory());
    }
}