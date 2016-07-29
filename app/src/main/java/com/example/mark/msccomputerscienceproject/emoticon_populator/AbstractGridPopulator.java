package com.example.mark.msccomputerscienceproject.emoticon_populator;

import com.example.mark.msccomputerscienceproject.Emoticon;
import com.example.mark.msccomputerscienceproject.GameControllerImpl;

/**
 * Populates 2d Emoticons array at random.
 * If lacing the emoticon would result in a 2d array that has 3 consecutive emoticons at
 * the start of the game, another emoticon is chosen until one that does not form a match is
 * found { @inheritDocs }
 */
public class AbstractGridPopulator implements GridPopulator {

    public static final int X_MAX = GameControllerImpl.X_MAX;
    public static final int Y_MAX = GameControllerImpl.Y_MAX;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    private EmoticonCreator emoCreator;

    public AbstractGridPopulator(EmoticonCreator emoCreator, Emoticon[][] emoticons) {
        this.emoCreator = emoCreator;
        populateBoard(emoticons);
    }

    @Override
    public void populateBoard(Emoticon[][] emoticons) {
        Emoticon newEmoticon;
        for (int x = ROW_START; x < X_MAX; x++) {
            int dropGap = Y_MAX * 2;
            for (int y = COLUMN_TOP; y < Y_MAX; y++) {
                do {
                    newEmoticon = getRandomEmoticon(x, y, ((y - Y_MAX) - dropGap));

                } while ((y >= 2 &&
                        (newEmoticon.getEmoticonType().equals(emoticons[x][y - 1].getEmoticonType()) &&
                                newEmoticon.getEmoticonType().equals(emoticons[x][y - 2].getEmoticonType()))) ||
                        (x >= 2 &&
                                (newEmoticon.getEmoticonType().equals(emoticons[x - 1][y].getEmoticonType()) &&
                                        newEmoticon.getEmoticonType().equals(emoticons[x - 2][y].getEmoticonType()))));

                dropGap--;
                emoticons[x][y] = newEmoticon;
            }
        }
    }

    @Override
    public Emoticon getRandomEmoticon(int x, int y, int offScreenStartPositionY) {
        return emoCreator.generateRandomEmoticon(x, y, offScreenStartPositionY);
    }

    @Override
    public Emoticon getEmptyEmoticon(int x, int y) {
        return emoCreator.getEmptyEmoticon(x, y);
    }

}
