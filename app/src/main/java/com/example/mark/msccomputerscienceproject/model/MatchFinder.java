package com.example.mark.msccomputerscienceproject.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class MatchFinder {

    private static final String TAG = "MatchFinder";
    public static final int X_MAX = GameModel.X_MAX;
    public static final int Y_MAX = GameModel.Y_MAX;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;
    public static final int COLUMN_BOTTOM = (Y_MAX - 1);
    public static final String EMPTY = "EMPTY";

    public ArrayList<LinkedList<GamePiece>> findVerticalMatches(GameBoard gameBoard) {
        LinkedList<GamePiece> consecutiveEmoticons = new LinkedList<>();
        ArrayList<LinkedList<GamePiece>> bigList = new ArrayList<>();
        GamePiece emo;
        for (int x = ROW_START; x < X_MAX; x++) {
            consecutiveEmoticons.add(gameBoard.getGamePiece(x, COLUMN_BOTTOM));


            for (int y = (COLUMN_BOTTOM - 1); y >= COLUMN_TOP; y--) {
                emo = gameBoard.getGamePiece(x, y);
                if (!emo.getEmoType().equals(consecutiveEmoticons.getLast().getEmoType())) {
                    examineList(consecutiveEmoticons, bigList);
                    consecutiveEmoticons = new LinkedList<>();
                }
                consecutiveEmoticons.add(emo);
                if (y == COLUMN_TOP) {
                    examineList(consecutiveEmoticons, bigList);
                    consecutiveEmoticons = new LinkedList<>();
                }
            }
        }
        return bigList;
    }

    public ArrayList<LinkedList<GamePiece>> findHorizontalMatches(GameBoard gameBoard) {
        LinkedList<GamePiece> consecutiveEmoticons = new LinkedList<>();
        ArrayList<LinkedList<GamePiece>> bigList = new ArrayList<>();
        GamePiece emo;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            consecutiveEmoticons.add(gameBoard.getGamePiece(ROW_START, y));

            for (int x = (ROW_START + 1); x < X_MAX; x++) {
                emo = gameBoard.getGamePiece(x, y);
                if (!(emo.getEmoType().equals(consecutiveEmoticons.getLast().getEmoType()))) {
                    examineList(consecutiveEmoticons, bigList);
                    consecutiveEmoticons = new LinkedList<>();
                }
                consecutiveEmoticons.add(emo);
                if (x == (X_MAX - 1)) {
                    examineList(consecutiveEmoticons, bigList);
                    consecutiveEmoticons = new LinkedList<>();
                }
            }
        }
        return bigList;

    }

    private void examineList(LinkedList<GamePiece> consecutiveEmotions, ArrayList<LinkedList<GamePiece>> bigList) {
        if ((consecutiveEmotions.size() >= 3) && (allSameType(consecutiveEmotions))) {
            bigList.add(consecutiveEmotions);
        }
    }

    private boolean allSameType(LinkedList<GamePiece> consecutiveEmoticons) {
        String previousEmo = consecutiveEmoticons.getFirst().getEmoType();
        String nextEmo;
        for (int i = 1; i < consecutiveEmoticons.size(); i++) {
            nextEmo = consecutiveEmoticons.get(i).getEmoType();
            if (nextEmo.equals(EMPTY) || (!(nextEmo.equals(previousEmo)))) {
                return false;
            } else {
                previousEmo = nextEmo;
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

                type = gameBoard.getGamePiece(x, y).getEmoType();

                if ((y - 1 >= COLUMN_TOP &&
                        gameBoard.getGamePiece(x, y - 1).getEmoType().equals(type) &&
                        verticalA(gameBoard, type, x, y)) ||
                        (y - 2 >= COLUMN_TOP &&
                                gameBoard.getGamePiece(x, y - 2).getEmoType().equals(type) &&
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
        return ((y - 3 >= COLUMN_TOP && gameBoard.getGamePiece(x, y - 3).getEmoType().equals(type)) ||
                (x - 1 >= ROW_START && gameBoard.getGamePiece(x - 1, y - 2).getEmoType().equals(type)) ||
                (x + 1 < X_MAX && gameBoard.getGamePiece(x + 1, y - 2).getEmoType().equals(type)));
    }

    /**
     * The condition that (y + 1) must be less than
     * COLUMN_BOTTOM was checked in the calling method
     */
    private boolean verticalBelowA(GameBoard gameBoard, String type, int x, int y) {
        return ((y + 2 <= COLUMN_BOTTOM && gameBoard.getGamePiece(x, y + 2).getEmoType().equals(type)) ||
                (x - 1 >= ROW_START && gameBoard.getGamePiece(x - 1, y + 1).getEmoType().equals(type)) ||
                (x + 1 < X_MAX && gameBoard.getGamePiece(x + 1, y + 1).getEmoType().equals(type)));
    }

    private boolean verticalB(GameBoard gameBoard, String type, int x, int y) {
        return ((x - 1 >= ROW_START && gameBoard.getGamePiece(x - 1, y - 1).getEmoType().equals(type)) ||
                (x + 1 < X_MAX && gameBoard.getGamePiece(x + 1, y - 1).getEmoType().equals(type)));
    }

    private boolean horizontalMatchPossible(GameBoard gameBoard) {
        String type;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < X_MAX; x++) {

                type = gameBoard.getGamePiece(x, y).getEmoType();

                if ((x + 1 < X_MAX &&
                        gameBoard.getGamePiece(x + 1, y).getEmoType().equals(type) &&
                        horizontalA(gameBoard, type, x, y)) ||
                        (x + 2 < X_MAX && gameBoard.getGamePiece(x + 2, y).getEmoType().equals(type) &&
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
        return ((x + 3 < X_MAX && gameBoard.getGamePiece(x + 3, y).getEmoType().equals(type)) ||
                (y - 1 >= COLUMN_TOP && gameBoard.getGamePiece(x + 2, y - 1).getEmoType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && gameBoard.getGamePiece(x + 2, y + 1).getEmoType().equals(type)));
    }

    /**
     * The condition that (x - 1) must be above equal to or
     * above  ROW_START was checked in the calling method
     */
    private boolean horizontalLeftA(GameBoard gameBoard, String type, int x, int y) {
        return ((x - 2 >= ROW_START && gameBoard.getGamePiece(x - 2, y).getEmoType().equals(type)) ||
                (y - 1 >= COLUMN_TOP && gameBoard.getGamePiece(x - 1, y - 1).getEmoType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && gameBoard.getGamePiece(x - 1, y + 1).getEmoType().equals(type)));
    }

    private boolean horizontalB(GameBoard gameBoard, String type, int x, int y) {
        return ((y - 1 >= COLUMN_TOP && gameBoard.getGamePiece(x + 1, y - 1).getEmoType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && gameBoard.getGamePiece(x + 1, y + 1).getEmoType().equals(type)));
    }
}
