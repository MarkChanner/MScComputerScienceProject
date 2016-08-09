package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class GameBoardImpl implements GameBoard {

    public static final int X_MAX = GameModel.X_MAX;
    public static final int Y_MAX = GameModel.Y_MAX;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    private GamePiece[][] emoticons = new GamePiece[X_MAX][Y_MAX];
    private GamePieceFactory emoFactory;
    private BitmapCreator bitmapCreator;
    private int emoWidth;
    private int emoHeight;

    public GameBoardImpl(BitmapCreator bitmapCreator, int emoWidth, int emoHeight, int level) {
        this.bitmapCreator = bitmapCreator;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
        setEmoFactory(level);
    }

    public void setEmoFactory(int level) {
        if (level == LEVEL_ONE) {
            emoFactory = new EmoticonFactoryLevel01(bitmapCreator, emoWidth, emoHeight);
        } else if (level == LEVEL_TWO) {
            emoFactory = new EmoticonFactoryLevel02(bitmapCreator, emoWidth, emoHeight);
        } else {
            emoFactory = new EmoticonFactoryLevel02(bitmapCreator, emoWidth, emoHeight);
        }
    }

    public GamePiece getRandomGamePiece(int x, int y, int offScreenStartPosition) {
        return emoFactory.getRandomEmo(x, y, offScreenStartPosition);
    }

    public void setRandomGamePiece(int x, int y, int offScreenStartPosition) {
        emoticons[x][y] = emoFactory.getRandomEmo(x, y, offScreenStartPosition);
    }

    public void setBlankGamePiece(int x, int y) {
        emoticons[x][y] = getEmptyGamePiece(x, y);
    }

    public GamePiece getEmptyGamePiece(int x, int y) {
        return emoFactory.createEmptyEmo(x, y);
    }

    public GamePiece getGamePiece(int x, int y) {
        return emoticons[x][y];
    }

    public void setGamePiece(int x, int y, GamePiece emo) {
        this.emoticons[x][y] = emo;
    }

    public void setToDrop(int x, int y) {
        emoticons[x][y].setArrayY(Y_MAX);
        emoticons[x][y].setPixelMovement(32);
        emoticons[x][y].setDropping(true);
    }

    public void populate() {
        GamePiece randomEmo;
        for (int x = ROW_START; x < X_MAX; x++) {

            int dropGap = Y_MAX * 2;

            for (int y = COLUMN_TOP; y < Y_MAX; y++) {

                do {
                    randomEmo = getRandomGamePiece(x, y, ((y - Y_MAX) - dropGap));
                } while (randomEmoCausesMatch(x, y, randomEmo.getEmoType()));

                dropGap--;
                setGamePiece(x, y, randomEmo);
            }
        }
    }

    private boolean randomEmoCausesMatch(int x, int y, String emoType) {
        if (y >= 2 && emoType.equals(getGamePiece(x, y - 1).getEmoType()) &&
                emoType.equals(getGamePiece(x, y - 2).getEmoType()))
            return true;
        else if (x >= 2 && emoType.equals(getGamePiece(x - 1, y).getEmoType()) &&
                emoType.equals(getGamePiece(x - 2, y).getEmoType())) {
            return true;
        }
        return false;
    }

    public void resetBoard() {
        emoticons = new GamePiece[X_MAX][Y_MAX];
        populate();
    }
}
