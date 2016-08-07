package com.example.mark.msccomputerscienceproject.emoticon_populator;

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
     *
     * Note that there are only two levels at present, so any
     * value of level higher than two will return an
     * EmoticonCreatorLevel02 object.
     *
     * @param level the next level of the game to create
     * @return AbstractEmoticonCreator subclass
     */
    public AbstractEmoticonCreator getEmoticonCreator(int level) {
        AbstractEmoticonCreator emoticonFactory;
        switch (level) {
            case LEVEL_ONE:
                emoticonFactory = new EmoticonCreatorLevel01(bitmapCreator, emoWidth, emoHeight);
                break;
            case LEVEL_TWO:
                emoticonFactory = new EmoticonCreatorLevel02(bitmapCreator, emoWidth, emoHeight);
            break;
            default:
                emoticonFactory = new EmoticonCreatorLevel02(bitmapCreator, emoWidth, emoHeight);
                break;
        }
        return emoticonFactory;
    }
}
