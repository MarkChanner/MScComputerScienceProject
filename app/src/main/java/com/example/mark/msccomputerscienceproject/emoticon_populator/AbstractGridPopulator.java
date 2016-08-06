package com.example.mark.msccomputerscienceproject.emoticon_populator;

import android.content.Context;

import com.example.mark.msccomputerscienceproject.*;

/**
 * Populates 2d Emoticons array at random.
 * If lacing the emoticon would result in a 2d array that has 3 consecutive emoticons at
 * the start of the game, another emoticon is chosen until one that does not form a match is
 * found { @inheritDocs }
 */
public abstract class AbstractGridPopulator {

    public static final int X_MAX = GameControllerImpl.X_MAX;
    public static final int Y_MAX = GameControllerImpl.Y_MAX;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;
    protected AbstractEmoticonFactory emoFactory;
    private Context context;
    private int emoWidth;
    private int emoHeight;

    public AbstractGridPopulator(Context context, int emoWidth, int emoHeight) {
        this.context = context;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
        this.emoFactory = new EmoticonFactoryLevel01(context, emoWidth, emoHeight);
    }

    public abstract void populateBoard(Emoticon[][] emoticons);

    public void setEmoticonFactory(int level) {
        switch (level) {
            case LEVEL_ONE:
                emoFactory = new EmoticonFactoryLevel01(context, emoWidth, emoHeight);
                break;
            case LEVEL_TWO:
                emoFactory = new EmoticonFactoryLevel02(context, emoWidth, emoHeight);
                break;
            default:
                break;
        }
    }

    public Emoticon getRandomEmoticon(int x, int y, int offScreenStartPositionY) {
        return emoFactory.createRandomEmoticon(x, y, offScreenStartPositionY);
    }

    public Emoticon getEmptyEmoticon(int x, int y) {
        return emoFactory.createEmptyEmoticon(x, y);
    }
}
