package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class BoardManipulatorImpl implements BoardManipulator {

    public static final int X = 0;
    public static final int Y = 1;
    public static final int X_MAX = 8;
    public static final int Y_MAX = 7;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    public static final int COLUMN_BOTTOM = (Y_MAX - 1);
    public static final String EMPTY = "EMPTY";

    private volatile boolean animatingSwap = false;
    private volatile boolean animatingDrop = false;
    private final Object swapLock = new Object();
    private final Object dropLock = new Object();
    private GameBoard board;

    public BoardManipulatorImpl(GameBoard board) {
        this.board = board;
    }

    public void populateBoard(GamePieceFactory emoFactory) {
        GamePiece emoticon;
        int xMax = board.getWidth();
        int yMax = board.getHeight();
        for (int x = ROW_START; x < xMax; x++) {

            int dropGap = yMax * 2;

            for (int y = COLUMN_TOP; y < yMax; y++) {

                do {
                    emoticon = emoFactory.getRandomGamePiece(x, y, ((y - yMax) - dropGap));
                } while (emoticonCausesMatch(board, x, y, emoticon.getEmoType()));

                dropGap--;
                board.setGamePiece(x, y, emoticon);
            }
        }
    }

    private boolean emoticonCausesMatch(GameBoard board, int x, int y, String emoType) {
        if (y >= 2 && emoType.equals(board.getGamePiece(x, y - 1).getEmoType()) &&
                emoType.equals(board.getGamePiece(x, y - 2).getEmoType()))
            return true;
        else if (x >= 2 && emoType.equals(board.getGamePiece(x - 1, y).getEmoType()) &&
                emoType.equals(board.getGamePiece(x - 2, y).getEmoType())) {
            return true;
        }
        return false;
    }
}
