package com.example.mark.msccomputerscienceproject.emoticon_populator;

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
    protected AbstractEmoticonFactory emoFactory;

    public AbstractGridPopulator(AbstractEmoticonFactory emoFactory) {
        this.emoFactory = emoFactory;
    }

    public abstract void populateBoard(Emoticon[][] emoticons);

    public void setEmoticonFactory(AbstractEmoticonFactory emoFactory) {
        this.emoFactory = emoFactory;
    }

    public Emoticon getRandomEmoticon(int x, int y, int offScreenStartPositionY) {
        return emoFactory.createRandomEmoticon(x, y, offScreenStartPositionY);
    }

    public Emoticon getEmptyEmoticon(int x, int y) {
        return emoFactory.createEmptyEmoticon(x, y);
    }
}
