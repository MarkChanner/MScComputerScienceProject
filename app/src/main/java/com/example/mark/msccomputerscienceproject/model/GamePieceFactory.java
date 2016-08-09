package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public abstract class GamePieceFactory {

    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;
    protected int emoWidth;
    protected int emoHeight;
    protected BitmapCreator bitmapCreator;


    public GamePieceFactory(BitmapCreator bitmapCreator, int emoWidth, int emoHeight) {
        this.bitmapCreator = bitmapCreator;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
    }

    public static GamePieceFactory getInstance(BitmapCreator bitmapCreator, int emoWidth, int emoHeight) {
        return new EmoticonFactoryLevel01(bitmapCreator, emoWidth, emoHeight);
    }

    // Factory method defers instantiation of emoticon to subclass
    protected abstract GamePiece createRandomEmo(int x, int y, int offScreenStartPositionY);

    public GamePiece getRandomEmo(int x, int y, int offScreenStartPositionY) {
        return createRandomEmo(x, y, offScreenStartPositionY);
    }

    public GamePiece createEmptyEmo(int x, int y) {
        return new EmptyEmoticon(x, y, emoWidth, emoHeight, bitmapCreator.getEmptyBitmap());
    }
}
