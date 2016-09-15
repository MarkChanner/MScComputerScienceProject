package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameActivity;

import android.util.Log;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class GameModelImpl implements GameModel {

    private static final String TAG = "GameModel";
    private static final String INVALID_MOVE = "INVALID_MOVE";
    private static final int ONE_SECOND = 1000;
    private static final int SCORE_TARGET_PER_LEVEL = 100;
    private static final int GAME_LEVELS = 2;
    private int currentLevelScore;
    private GameActivity controller;
    private LevelManager levelManager;
    private Selections selections;
    private MatchFinder matchFinder;
    private Board gameBoard;
    private BoardManipulator boardManipulator;
    private BoardPopulator populator;

    public GameModelImpl(GameActivity controller, LevelManager levelManager) {
        initializeGameModel(controller, levelManager);
    }

    private void initializeGameModel(GameActivity controller, LevelManager levelManager) {
        this.currentLevelScore = 0;
        this.controller = controller;
        this.levelManager = levelManager;
        this.selections = new SelectionsImpl();
        this.gameBoard = BoardImpl.getInstance();
        this.matchFinder = new MatchFinderImpl(gameBoard);
        this.boardManipulator = new BoardManipulatorImpl(gameBoard);
        this.populator = new BoardPopulatorImpl(gameBoard);
        populator.populate(levelManager.getGamePieceFactory());
    }

    @Override
    public void updateLogic() {
        boardManipulator.update();
    }

    @Override
    public void handleSelection(int x, int y) {
        Log.d(TAG, "handleSelection(int, int)");
        if (!gameBoard.getGamePiece(x, y).isDropping()) {
            if (!selections.selection01Made()) {
                selections.setSelection01(x, y);
                gameBoard.highlightTile(x, y);
            } else {
                handleSecondSelection(x, y);
            }
        }
    }

    private void handleSecondSelection(int x, int y) {
        selections.setSelection02(x, y);
        gameBoard.clearHighlights();
        if (selections.sameSelectionMadeTwice()) {
            selections.resetSelections();
        } else if (selections.notAdjacent()) {
            selections.secondSelectionToFirstSelection();
            gameBoard.highlightTile(x, y);
        } else {
            boardManipulator.swap(selections);
            checkForMatches(selections);
            selections.resetSelections();
        }
    }

    private void checkForMatches(Selections selections) {
        MatchContainer matchContainer = matchFinder.findMatches();
        if (matchContainer.hasMatches()) {
            handleMatches(matchContainer);
        } else {
            controller.playSound(INVALID_MOVE);
            boardManipulator.swapBack(selections);
        }
    }

    /**
     * This method handles the bulk of the requirements for handling a match on the gameBoard. It
     * does this within a loop until the manipulated gameBoard no longer contains matches.
     *
     * @param matchContainer a wrapper object containing gameBoard matches
     */
    private void handleMatches(MatchContainer matchContainer) {
        Log.d(TAG, "handleMatches method");
        GamePieceFactory gamePieceFactory = levelManager.getGamePieceFactory();
        do {
            currentLevelScore += matchContainer.getMatchPoints();
            controller.updateScoreBoardView(matchContainer.getMatchPoints());
            gameBoard.highlightTile(matchContainer);
            controller.playSound(matchContainer);
            controller.controlGameBoardView(ONE_SECOND);
            boardManipulator.removeFromBoard(matchContainer, gamePieceFactory);
            boardManipulator.lowerGamePieces(gamePieceFactory);
            matchContainer = matchFinder.findMatches();
        } while (matchContainer.hasMatches());
        checkForLevelUp();
    }

    private void checkForLevelUp() {
        if (currentLevelScore >= SCORE_TARGET_PER_LEVEL) {
            loadNextLevel();
        } else if (!matchFinder.furtherMatchesPossible()) {
            Log.d(TAG, "checkForLevelUp() entered condition to call finishRound()");
            finishRound();
        }
    }

    private void loadNextLevel() {
        Log.d(TAG, "loadNextLevel()");
        gameBoard.clearHighlights();
        gameBoard.clearGamePieces();
        boardManipulator.lowerGamePieces(levelManager.getGamePieceFactory());
        currentLevelScore = 0;
        if (levelManager.getLevel() < GAME_LEVELS) {
            levelManager.incrementLevel();
        }
        populator.populate(levelManager.getGamePieceFactory());
    }

    private void finishRound() {
        gameBoard.clearHighlights();
        gameBoard.clearGamePieces();
        boardManipulator.lowerGamePieces(levelManager.getGamePieceFactory());
        controller.setGameEnded(true);
    }

    /**
     * Called from GameController
     */
    @Override
    public void resetGame() {
        selections.resetSelections();
        levelManager.setGameLevel(1);
        gameBoard.reset();
        populator.populate(levelManager.getGamePieceFactory());
    }
}