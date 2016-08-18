package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameControllerImpl;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class GameBoardImpl implements GameBoard {

    private final int boardWidth = GameControllerImpl.X_MAX;
    private final int boardHeight = GameControllerImpl.Y_MAX;
    private GamePiece[][] emoticons = new GamePiece[boardWidth][boardHeight];

    private static GameBoard instance;

    private GameBoardImpl() {
    }

    public static synchronized GameBoard getInstance() {
        if (instance == null) {
            instance = new GameBoardImpl();
        }
        return instance;
    }

    public GamePiece getGamePiece(int x, int y) {
        return emoticons[x][y];
    }

    public void setGamePiece(int x, int y, GamePiece gamePiece) {
        this.emoticons[x][y] = gamePiece;
    }

    public void setToDrop(int x, int y) {
        emoticons[x][y].setArrayY(boardHeight);
        emoticons[x][y].setPixelMovement(32);
        emoticons[x][y].setDropping(true);
    }

    public void resetBoard() {
        emoticons = new GamePiece[boardWidth][boardHeight];
    }

}
