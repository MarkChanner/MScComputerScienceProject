package com.example.mark.msccomputerscienceproject;

import android.content.Context;

import com.example.mark.msccomputerscienceproject.emoticon_populator.AbstractGridPopulator;

public class MockGridPopulator01 extends AbstractGridPopulator {


    public MockGridPopulator01(Context context, int emoWidth, int emoHeight) {
        super(context, emoWidth, emoHeight);
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
        emoticons[0][1] = emoFactory.createSpecifiedEmoticon(0, 1, "HAPPY");
        emoticons[0][2] = emoFactory.createSpecifiedEmoticon(0, 2, "HAPPY");
        emoticons[0][3] = emoFactory.createSpecifiedEmoticon(0, 3, "SURPRISED");
        emoticons[0][4] = emoFactory.createSpecifiedEmoticon(0, 4, "HAPPY");
        emoticons[1][4] = emoFactory.createSpecifiedEmoticon(1, 4, "SURPRISED");
        emoticons[2][4] = emoFactory.createSpecifiedEmoticon(2, 4, "SURPRISED");

        // Populates board so that a horizontal match of Angry
        // emoticons and a vertical match of Sad emoticons
        // can occur when Emoticons at (3,3) and (4,3) are selected
        emoticons[3][3] = emoFactory.createSpecifiedEmoticon(3, 3, "SAD");
        emoticons[4][3] = emoFactory.createSpecifiedEmoticon(4, 3, "ANGRY");
        emoticons[5][3] = emoFactory.createSpecifiedEmoticon(5, 3, "SAD");
        emoticons[6][3] = emoFactory.createSpecifiedEmoticon(6, 3, "SAD");
        emoticons[4][4] = emoFactory.createSpecifiedEmoticon(4, 4, "SAD");
        emoticons[4][5] = emoFactory.createSpecifiedEmoticon(4, 5, "SAD");
    }

    public Emoticon getMockEmoticon(int x, int y) {
        return emoFactory.createMockEmoticon(x, y);
    }
}
