package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameActivityImpl;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class GameBoardImpl implements GameBoard {

    private static final int ROWS = GameActivityImpl.ROWS;
    private static final int COLUMNS = GameActivityImpl.COLUMNS;
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

    @Override
    public GamePiece getGamePiece(int x, int y) throws ArrayIndexOutOfBoundsException {
        if (x >= COLUMNS || y >= ROWS) {
            throw new ArrayIndexOutOfBoundsException("getGamePiece argument larger than board");
        }
        return emoticons[x][y];
    }

    @Override
    public void setGamePiece(int x, int y, GamePiece gamePiece) throws ArrayIndexOutOfBoundsException {
        if (x >= COLUMNS || y >= ROWS) {
            throw new ArrayIndexOutOfBoundsException("setGamePiece argument larger than board");
        }
        this.emoticons[x][y] = gamePiece;
    }

    @Override
    public void setToDrop(int x, int y) throws ArrayIndexOutOfBoundsException{
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
