package com.example.mark.msccomputerscienceproject;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;

public class EmoticonMatchFinder {

    private Emoticon[][] emoticons;

    public EmoticonMatchFinder(Emoticon[][] emoticons) {
        this.emoticons = emoticons;
    }

    private static final String TAG = "EmoticonMatchFinder";
    public static final int X_MAX = GameControllerImpl.X_MAX;
    public static final int Y_MAX = GameControllerImpl.Y_MAX;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    public static final int COLUMN_BOTTOM = (Y_MAX - 1);
    public static final String EMPTY = "EMPTY";

    public ArrayList<LinkedList<Emoticon>> findVerticalMatches() {
        LinkedList<Emoticon> consecutiveEmoticons = new LinkedList<>();
        ArrayList<LinkedList<Emoticon>> bigList = new ArrayList<>();
        Emoticon emoticon;
        for (int x = ROW_START; x < X_MAX; x++) {
            consecutiveEmoticons.add(emoticons[x][COLUMN_BOTTOM]);

            for (int y = (COLUMN_BOTTOM - 1); y >= COLUMN_TOP; y--) {
                emoticon = emoticons[x][y];
                if (!emoticon.getEmoticonType().equals(consecutiveEmoticons.getLast().getEmoticonType())) {
                    examineList(consecutiveEmoticons, bigList);
                    consecutiveEmoticons = new LinkedList<>();
                }
                consecutiveEmoticons.add(emoticon);
                if (y == COLUMN_TOP) {
                    examineList(consecutiveEmoticons, bigList);
                    consecutiveEmoticons = new LinkedList<>();
                }
            }
        }
        return bigList;
    }

