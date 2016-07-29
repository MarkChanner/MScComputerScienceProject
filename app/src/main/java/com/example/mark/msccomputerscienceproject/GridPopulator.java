package com.example.mark.msccomputerscienceproject;

public interface GridPopulator {

    void populateBoard(Emoticon[][] emoticons);

    Emoticon getRandomEmoticon(int x, int y, int offScreenStartPositionY);

    Emoticon getEmptyEmoticon(int x, int y);

}
