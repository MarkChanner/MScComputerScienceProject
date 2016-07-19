package com.example.mark.msccomputerscienceproject;

import android.graphics.Bitmap;

/**
 * Populates the board with numbers, starting with 0 and incrementing by 1. This is so
 * that no matches can occur except for on the tiles that are set with GamePieces at
 * strategic places for testing
 *
 * @author Mark Channer for first prototype of Birkbeck MSc Computer Science final project
 */
public class GamePopulatorMock01 implements GamePopulator {

    public static final int X_MAX = GameControllerImpl.X_MAX;
    public static final int Y_MAX = GameControllerImpl.Y_MAX;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;

    private Bitmap bitmap = BitmapCreator.getInstance().getEmptyBitmap();

    @Override
    public void populateEmoticonsArray(EmoticonFactory emoticonFactory, Emoticon[][] emoticons, int emoWidth, int emoHeight) {
        // Populates the emoticons array with numbers that increment by 1 so that no matches are possible yet
        int counter = 0;
        for (int x = ROW_START; x < X_MAX; x++) {
            for (int y = COLUMN_TOP; y < Y_MAX; y++) {
                if (emoticons[x][y] == null) {
                    emoticons[x][y] = new MockEmoticon(x, y, 20, 20, bitmap, "HAPPY");
                    counter++;
                }
            }
        }

        // Populates emoticons array so that a match of happy Emoticons in a row
        // and a match of sad Emoticons in a column can occur
        // when tiles 3,0 and 4,0 are selected
        emoticons[0][1] = new MockEmoticon(0, 1, 20, 20, bitmap, "HAPPY");
        emoticons[0][2] = new MockEmoticon(0, 2, 20, 20, bitmap, "HAPPY");
        emoticons[0][3] = new MockEmoticon(0, 3, 20, 20, bitmap, "SAD");
        emoticons[0][4] = new MockEmoticon(0, 4, 20, 20, bitmap, "HAPPY");
        emoticons[1][4] = new MockEmoticon(1, 4, 20, 20, bitmap, "SAD");
        emoticons[2][4] = new MockEmoticon(2, 4, 20, 20, bitmap, "SAD");

        // Sets up tiles so that a match of embarrassed emoticons in a row
        // and a match of surprised emoticons in a column can occur
        // when tiles 3,3 and 3,4 are selected
        emoticons[3][3] = new MockEmoticon(3, 3, 20, 20, bitmap, "SURPRISED");
        emoticons[4][3] = new MockEmoticon(4, 3, 20, 20, bitmap, "EMBARRASSED");
        emoticons[5][3] = new MockEmoticon(5, 3, 20, 20, bitmap, "SURPRISED");
        emoticons[6][3] = new MockEmoticon(6, 3, 20, 20, bitmap, "SURPRISED");
        emoticons[4][4] = new MockEmoticon(4, 4, 20, 20, bitmap, "SURPRISED");
        emoticons[4][5] = new MockEmoticon(4, 5, 20, 20, bitmap, "SURPRISED");
    }
}
