package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class BoardPopulatorImpl implements BoardPopulator {

    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;

    public void populateBoard(GameBoard board, GamePieceFactory emoFactory) {
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
