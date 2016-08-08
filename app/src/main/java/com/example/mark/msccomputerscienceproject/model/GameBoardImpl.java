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
    private AbstractEmoticonCreator emoCreator;
    private BitmapCreator bitmapCreator;
    private int emoWidth;
    private int emoHeight;

    public GameBoardImpl(BitmapCreator bitmapCreator, int emoWidth, int emoHeight, int level) {
        this.bitmapCreator = bitmapCreator;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
        setEmoticonCreator(level);
    }

    public void setEmoticonCreator(int level) {
        if (level == LEVEL_ONE) {
            emoCreator = new EmoticonCreatorLevel01(bitmapCreator, emoWidth, emoHeight);
        } else if (level == LEVEL_TWO) {
            emoCreator = new EmoticonCreatorLevel02(bitmapCreator, emoWidth, emoHeight);
        } else {
            emoCreator = new EmoticonCreatorLevel02(bitmapCreator, emoWidth, emoHeight);
        }
    }

    public Emoticon getRandomGamePiece(int x, int y, int offScreenStartPosition) {
        return emoCreator.getRandomEmoticon(x, y, offScreenStartPosition);
    }

    public void setRandomGamePiece(int x, int y, int offScreenStartPosition) {
        emoticons[x][y] = emoCreator.getRandomEmoticon(x, y, offScreenStartPosition);
    }

    public void setBlankGamePiece(int x, int y) {
        emoticons[x][y] = getEmptyGamePiece(x, y);
    }

    public Emoticon getEmptyGamePiece(int x, int y) {
        return emoCreator.createEmptyEmoticon(x, y);
    }

    public Emoticon getGamePiece(int x, int y) {
        return emoticons[x][y];
    }

    public void setGamePiece(int x, int y, Emoticon emoticon) {
        this.emoticons[x][y] = emoticon;
    }

    public void setToDrop(int x, int y) {
        emoticons[x][y].setArrayY(Y_MAX);
        emoticons[x][y].setPixelMovement(32);
        emoticons[x][y].setDropping(true);
    }

    public void populate() {
        Emoticon newEmoticon;
        for (int x = ROW_START; x < X_MAX; x++) {
            int dropGap = Y_MAX * 2;
            for (int y = COLUMN_TOP; y < Y_MAX; y++) {
                do {
                    newEmoticon = getRandomGamePiece(x, y, ((y - Y_MAX) - dropGap));

                } while ((y >= 2 &&
                        (newEmoticon.getEmoticonType().equals(getGamePiece(x, y - 1).getEmoticonType()) &&
                                newEmoticon.getEmoticonType().equals(getGamePiece(x, y - 2).getEmoticonType()))) ||
                        (x >= 2 &&
                                (newEmoticon.getEmoticonType().equals(getGamePiece(x - 1, y).getEmoticonType()) &&
                                        newEmoticon.getEmoticonType().equals(getGamePiece(x - 2, y).getEmoticonType()))));

                dropGap--;
                setGamePiece(x, y, newEmoticon);
            }
        }
    }

    public void resetBoard() {
        emoticons = new AbstractEmoticon[X_MAX][Y_MAX];
        populate();
    }
}
