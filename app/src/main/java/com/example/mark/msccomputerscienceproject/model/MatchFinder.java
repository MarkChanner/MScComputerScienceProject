package com.example.mark.msccomputerscienceproject.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class MatchFinder {

    private static final String TAG = "MatchFinder";
    public static final int X_MAX = GameModelImpl.X_MAX;
    public static final int Y_MAX = GameModelImpl.Y_MAX;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    public static final int COLUMN_BOTTOM = (Y_MAX - 1);
    public static final String EMPTY = "EMPTY";

    public ArrayList<LinkedList<Emoticon>> findVerticalMatches(GameBoard gameBoard) {
        LinkedList<Emoticon> consecutiveEmoticons = new LinkedList<>();
        ArrayList<LinkedList<Emoticon>> bigList = new ArrayList<>();
        Emoticon emoticon;
        for (int x = ROW_START; x < X_MAX; x++) {
            consecutiveEmoticons.add(gameBoard.getGamePiece(x, COLUMN_BOTTOM));


            for (int y = (COLUMN_BOTTOM - 1); y >= COLUMN_TOP; y--) {
                emoticon = gameBoard.getGamePiece(x, y);
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

    public ArrayList<LinkedList<Emoticon>> findHorizontalMatches(GameBoard gameBoard) {
        LinkedList<Emoticon> consecutiveEmoticons = new LinkedList<>();
        ArrayList<LinkedList<Emoticon>> bigList = new ArrayList<>();
        Emoticon emoticon;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            consecutiveEmoticons.add(gameBoard.getGamePiece(ROW_START, y));

            for (int x = (ROW_START + 1); x < X_MAX; x++) {
                emoticon = gameBoard.getGamePiece(x, y);
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

    public boolean anotherMatchPossible(GameBoard gameBoard) {
        //Log.d(TAG, "anotherMatchPossible(Emoticon[][])");
        return (verticalMatchPossible(gameBoard) || horizontalMatchPossible(gameBoard));
    }

    private boolean verticalMatchPossible(GameBoard gameBoard) {
        String type;
        for (int x = ROW_START; x < X_MAX; x++) {
            for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {

                type = gameBoard.getGamePiece(x, y).getEmoticonType();

                if ((y - 1 >= COLUMN_TOP &&
                        gameBoard.getGamePiece(x, y - 1).getEmoticonType().equals(type) &&
                        verticalA(gameBoard, type, x, y)) ||
                        (y - 2 >= COLUMN_TOP &&
                                gameBoard.getGamePiece(x, y - 2).getEmoticonType().equals(type) &&
                                verticalB(gameBoard, type, x, y))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verticalA(GameBoard gameBoard, String type, int x, int y) {
        return ((y - 2 >= COLUMN_TOP && verticalAboveA(gameBoard, type, x, y)) ||
                (y + 1 <= COLUMN_BOTTOM && verticalBelowA(gameBoard, type, x, y)));
    }

    /**
     * The condition that (y - 2) must be higher than
     * COLUMN_TOP was  checked in the calling method
     */
    private boolean verticalAboveA(GameBoard gameBoard, String type, int x, int y) {
        return ((y - 3 >= COLUMN_TOP && gameBoard.getGamePiece(x, y - 3).getEmoticonType().equals(type)) ||
                (x - 1 >= ROW_START && gameBoard.getGamePiece(x - 1, y - 2).getEmoticonType().equals(type)) ||
                (x + 1 < X_MAX && gameBoard.getGamePiece(x + 1, y - 2).getEmoticonType().equals(type)));
    }

    /**
     * The condition that (y + 1) must be less than
     * COLUMN_BOTTOM was checked in the calling method
     */
    private boolean verticalBelowA(GameBoard gameBoard, String type, int x, int y) {
        return ((y + 2 <= COLUMN_BOTTOM && gameBoard.getGamePiece(x, y + 2).getEmoticonType().equals(type)) ||
                (x - 1 >= ROW_START && gameBoard.getGamePiece(x - 1, y + 1).getEmoticonType().equals(type)) ||
                (x + 1 < X_MAX && gameBoard.getGamePiece(x + 1, y + 1).getEmoticonType().equals(type)));
    }

    private boolean verticalB(GameBoard gameBoard, String type, int x, int y) {
        return ((x - 1 >= ROW_START && gameBoard.getGamePiece(x - 1, y - 1).getEmoticonType().equals(type)) ||
                (x + 1 < X_MAX && gameBoard.getGamePiece(x + 1, y - 1).getEmoticonType().equals(type)));
    }

    private boolean horizontalMatchPossible(GameBoard gameBoard) {
        String type;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {

                type = gameBoard.getGamePiece(x, y).getEmoticonType();

                if ((x + 1 < X_MAX &&
                        gameBoard.getGamePiece(x + 1, y).getEmoticonType().equals(type) &&
                        horizontalA(gameBoard, type, x, y)) ||
                        (x + 2 < X_MAX && gameBoard.getGamePiece(x + 2, y).getEmoticonType().equals(type) &&
                                horizontalB(gameBoard, type, x, y))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean horizontalA(GameBoard gameBoard, String type, int x, int y) {
        return ((x + 2 < X_MAX && horizontalRightA(gameBoard, type, x, y)) ||
                (x - 1 >= ROW_START && horizontalLeftA(gameBoard, type, x, y)));
    }

    /**
     * The condition that (x + 2) must be above
     * below X_MAX was checked in the calling method
     */
    private boolean horizontalRightA(GameBoard gameBoard, String type, int x, int y) {
        return ((x + 3 < X_MAX && gameBoard.getGamePiece(x + 3, y).getEmoticonType().equals(type)) ||
                (y - 1 >= COLUMN_TOP && gameBoard.getGamePiece(x + 2, y - 1).getEmoticonType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && gameBoard.getGamePiece(x + 2, y + 1).getEmoticonType().equals(type)));
    }

    /**
     * The condition that (x - 1) must be above equal to or
     * above  ROW_START was checked in the calling method
     */
    private boolean horizontalLeftA(GameBoard gameBoard, String type, int x, int y) {
        return ((x - 2 >= ROW_START && gameBoard.getGamePiece(x - 2, y).getEmoticonType().equals(type)) ||
                (y - 1 >= COLUMN_TOP && gameBoard.getGamePiece(x - 1, y - 1).getEmoticonType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && gameBoard.getGamePiece(x - 1, y + 1).getEmoticonType().equals(type)));
    }

    private boolean horizontalB(GameBoard gameBoard, String type, int x, int y) {
        return ((y - 1 >= COLUMN_TOP && gameBoard.getGamePiece(x + 1, y - 1).getEmoticonType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && gameBoard.getGamePiece(x + 1, y + 1).getEmoticonType().equals(type)));
    }
}
