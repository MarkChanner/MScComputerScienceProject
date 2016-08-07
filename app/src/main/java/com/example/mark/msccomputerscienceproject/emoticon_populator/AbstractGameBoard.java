package com.example.mark.msccomputerscienceproject.emoticon_populator;

import com.example.mark.msccomputerscienceproject.*;

/**
 * Populates 2d Emoticons array at random.
 * If lacing the emoticon would result in a 2d array that has 3 consecutive emoticons at
 * the start of the game, another emoticon is chosen until one that does not form a match is
 * found { @inheritDocs }
 */
public abstract class AbstractGameBoard {

    public static final int X_MAX = GameControllerImpl.X_MAX;
    public static final int Y_MAX = GameControllerImpl.Y_MAX;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    protected AbstractEmoticonCreator emoCreator;

    public AbstractGameBoard(AbstractEmoticonCreator emoCreator) {
        this.emoCreator = emoCreator;
    }

    /**
     * abstract method to be implemented by subclasses
     *
     * @param emoticons the emoticon array to be populated with Emoticons
     */
    public abstract void populate(Emoticon[][] emoticons);

    public Emoticon getRandomEmoticon(int x, int y, int offScreenStartPositionY) {
        return emoCreator.createRandomEmoticon(x, y, offScreenStartPositionY);
    }

    public Emoticon getEmptyEmoticon(int x, int y) {
        return emoCreator.createEmptyEmoticon(x, y);
    }

    public void setEmoticonFactory(AbstractEmoticonCreator emoCreator) {
        this.emoCreator = emoCreator;
    }
}
