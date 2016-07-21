package com.example.mark.msccomputerscienceproject;

/**
 * Populates 2d Emoticons array at random.
 * If lacing the emoticon would result in a 2d array that has 3 consecutive emoticons at
 * the start of the game, another emoticon is chosen until one that does not form a match is
 * found { @inheritDocs }
 */
public class GamePopulatorImpl implements GamePopulator {

    public static final int X_MAX = GameControllerImpl.X_MAX;
    public static final int Y_MAX = GameControllerImpl.Y_MAX;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;

    public void populateBoard(EmoticonCreator emoCreator, Emoticon[][] emoticons) {
        Emoticon newEmoticon;
        for (int x = ROW_START; x < X_MAX; x++) {
            int dropGap = Y_MAX * 2;
            for (int y = COLUMN_TOP; y < Y_MAX; y++) {
                do {
                    newEmoticon = emoCreator.generateEmoticon(x, y, ((y - Y_MAX) - dropGap));

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