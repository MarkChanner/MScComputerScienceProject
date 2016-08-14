package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public interface Selections {

    void resetUserSelections();

    boolean selection01Made();

    int[] getSelection01();

    void setSelection01(int x, int y);

    int[] getSelection02();

    void setSelection02(int x, int y);

    boolean sameSelectionMadeTwice();

    boolean areNotAdjacent();

    void secondSelectionBecomesFirstSelection();

}

