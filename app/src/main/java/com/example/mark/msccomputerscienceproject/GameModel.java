package com.example.mark.msccomputerscienceproject;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface GameModel {

    void updateEmoticonSwapCoordinates();

    void updateEmoticonDropCoordinates();

    void handleSelection(int x, int y);

    void processSelections(int[] sel1, int[] sel2);

    Emoticon[][] getEmoticons();

    void resetBoard();

}