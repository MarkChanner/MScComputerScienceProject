package com.example.mark.msccomputerscienceproject;

public interface EmoticonCreator {

    Emoticon generateRandomEmoticon(int x, int y, int offScreenStartPositionY);

    Emoticon generateSpecifiedEmoticon(int x, int y, String emoType);

    Emoticon generateMockEmoticon(int x, int y);

    Emoticon getEmptyEmoticon(int x, int y);
}
