package com.example.mark.msccomputerscienceproject;

public interface EmoticonCreator {

    Emoticon generateEmoticon(int x, int y, int offScreenStartPositionY);

    Emoticon getEmptyEmoticon(int x, int y);

}
