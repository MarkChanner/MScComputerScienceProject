package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameControllerImpl;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class GameBoardImpl implements GameBoard {

    private static final int ROWS = GameControllerImpl.ROWS;
    private static final int COLUMNS = GameControllerImpl.COLUMNS;
    private static final int frames = 32;
    private GamePiece[][] emoticons = new GamePiece[COLUMNS][ROWS];

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
        if (x >= COLUMNS || y >= ROWS) {
            throw new ArrayIndexOutOfBoundsException("getGamePiece argument larger than board");
        }
        return emoticons[x][y];
    }

    public void setGamePiece(int x, int y, GamePiece gamePiece) {
        if (x >= COLUMNS || y >= ROWS) {
            throw new ArrayIndexOutOfBoundsException("setGamePiece argument larger than board");
        }
        this.emoticons[x][y] = gamePiece;
    }

    public void setToDrop(int x, int y) {
        if (x >= COLUMNS || y >= ROWS) {
            throw new ArrayIndexOutOfBoundsException("setToDrop argument larger than board");
        }
        emoticons[x][y].setArrayY(ROWS);
        emoticons[x][y].setPixelMovement(frames);
        emoticons[x][y].setDropping(true);
    }

    public void resetBoard() {
        emoticons = new GamePiece[COLUMNS][ROWS];
    }

}
