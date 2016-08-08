package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public abstract class AbstractEmoticonCreator implements EmoticonCreator {

    protected int emoWidth;
    protected int emoHeight;
    protected BitmapCreator bitmapCreator;

    public AbstractEmoticonCreator(BitmapCreator bitmapCreator, int emoWidth, int emoHeight) {
        this.bitmapCreator = bitmapCreator;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
    }

    // Factory method defers instantiation of emoticon to subclass
    protected abstract Emoticon createRandomEmoticon(int x, int y, int offScreenStartPositionY);

    public Emoticon getRandomEmoticon(int x, int y, int offScreenStartPositionY) {
        return createRandomEmoticon(x, y, offScreenStartPositionY);
    }

    public Emoticon createEmptyEmoticon(int x, int y) {
        return new EmptyEmoticon(x, y, emoWidth, emoHeight, bitmapCreator.getEmptyBitmap());
    }

}
