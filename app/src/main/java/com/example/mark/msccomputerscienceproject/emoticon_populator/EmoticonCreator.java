package com.example.mark.msccomputerscienceproject.emoticon_populator;

import com.example.mark.msccomputerscienceproject.Emoticon;

public interface EmoticonCreator {

    Emoticon createRandomEmoticon(int x, int y, int offScreenStartPositionY);

    Emoticon createSpecifiedEmoticon(int x, int y, String emoType);

    Emoticon createMockEmoticon(int x, int y);

    Emoticon createEmptyEmoticon(int x, int y);
}
