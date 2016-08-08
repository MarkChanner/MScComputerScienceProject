package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface EmoticonFactory {

    Emoticon getRandomEmoticon(int x, int y, int offScreenStartPositionY);

    Emoticon createEmptyEmoticon(int x, int y);

}
