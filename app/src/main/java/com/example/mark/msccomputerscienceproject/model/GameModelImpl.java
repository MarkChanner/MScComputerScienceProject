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
    private Board board;
    private BoardManipulator manipulator;
    private BoardPopulator populator;

    public GameModelImpl(GameActivity controller, LevelManager levelManager) {
        initializeGameModel(controller, levelManager);
    }

    private void initializeGameModel(GameActivity controller, LevelManager levelManager) {
        this.currentLevelScore = 0;
        this.controller = controller;
        this.levelManager = levelManager;
        this.selections = new SelectionsImpl();
        this.board = BoardImpl.getInstance();
        this.matchFinder = new MatchFinderImpl(board);
        this.manipulator = new BoardManipulatorImpl(board);
        this.populator = new BoardPopulatorImpl(board);
        populator.populate(levelManager.getGamePieceFactory());
    }

    @Override
    public void updateLogic() {
        manipulator.update();
    }

    @Override
    public void handleSelection(int x, int y) {
        Log.d(TAG, "handleSelection(int, int)");
        if (!board.getGamePiece(x, y).isDropping()) {
            if (!selections.selection01Made()) {
                selections.setSelection01(x, y);
                board.highlight(x, y);
            } else {
                handleSecondSelection(x, y);
            }
        }
    }

    private void handleSecondSelection(int x, int y) {
        selections.setSelection02(x, y);
        board.clearHighlights();
        if (selections.sameSelectionMadeTwice()) {
            selections.reset();
        } else if (selections.areNotAdjacent()) {
            selections.secondSelectionBecomesFirstSelection();
            board.highlight(x, y);
        } else {
            manipulator.swap(selections);
            checkForMatches(selections);
            selections.reset();
        }
    }

    private void checkForMatches(Selections selections) {
        MatchContainer matchContainer = matchFinder.findMatches();
        if (matchContainer.hasMatches()) {
            handleMatches(matchContainer);
        } else {
            controller.playSound(INVALID_MOVE);
            manipulator.swapBack(selections);
        }
    }

    /**
     * This method handles the bulk of the requirements for handling a match on the board. It
     * does this within a loop until the manipulated board no longer contains matches.
     *
     * @param matchContainer a wrapper object containing board matches
     */
    private void handleMatches(MatchContainer matchContainer) {
        Log.d(TAG, "handleMatches method");
        GamePieceFactory gamePieceFactory = levelManager.getGamePieceFactory();
        do {
            currentLevelScore += matchContainer.getMatchPoints();
            controller.updateScoreBoardView(matchContainer.getMatchPoints());
            board.highlight(matchContainer);
            controller.playSound(matchContainer);
            controller.controlGameBoardView(ONE_SECOND);
            manipulator.removeFromBoard(matchContainer, gamePieceFactory);
            manipulator.lowerGamePieces(gamePieceFactory);
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
        board.clearHighlights();
        board.clearGamePieces();
        manipulator.lowerGamePieces(levelManager.getGamePieceFactory());
        currentLevelScore = 0;
        if (levelManager.getLevel() < GAME_LEVELS) {
            levelManager.incrementLevel();
        }
        populator.populate(levelManager.getGamePieceFactory());
    }

    private void finishRound() {
        board.clearHighlights();
        board.clearGamePieces();
        manipulator.lowerGamePieces(levelManager.getGamePieceFactory());
        controller.setGameEnded(true);
    }

    /**
     * Called from GameController
     */
    @Override
    public void resetGame() {
        selections.reset();
        levelManager.setGameLevel(1);
        board.reset();
        populator.populate(levelManager.getGamePieceFactory());
    }
}