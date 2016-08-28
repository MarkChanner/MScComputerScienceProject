package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameActivityImpl;

import java.util.LinkedList;
import java.util.ArrayList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class MatchFinderImpl implements MatchFinder {

    private static final String TAG = "MatchFinderImpl";
    private static final int ROWS = GameActivityImpl.ROWS;
    private static final int COLUMNS = GameActivityImpl.COLUMNS;
    private static final int ROW_START = 0;
    private static final int ROW_END = (COLUMNS - 1);
    private static final int COLUMN_TOP = 0;
    private static final int COLUMN_BOTTOM = (ROWS - 1);
    private static final String EMPTY = "EMPTY";

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchContainer findMatches(GameBoard board) {
        ArrayList<LinkedList<GamePiece>> matchingX = findVerticalMatches(board);
        ArrayList<LinkedList<GamePiece>> matchingY = findHorizontalMatches(board);
        MatchContainer container = new MatchContainerImpl();
        container.addMatchingX(matchingX);
        container.addMatchingY(matchingY);
        return container;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean furtherMatchesPossible(GameBoard board) {
        return (verticalMatchPossible(board) || horizontalMatchPossible(board));
    }

    private ArrayList<LinkedList<GamePiece>> findVerticalMatches(GameBoard board) {
        LinkedList<GamePiece> consecutiveEmoticons = new LinkedList<>();
        ArrayList<LinkedList<GamePiece>> bigList = new ArrayList<>();
        GamePiece emo;
        for (int x = ROW_START; x < COLUMNS; x++) {
            consecutiveEmoticons.add(board.getGamePiece(x, COLUMN_BOTTOM));


            for (int y = (COLUMN_BOTTOM - 1); y >= COLUMN_TOP; y--) {
                emo = board.getGamePiece(x, y);
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

    private ArrayList<LinkedList<GamePiece>> findHorizontalMatches(GameBoard board) {
        LinkedList<GamePiece> consecutiveEmoticons = new LinkedList<>();
        ArrayList<LinkedList<GamePiece>> bigList = new ArrayList<>();
        GamePiece emo;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            consecutiveEmoticons.add(board.getGamePiece(ROW_START, y));

            for (int x = (ROW_START + 1); x < COLUMNS; x++) {
                emo = board.getGamePiece(x, y);
                if (!(emo.getEmoType().equals(consecutiveEmoticons.getLast().getEmoType()))) {
                    examineList(consecutiveEmoticons, bigList);
                    consecutiveEmoticons = new LinkedList<>();
                }
                consecutiveEmoticons.add(emo);
                if (x == ROW_END) {
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

    private boolean verticalMatchPossible(GameBoard board) {
        String type;
        for (int x = ROW_START; x < COLUMNS; x++) {
            for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {

                type = board.getGamePiece(x, y).getEmoType();

                if ((y - 1 >= COLUMN_TOP &&
                        board.getGamePiece(x, y - 1).getEmoType().equals(type) &&
                        verticalA(board, type, x, y)) ||
                        (y - 2 >= COLUMN_TOP &&
                                board.getGamePiece(x, y - 2).getEmoType().equals(type) &&
                                verticalB(board, type, x, y))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verticalA(GameBoard board, String type, int x, int y) {
        return ((y - 2 >= COLUMN_TOP && verticalAboveA(board, type, x, y)) ||
                (y + 1 <= COLUMN_BOTTOM && verticalBelowA(board, type, x, y)));
    }

    /**
     * The condition that (y - 2) must be higher than
     * COLUMN_TOP was  checked in the calling method
     */
    private boolean verticalAboveA(GameBoard board, String type, int x, int y) {
        return ((y - 3 >= COLUMN_TOP && board.getGamePiece(x, y - 3).getEmoType().equals(type)) ||
                (x - 1 >= ROW_START && board.getGamePiece(x - 1, y - 2).getEmoType().equals(type)) ||
                (x + 1 < COLUMNS && board.getGamePiece(x + 1, y - 2).getEmoType().equals(type)));
    }

    /**
     * The condition that (y + 1) must be less than
     * COLUMN_BOTTOM was checked in the calling method
     */
    private boolean verticalBelowA(GameBoard board, String type, int x, int y) {
        return ((y + 2 <= COLUMN_BOTTOM && board.getGamePiece(x, y + 2).getEmoType().equals(type)) ||
                (x - 1 >= ROW_START && board.getGamePiece(x - 1, y + 1).getEmoType().equals(type)) ||
                (x + 1 < COLUMNS && board.getGamePiece(x + 1, y + 1).getEmoType().equals(type)));
    }

    private boolean verticalB(GameBoard board, String type, int x, int y) {
        return ((x - 1 >= ROW_START && board.getGamePiece(x - 1, y - 1).getEmoType().equals(type)) ||
                (x + 1 < COLUMNS && board.getGamePiece(x + 1, y - 1).getEmoType().equals(type)));
    }

    private boolean horizontalMatchPossible(GameBoard board) {
        String type;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < COLUMNS; x++) {

                type = board.getGamePiece(x, y).getEmoType();

                if ((x + 1 < COLUMNS &&
                        board.getGamePiece(x + 1, y).getEmoType().equals(type) &&
                        horizontalA(board, type, x, y)) ||
                        (x + 2 < COLUMNS && board.getGamePiece(x + 2, y).getEmoType().equals(type) &&
                                horizontalB(board, type, x, y))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean horizontalA(GameBoard board, String type, int x, int y) {
        return ((x + 2 < COLUMNS && horizontalRightA(board, type, x, y)) ||
                (x - 1 >= ROW_START && horizontalLeftA(board, type, x, y)));
    }

    /**
     * The condition that (x + 2) must be above
     * below COLUMNS was checked in the calling method
     */
    private boolean horizontalRightA(GameBoard board, String type, int x, int y) {
        return ((x + 3 < COLUMNS && board.getGamePiece(x + 3, y).getEmoType().equals(type)) ||
                (y - 1 >= COLUMN_TOP && board.getGamePiece(x + 2, y - 1).getEmoType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && board.getGamePiece(x + 2, y + 1).getEmoType().equals(type)));
    }

    /**
     * The condition that (x - 1) must be above equal to or
     * above  ROW_START was checked in the calling method
     */
    private boolean horizontalLeftA(GameBoard board, String type, int x, int y) {
        return ((x - 2 >= ROW_START && board.getGamePiece(x - 2, y).getEmoType().equals(type)) ||
                (y - 1 >= COLUMN_TOP && board.getGamePiece(x - 1, y - 1).getEmoType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && board.getGamePiece(x - 1, y + 1).getEmoType().equals(type)));
    }

    private boolean horizontalB(GameBoard board, String type, int x, int y) {
        return ((y - 1 >= COLUMN_TOP && board.getGamePiece(x + 1, y - 1).getEmoType().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && board.getGamePiece(x + 1, y + 1).getEmoType().equals(type)));
    }
}
