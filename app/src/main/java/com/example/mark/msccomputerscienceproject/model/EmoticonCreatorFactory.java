package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class EmoticonCreatorFactory {

    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;
    private BitmapCreator bitmapCreator;
    private int emoWidth;
    private int emoHeight;

    public EmoticonCreatorFactory(BitmapCreator bitmapCreator, int emoWidth, int emoHeight) {
        this.bitmapCreator = bitmapCreator;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
    }

    /**
     * Returns the correct subclass of AbstractEmoticonCreator.
     * <p/>
     * Note that there are only two levels at present, so any
     * value of level higher than two will return an
     * EmoticonCreatorLevel02 object.
     *
     * @param level the next level of the game to create
     * @return EmoticonCreator subclass
     */
    public EmoticonCreator createEmoticonCreator(int level) {
        if (level == LEVEL_ONE) {
            return new EmoticonCreatorLevel01(bitmapCreator, emoWidth, emoHeight);
        } else if (level == LEVEL_TWO) {
            return new EmoticonCreatorLevel02(bitmapCreator, emoWidth, emoHeight);
        } else {
            return new EmoticonCreatorLevel02(bitmapCreator, emoWidth, emoHeight);
        }

    }
}