    public ArrayList<LinkedList<Emoticon>> findHorizontalMatches() {
        LinkedList<Emoticon> consecutiveEmoticons = new LinkedList<>();
        ArrayList<LinkedList<Emoticon>> bigList = new ArrayList<>();
        Emoticon emoticon;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            consecutiveEmoticons.add(emoticons[ROW_START][y]);

            for (int x = (ROW_START + 1); x < X_MAX; x++) {
                emoticon = emoticons[x][y];
                if (!(emoticon.getEmoticonType().equals(consecutiveEmoticons.getLast().getEmoticonType()))) {
                    examineList(consecutiveEmoticons, bigList);
                    consecutiveEmoticons = new LinkedList<>();
                }
                consecutiveEmoticons.add(emoticon);
                if (x == (X_MAX - 1)) {
                    examineList(consecutiveEmoticons, bigList);
                    consecutiveEmoticons = new LinkedList<>();
                }
            }
        }
        return bigList;
    }

    private void examineList(LinkedList<Emoticon> consecutiveEmotions, ArrayList<LinkedList<Emoticon>> bigList) {
        if ((consecutiveEmotions.size() >= 3) && (allSameType(consecutiveEmotions))) {
            bigList.add(consecutiveEmotions);
        }
    }

    private boolean allSameType(LinkedList<Emoticon> consecutiveEmoticons) {
        String previousEmoticon = consecutiveEmoticons.getFirst().getEmoticonType();
        String nextEmoticon;
        for (int i = 1; i < consecutiveEmoticons.size(); i++) {
            nextEmoticon = consecutiveEmoticons.get(i).getEmoticonType();
            if (nextEmoticon.equals(EMPTY) || (!(nextEmoticon.equals(previousEmoticon)))) {
                return false;
            } else {
                previousEmoticon = nextEmoticon;
            }
        }
        return true;
    }

    public boolean anotherMatchPossible() {
        // Log.d(TAG, "in anotherMatchPossible()");
        return (verticalMatchPossible() || horizontalMatchPossible());
    }

    private boolean verticalMatchPossible() {
        //Log.d(TAG, "in verticalMatchPossible()");
        String type;
        for (int x = ROW_START; x < X_MAX; x++) {
            for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {

                type = emoticons[x][y].getEmoticonType();

                if ((y - 1 >= COLUMN_TOP &&
                        emoticons[x][y - 1].getEmoticonType().equals(type) &&
                        verticalA(type, x, y)) ||
                        (y - 2 >= COLUMN_TOP &&
                                emoticons[x][y - 2].getEmoticonType().equals(type) &&
                                verticalB(type, x, y))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verticalA(String type, int x, int y) {
       // Log.d(TAG, "in verticalA");
        return ((y - 2 >= COLUMN_TOP && verticalAboveA(type, x, y)) ||
                (y + 1 <= COLUMN_BOTTOM && verticalBelowA(type, x, y)));
    }

    /**
     * The condition that (y - 2) must be higher than
     * COLUMN_TOP was  checked in the calling method
     */
    private boolean verticalAboveA(String type, int x, int y) {
        //Log.d(TAG, "in verticalAboveA");
        return ((y - 3 >= COLUMN_TOP && emoticons[x][y - 3].getEmoticonType().equals(type)) ||
                (x - 1 >= ROW_START && emoticons[x - 1][y - 2].getEmoticonType().equals(type)) ||
                (x + 1 < X_MAX && emoticons[x + 1][y - 2].getEmoticonType().equals(type)));
    }

    /**
     * The condition that (y + 1) must be less than
     * COLUMN_BOTTOM was checked in the calling method
     */
    private boolean verticalBelowA(String type, int x, int y) {
       // Log.d(TAG, "in verticalBelowA");
        return ((y + 2 <= COLUMN_BOTTOM && emoticons[x][y + 2].getEmoticonType().equals(type)) ||
                (x - 1 >= ROW_START && emoticons[x - 1][y + 1].getEmoticonType().equals(type)) ||
                (x + 1 < X_MAX && emoticons[x + 1][y + 1].getEmoticonType().equals(type)));
    }

    private boolean verticalB(String type, int x, int y) {
       // Log.d(TAG, "in verticalB");
        return ((x - 1 >= ROW_START && emoticons[x - 1][y - 1].getEmoticonType().equals(type)) ||
                (x + 1 < X_MAX && emoticons[x + 1][y - 1].getEmoticonType().equals(type)));
    }

    private boolean horizontalMatchPossible() {
       // Log.d(TAG, "in horizontalMatchPossible()");
        String type;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {

                type = emoticons[x][y].getEmoticonType();

                if ((x + 1 < X_MAX &&
                        emoticons[x + 1][y].getEmoticonType().equals(type) &&
                        horizontalA(type, x, y)) ||
                        (x + 2 < X_MAX && emoticons[x + 2][y].getEmoticonType().equals(type) &&
                                horizontalB(type, x, y))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean horizontalA(String type, int x, int y) {
       // Log.d(TAG, "in horizontalA()");
        return ((x + 2 < X_MAX && horizontalRightA(type, x, y)) ||
                (x - 1 >= ROW_START && horizontalLeftA(type, x, y)));
    }

    /**
     * The condition that (x + 2) must be above
     * below X_MAX was checked in the calling method
     */
    private boolean horizontalRightA(String type, int x, int y) {
        return ((x + 3 < X_MAX && emoticons[x + 3][y].getEmoticonType().equals(type)) ||
                (y - 1 >= COLUMN_TOP && emoticons[x + 2][y - 1].getEmoticonType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && emoticons[x + 2][y + 1].getEmoticonType().equals(type)));
    }

    /**
     * The condition that (x - 1) must be above equal to or
     * above  ROW_START was checked in the calling method
     */
    private boolean horizontalLeftA(String type, int x, int y) {
        return ((x - 2 >= ROW_START && emoticons[x - 2][y].getEmoticonType().equals(type)) ||
                (y - 1 >= COLUMN_TOP && emoticons[x - 1][y - 1].getEmoticonType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && emoticons[x - 1][y + 1].getEmoticonType().equals(type)));
    }

    private boolean horizontalB(String type, int x, int y) {
        return ((y - 1 >= COLUMN_TOP && emoticons[x + 1][y - 1].getEmoticonType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && emoticons[x + 1][y + 1].getEmoticonType().equals(type)));
    }
}
