package com.example.mark.msccomputerscienceproject.emoticon_populator;

import com.example.mark.msccomputerscienceproject.Emoticon;

public interface GridPopulator {

    void populateBoard(Emoticon[][] emoticons);

    Emoticon getRandomEmoticon(int x, int y, int offScreenStartPositionY);

    Emoticon getEmptyEmoticon(int x, int y);

}
