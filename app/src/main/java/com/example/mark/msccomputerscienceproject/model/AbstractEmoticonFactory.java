package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public abstract class AbstractEmoticonFactory implements EmoticonFactory {

    protected int emoWidth;
    protected int emoHeight;
    protected BitmapCreator bitmapCreator;

    public AbstractEmoticonFactory(BitmapCreator bitmapCreator, int emoWidth, int emoHeight) {
        this.bitmapCreator = bitmapCreator;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
    }

    // Factory method defers instantiation of emoticon to subclass
    protected abstract Emoticon createRandomEmo(int x, int y, int offScreenStartPositionY);

    public Emoticon getRandomEmo(int x, int y, int offScreenStartPositionY) {
        return createRandomEmo(x, y, offScreenStartPositionY);
    }

    public Emoticon createEmptyEmo(int x, int y) {
        return new EmptyEmoticon(x, y, emoWidth, emoHeight, bitmapCreator.getEmptyBitmap());
    }

}
