package com.example.mark.msccomputerscienceproject.emoticon_populator;

import com.example.mark.msccomputerscienceproject.Emoticon;

/**
 * Populates 2d Emoticons array at random.
 * If lacing the emoticon would result in a 2d array that has 3 consecutive emoticons at
 * the start of the game, another emoticon is chosen until one that does not form a match is
 * found { @inheritDocs }
 */
public class GridPopulatorImpl extends AbstractGridPopulator {

    public GridPopulatorImpl(EmoticonCreator emoCreator, Emoticon[][] emoticons) {
        super(emoCreator, emoticons);
    }
}