package com.example.mark.msccomputerscienceproject;

import com.example.mark.msccomputerscienceproject.emoticon_populator.AbstractEmoticonCreator;
import com.example.mark.msccomputerscienceproject.emoticon_populator.AbstractGameBoard;

public class MockGridPopulator01 extends AbstractGameBoard{


    public MockGridPopulator01(AbstractEmoticonCreator emoCreator) {
        super(emoCreator);
    }

    @Override
    public void populate(Emoticon[][] emoticons) {
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
        emoticons[0][1] = emoCreator.createSpecifiedEmoticon(0, 1, "HAPPY");
        emoticons[0][2] = emoCreator.createSpecifiedEmoticon(0, 2, "HAPPY");
        emoticons[0][3] = emoCreator.createSpecifiedEmoticon(0, 3, "SURPRISED");
        emoticons[0][4] = emoCreator.createSpecifiedEmoticon(0, 4, "HAPPY");
        emoticons[1][4] = emoCreator.createSpecifiedEmoticon(1, 4, "SURPRISED");
        emoticons[2][4] = emoCreator.createSpecifiedEmoticon(2, 4, "SURPRISED");

        // Populates board so that a horizontal match of Angry
        // emoticons and a vertical match of Sad emoticons
        // can occur when Emoticons at (3,3) and (4,3) are selected
        emoticons[3][3] = emoCreator.createSpecifiedEmoticon(3, 3, "SAD");
        emoticons[4][3] = emoCreator.createSpecifiedEmoticon(4, 3, "ANGRY");
        emoticons[5][3] = emoCreator.createSpecifiedEmoticon(5, 3, "SAD");
        emoticons[6][3] = emoCreator.createSpecifiedEmoticon(6, 3, "SAD");
        emoticons[4][4] = emoCreator.createSpecifiedEmoticon(4, 4, "SAD");
        emoticons[4][5] = emoCreator.createSpecifiedEmoticon(4, 5, "SAD");
    }

    public Emoticon getMockEmoticon(int x, int y) {
        return emoCreator.createMockEmoticon(x, y);
    }
}
