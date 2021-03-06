package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameActivityImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class MockBoardManipulator implements BoardManipulator {

    private static final int ROWS = GameActivityImpl.MAX_ROWS;
    private static final int COLUMNS = GameActivityImpl.MAX_COLUMNS;
    private static final int ROW_START = 0;
    private static final int COLUMN_TOP = 0;
    private static final int COLUMN_BOTTOM = (ROWS - 1);
    private static final int X = 0;
    private static final int Y = 1;
    private static final String EMPTY = "EMPTY";

    private GameBoard gameBoard;

    public MockBoardManipulator(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void swap(Selections selections) {
        int[] s01 = selections.getSelection01();
        int[] s02 = selections.getSelection02();

        int emo01X = gameBoard.getGamePiece(s01[X], s01[Y]).getArrayX();
        int emo01Y = gameBoard.getGamePiece(s01[X], s01[Y]).getArrayY();
        int emo02X = gameBoard.getGamePiece(s02[X], s02[Y]).getArrayX();
        int emo02Y = gameBoard.getGamePiece(s02[X], s02[Y]).getArrayY();
        GamePiece newEmo02 = gameBoard.getGamePiece(s01[X], s01[Y]);

        gameBoard.setGamePiece(s01[X], s01[Y], gameBoard.getGamePiece(s02[X], s02[Y]));
        gameBoard.getGamePiece(s01[X], s01[Y]).setArrayX(emo01X);
        gameBoard.getGamePiece(s01[X], s01[Y]).setArrayY(emo01Y);

        gameBoard.setGamePiece(s02[X], s02[Y], newEmo02);
        gameBoard.getGamePiece(s02[X], s02[Y]).setArrayX(emo02X);
        gameBoard.getGamePiece(s02[X], s02[Y]).setArrayY(emo02Y);
    }

    @Override
    public void swapBack(Selections selections) {
        swap(selections);
    }

    @Override
    public void removeFromBoard(MatchContainer matchContainer, GamePieceFactory factory) {
        remove(gameBoard, matchContainer.getMatchingX(), factory);
        remove(gameBoard, matchContainer.getMatchingY(), factory);
    }

    private void remove(GameBoard gameBoard, ArrayList<LinkedList<GamePiece>> matches, GamePieceFactory factory) {
        for (List<GamePiece> removeList : matches) {
            for (GamePiece emo : removeList) {
                int x = emo.getArrayX();
                int y = emo.getArrayY();
                if (!gameBoard.getGamePiece(x, y).toString().equals(EMPTY)) {
                    gameBoard.setGamePiece(x, y, factory.createBlankTile(x, y));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBoard(GamePieceFactory factory) {
        int offScreenStartPosition;
        int runnerY;
        for (int x = ROW_START; x < COLUMNS; x++) {
            offScreenStartPosition = -1;
            for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
                if (gameBoard.getGamePiece(x, y).toString().equals(EMPTY)) {
                    runnerY = y;
                    while ((runnerY >= COLUMN_TOP) && gameBoard.getGamePiece(x, runnerY).toString().equals(EMPTY)) {
                        // Travel up the column and, if emoticon found, fill the empty tile with it
                        runnerY--;
                    }
                    if (runnerY >= COLUMN_TOP) {
                        int tempY = gameBoard.getGamePiece(x, y).getArrayY();
                        gameBoard.setGamePiece(x, y, gameBoard.getGamePiece(x, runnerY));
                        gameBoard.getGamePiece(x, y).setArrayY(tempY);
                        gameBoard.getGamePiece(x, y).setDropping(true);
                        gameBoard.setGamePiece(x, runnerY, factory.createBlankTile(x, runnerY));
                    } else {
                        gameBoard.setGamePiece(x, y, factory.getRandomGamePiece(x, y, offScreenStartPosition));
                        offScreenStartPosition--;
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        // not implemented as uses locking that relies on animation to be released
    }
}
