package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameController;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class GameModel implements Model {

    private static final String TAG = "GameModel";
    public static final int MAX_GAME_LEVELS = 2;
    public static final int X_MAX = 8;
    public static final int Y_MAX = 7;
    public static final int ONE_SECOND = 1000;
    public static final String INVALID_MOVE = "INVALID_MOVE";

    private int currentLevelScore;
    private GameController controller;
    private LevelManager levelManager;
    private Selections selections;
    private MatchFinder matchHandler;
    private GameBoard board;
    private BoardManipulator boardController;

    public GameModel(GameController controller, int emoWidth, int emoHeight, int level) {
        initializeGameModel(controller, emoWidth, emoHeight, level);
    }

    private void initializeGameModel(GameController controller, int emoWidth, int emoHeight, int level) {
        this.currentLevelScore = 0;
        this.controller = controller;
        this.levelManager = new LevelManagerImpl(emoWidth, emoHeight, level);
        this.selections = new SelectionsImpl();
        this.matchHandler = new MatchFinderImpl();
        this.board = MixedEmotionsBoard.getInstance();
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
            board.getGamePiece(x, y).setIsSelected(true);
            if (!selections.selection01Made()) {
                selections.setSelection01(x, y);
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
            board.getGamePiece(x, y).setIsSelected(true);
            selections.secondSelectionBecomesFirstSelection();
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
            modifyBoard(matchingX, matchingY);
        } else {
            controller.playSound(INVALID_MOVE);
            boardController.swapBack(selections);
        }
        //selections.resetUserSelections();
    }

    /**
     * This method handles the bulk of the requirements for handling a match on the board. It
     * does this within a loop until the manipulated board no longer contains matches.
     *
     * @param matchingX An ArrayList containing a LinkedList of matching vertical GamePieces
     * @param matchingY An ArrayList containing a LinkedList of matching horizontal GamePieces
     */
    private void modifyBoard(ArrayList<LinkedList<GamePiece>> matchingX, ArrayList<LinkedList<GamePiece>> matchingY) {
        Log.d(TAG, "modifyBoard method");
        GamePieceFactory gamePieceFactory = levelManager.getGamePieceFactory();
        int points;
        do {
            points = matchHandler.getMatchPoints(matchingX, matchingY);
            controller.updateScoreBoardView(points);
            currentLevelScore += points;
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
        Log.d(TAG, "matchesFound method");
        return (!(matchingX.isEmpty() && matchingY.isEmpty()));
    }

    private void checkForLevelUp() {
        if (currentLevelScore >= 90) {
            loadNextLevel();
        } else if (!matchHandler.anotherMatchPossible(board)) {
            Log.d(TAG, "NO MATCHES AVAILABLE - END GAME CONDITION ENTERED");
            finishRound();
        }
    }

    private void loadNextLevel() {
        Log.d(TAG, "loadNextLevel()");
        boardController.unHighlightSelections();
        boardController.setToDrop();
        boardController.dropGamePieces(levelManager.getGamePieceFactory());
        currentLevelScore = 0;
        if (levelManager.getGameLevel() < MAX_GAME_LEVELS) {
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