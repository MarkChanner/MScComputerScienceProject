package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface EmoticonFactory {

    Emoticon getRandomEmo(int x, int y, int offScreenStartPositionY);

    Emoticon createEmptyEmo(int x, int y);

}
