package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class GameBoardImpl implements GameBoard {

    public static final int X_MAX = GameModelImpl.X_MAX;
    public static final int Y_MAX = GameModelImpl.Y_MAX;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    private Emoticon[][] emoticons = new AbstractEmoticon[X_MAX][Y_MAX];
    private EmoticonFactory emoFactory;
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

    public Emoticon getRandomGamePiece(int x, int y, int offScreenStartPosition) {
        return emoFactory.getRandomEmo(x, y, offScreenStartPosition);
    }

    public void setRandomGamePiece(int x, int y, int offScreenStartPosition) {
        emoticons[x][y] = emoFactory.getRandomEmo(x, y, offScreenStartPosition);
    }

    public void setBlankGamePiece(int x, int y) {
        emoticons[x][y] = getEmptyGamePiece(x, y);
    }

    public Emoticon getEmptyGamePiece(int x, int y) {
        return emoFactory.createEmptyEmo(x, y);
    }

    public Emoticon getGamePiece(int x, int y) {
        return emoticons[x][y];
    }

    public void setGamePiece(int x, int y, Emoticon emo) {
        this.emoticons[x][y] = emo;
    }

    public void setToDrop(int x, int y) {
        emoticons[x][y].setArrayY(Y_MAX);
        emoticons[x][y].setPixelMovement(32);
        emoticons[x][y].setDropping(true);
    }

    public void populate() {
        Emoticon randomEmo;
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
        emoticons = new AbstractEmoticon[X_MAX][Y_MAX];
        populate();
    }
}
