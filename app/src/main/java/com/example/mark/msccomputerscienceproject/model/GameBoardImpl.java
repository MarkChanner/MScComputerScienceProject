package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class MixedEmotionsBoard implements GameBoard {

    private final int boardWidth = 8;
    private final int boardHeight = 7;
    private GamePiece[][] emoticons = new GamePiece[boardWidth][boardHeight];

    private static MixedEmotionsBoard instance;

    private MixedEmotionsBoard() {
    }

    public static synchronized MixedEmotionsBoard getInstance() {
        if (instance == null) {
            instance = new MixedEmotionsBoard();
        }
        return instance;
    }

    public int getWidth() {
        return boardWidth;
    }

    public int getHeight() {
        return boardHeight;
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
