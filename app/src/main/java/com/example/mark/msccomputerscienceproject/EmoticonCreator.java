package com.example.mark.msccomputerscienceproject;

public interface EmoticonCreator {

    /**
     * Creates 1 of 5 randomly selected emoticons
     *
     * @param x                       the location of the x value in the array
     * @param y                       the location of the y value in the array
     * @param offScreenStartPositionY the off-screen location
     *                                from which the emoticon
     *                                is to drop
     * @return a random Emoticon
     */
    Emoticon generateRandomEmoticon(int x, int y, int offScreenStartPositionY);

    /**
     * Creates the required emoticon
     *
     * @param x       the location of the x value in the array
     * @param y       the location of the y value in the array
     * @param emoType the type of emoticon to be created
     * @return the required Emoticon
     */
    Emoticon generateSpecifiedEmoticon(int x, int y, String emoType);

    /**
     * @param x the location of the x value in the array
     * @param y the location of the y value in the array
     * @return a MockEmoticon for testing
     */
    Emoticon generateMockEmoticon(int x, int y);

    Emoticon getEmptyEmoticon(int x, int y);

}