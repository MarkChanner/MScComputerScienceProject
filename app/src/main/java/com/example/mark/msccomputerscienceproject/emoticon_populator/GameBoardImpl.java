package com.example.mark.msccomputerscienceproject.emoticon_populator;

import com.example.mark.msccomputerscienceproject.Emoticon;

/**
 * Populates 2d Emoticons array at random.
 * If lacing the emoticon would result in a 2d array that has 3 consecutive emoticons at
 * the start of the game, another emoticon is chosen until one that does not form a match is
 * found { @inheritDocs }
 */
public class GridPopulatorImpl extends AbstractGridPopulator {

    public GridPopulatorImpl(AbstractEmoticonFactory emoticonFactory) {
        super(emoticonFactory);
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
}