package com.example.mark.msccomputerscienceproject;

import com.example.mark.msccomputerscienceproject.emoticon_populator.AbstractGridPopulator;
import com.example.mark.msccomputerscienceproject.emoticon_populator.EmoticonCreator;

public class MockGridPopulator01 extends AbstractGridPopulator {

    public static final int X_MAX = GameControllerImpl.X_MAX;
    public static final int Y_MAX = GameControllerImpl.Y_MAX;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    private EmoticonCreator emoCreator;

    public MockGridPopulator01(EmoticonCreator emoCreator) {
        super(emoCreator);
        this.emoCreator = emoCreator;
    }

    @Override
    public void populateBoard(Emoticon[][] emoticons) {
        for (int x = ROW_START; x < X_MAX; x++) {
            for (int y = COLUMN_TOP; y < Y_MAX; y++) {
                emoticons[x][y] = getMockEmoticon(x, y);
            }
        }
        generateEmoticonAtSetLocation(emoticons);
    }

    private void generateEmoticonAtSetLocation(Emoticon[][] emoticons) {
        // Populates board so that a horizontal match of Happy
        // emoticons and a vertical match of Surprised emoticons
        // can occur when Emoticons at (0,3) and (0,4) are selected
        emoticons[0][1] = emoCreator.generateSpecifiedEmoticon(0, 1, "HAPPY");
        emoticons[0][2] = emoCreator.generateSpecifiedEmoticon(0, 2, "HAPPY");
        emoticons[0][3] = emoCreator.generateSpecifiedEmoticon(0, 3, "SURPRISED");
        emoticons[0][4] = emoCreator.generateSpecifiedEmoticon(0, 4, "HAPPY");
        emoticons[1][4] = emoCreator.generateSpecifiedEmoticon(1, 4, "SURPRISED");
        emoticons[2][4] = emoCreator.generateSpecifiedEmoticon(2, 4, "SURPRISED");

        // Populates board so that a horizontal match of Angry
        // emoticons and a vertical match of Sad emoticons
        // can occur when Emoticons at (3,3) and (4,3) are selected
        emoticons[3][3] = emoCreator.generateSpecifiedEmoticon(3, 3, "SAD");
        emoticons[4][3] = emoCreator.generateSpecifiedEmoticon(4, 3, "ANGRY");
        emoticons[5][3] = emoCreator.generateSpecifiedEmoticon(5, 3, "SAD");
        emoticons[6][3] = emoCreator.generateSpecifiedEmoticon(6, 3, "SAD");
        emoticons[4][4] = emoCreator.generateSpecifiedEmoticon(4, 4, "SAD");
        emoticons[4][5] = emoCreator.generateSpecifiedEmoticon(4, 5, "SAD");
    }

    public Emoticon getMockEmoticon(int x, int y) {
        return emoCreator.generateMockEmoticon(x, y);
    }
}
