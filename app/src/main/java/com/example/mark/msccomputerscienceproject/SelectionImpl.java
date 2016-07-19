package com.example.mark.msccomputerscienceproject;

public class SelectionImpl implements Selection {

    public static final int X = 0;
    public static final int Y = 1;
    private int[] selection01 = new int[2];
    private int[] selection02 = new int[2];
    private boolean selection01Made;

    /**
     * @author Mark Channer for Birkbeck MSc Computer Science project
     */
    public SelectionImpl() {

    }

    @Override
    public void resetUserSelections() {

    }

    @Override
    public boolean selection01Made() {
        return selection01Made;
    }

    @Override
    public int[] getSelection01() {
        return selection01;
    }

    @Override
    public void setSelection01(int x, int y) {
        selection01[X] = x;
        selection01[Y] = y;
        selection01Made = true;
    }

    @Override
    public int[] getSelection02() {
        return selection02;
    }

    @Override
    public void setSelection02(int x, int y) {
        selection02[X] = x;
        selection02[Y] = y;
    }

    @Override
    public boolean sameSelectionMadeTwice() {
        return false;
    }

    @Override
    public boolean adjacentSelections() {
        return false;
    }

    @Override
    public void setSelection02ToSelection01() {

    }
}