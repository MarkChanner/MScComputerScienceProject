package com.example.mark.msccomputerscienceproject.model;

import java.util.ArrayList;
import java.util.LinkedList;

public class MatchFinder {

    private static final String TAG = "MatchFinder";
    public static final int X_MAX = GameModelImpl.X_MAX;
    public static final int Y_MAX = GameModelImpl.Y_MAX;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    public static final int COLUMN_BOTTOM = (Y_MAX - 1);
    public static final String EMPTY = "EMPTY";

    public ArrayList<LinkedList<Emoticon>> findVerticalMatches(GameBoardImpl gamePieces) {
        LinkedList<Emoticon> consecutiveEmoticons = new LinkedList<>();
        ArrayList<LinkedList<Emoticon>> bigList = new ArrayList<>();
        Emoticon emoticon;
        for (int x = ROW_START; x < X_MAX; x++) {
            consecutiveEmoticons.add(gamePieces.getGamePiece(x, COLUMN_BOTTOM));


            for (int y = (COLUMN_BOTTOM - 1); y >= COLUMN_TOP; y--) {
                emoticon = gamePieces.getGamePiece(x, y);
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

    public ArrayList<LinkedList<Emoticon>> findHorizontalMatches(GameBoardImpl gamePieces) {
        LinkedList<Emoticon> consecutiveEmoticons = new LinkedList<>();
        ArrayList<LinkedList<Emoticon>> bigList = new ArrayList<>();
        Emoticon emoticon;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            consecutiveEmoticons.add(gamePieces.getGamePiece(ROW_START, y));

            for (int x = (ROW_START + 1); x < X_MAX; x++) {
                emoticon = gamePieces.getGamePiece(x, y);
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

    public boolean anotherMatchPossible(GameBoardImpl gamePieces) {
        //Log.d(TAG, "anotherMatchPossible(Emoticon[][])");
        return (verticalMatchPossible(gamePieces) || horizontalMatchPossible(gamePieces));
    }

    private boolean verticalMatchPossible(GameBoardImpl gamePieces) {
        String type;
        for (int x = ROW_START; x < X_MAX; x++) {
            for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {

                type = gamePieces.getGamePiece(x, y).getEmoticonType();

                if ((y - 1 >= COLUMN_TOP &&
                        gamePieces.getGamePiece(x, y - 1).getEmoticonType().equals(type) &&
                        verticalA(gamePieces, type, x, y)) ||
                        (y - 2 >= COLUMN_TOP &&
                                gamePieces.getGamePiece(x, y - 2).getEmoticonType().equals(type) &&
                                verticalB(gamePieces, type, x, y))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verticalA(GameBoardImpl gamePieces, String type, int x, int y) {
        return ((y - 2 >= COLUMN_TOP && verticalAboveA(gamePieces, type, x, y)) ||
                (y + 1 <= COLUMN_BOTTOM && verticalBelowA(gamePieces, type, x, y)));
    }

    /**
     * The condition that (y - 2) must be higher than
     * COLUMN_TOP was  checked in the calling method
     */
    private boolean verticalAboveA(GameBoardImpl gamePieces, String type, int x, int y) {
        return ((y - 3 >= COLUMN_TOP && gamePieces.getGamePiece(x, y - 3).getEmoticonType().equals(type)) ||
                (x - 1 >= ROW_START && gamePieces.getGamePiece(x - 1, y - 2).getEmoticonType().equals(type)) ||
                (x + 1 < X_MAX && gamePieces.getGamePiece(x + 1, y - 2).getEmoticonType().equals(type)));
    }

    /**
     * The condition that (y + 1) must be less than
     * COLUMN_BOTTOM was checked in the calling method
     */
    private boolean verticalBelowA(GameBoardImpl gamePieces, String type, int x, int y) {
        return ((y + 2 <= COLUMN_BOTTOM && gamePieces.getGamePiece(x, y + 2).getEmoticonType().equals(type)) ||
                (x - 1 >= ROW_START && gamePieces.getGamePiece(x - 1, y + 1).getEmoticonType().equals(type)) ||
                (x + 1 < X_MAX && gamePieces.getGamePiece(x + 1, y + 1).getEmoticonType().equals(type)));
    }

    private boolean verticalB(GameBoardImpl gamePieces, String type, int x, int y) {
        return ((x - 1 >= ROW_START && gamePieces.getGamePiece(x - 1, y - 1).getEmoticonType().equals(type)) ||
                (x + 1 < X_MAX && gamePieces.getGamePiece(x + 1, y - 1).getEmoticonType().equals(type)));
    }

    private boolean horizontalMatchPossible(GameBoardImpl gamePieces) {
        String type;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {

                type = gamePieces.getGamePiece(x, y).getEmoticonType();

                if ((x + 1 < X_MAX &&
                        gamePieces.getGamePiece(x + 1, y).getEmoticonType().equals(type) &&
                        horizontalA(gamePieces, type, x, y)) ||
                        (x + 2 < X_MAX && gamePieces.getGamePiece(x + 2, y).getEmoticonType().equals(type) &&
                                horizontalB(gamePieces, type, x, y))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean horizontalA(GameBoardImpl gamePieces, String type, int x, int y) {
        return ((x + 2 < X_MAX && horizontalRightA(gamePieces, type, x, y)) ||
                (x - 1 >= ROW_START && horizontalLeftA(gamePieces, type, x, y)));
    }

    /**
     * The condition that (x + 2) must be above
     * below X_MAX was checked in the calling method
     */
    private boolean horizontalRightA(GameBoardImpl gamePieces, String type, int x, int y) {
        return ((x + 3 < X_MAX && gamePieces.getGamePiece(x + 3, y).getEmoticonType().equals(type)) ||
                (y - 1 >= COLUMN_TOP && gamePieces.getGamePiece(x + 2, y - 1).getEmoticonType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && gamePieces.getGamePiece(x + 2, y + 1).getEmoticonType().equals(type)));
    }

    /**
     * The condition that (x - 1) must be above equal to or
     * above  ROW_START was checked in the calling method
     */
    private boolean horizontalLeftA(GameBoardImpl gamePieces, String type, int x, int y) {
        return ((x - 2 >= ROW_START && gamePieces.getGamePiece(x - 2, y).getEmoticonType().equals(type)) ||
                (y - 1 >= COLUMN_TOP && gamePieces.getGamePiece(x - 1, y - 1).getEmoticonType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && gamePieces.getGamePiece(x - 1, y + 1).getEmoticonType().equals(type)));
    }

    private boolean horizontalB(GameBoardImpl gamePieces, String type, int x, int y) {
        return ((y - 1 >= COLUMN_TOP && gamePieces.getGamePiece(x + 1, y - 1).getEmoticonType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && gamePieces.getGamePiece(x + 1, y + 1).getEmoticonType().equals(type)));
    }
}
