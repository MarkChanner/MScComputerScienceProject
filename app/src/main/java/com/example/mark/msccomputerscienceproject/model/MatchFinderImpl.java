package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameActivityImpl;

import java.util.LinkedList;
import java.util.ArrayList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class MatchFinderImpl implements MatchFinder {

    private static final String TAG = "MatchFinderImpl";
    private static final int ROWS = GameActivityImpl.MAX_ROWS;
    private static final int COLUMNS = GameActivityImpl.MAX_COLUMNS;
    private static final int ROW_START = 0;
    private static final int ROW_END = (COLUMNS - 1);
    private static final int COLUMN_TOP = 0;
    private static final int COLUMN_BOTTOM = (ROWS - 1);
    private static final String EMPTY = "EMPTY";
    private GameBoard gameGameBoard;

    public MatchFinderImpl(GameBoard gameGameBoard) {
        this.gameGameBoard = gameGameBoard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchContainer findMatches() {
        ArrayList<LinkedList<GamePiece>> matchingX = findVerticalMatches();
        ArrayList<LinkedList<GamePiece>> matchingY = findHorizontalMatches();
        MatchContainer container = new MatchContainerImpl();
        container.addMatchingX(matchingX);
        container.addMatchingY(matchingY);
        return container;
    }
    @Override
    public boolean getTrue() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean furtherMatchesPossible() {
        return (verticalMatchPossible() || horizontalMatchPossible());
    }

    private ArrayList<LinkedList<GamePiece>> findVerticalMatches() {
        LinkedList<GamePiece> matchingEmoticons = new LinkedList<>();
        ArrayList<LinkedList<GamePiece>> allVerticalMatches = new ArrayList<>();
        GamePiece emo;
        for (int x = ROW_START; x < COLUMNS; x++) {
            matchingEmoticons.add(gameGameBoard.getGamePiece(x, COLUMN_BOTTOM));

            for (int y = (COLUMN_BOTTOM - 1); y >= COLUMN_TOP; y--) {
                emo = gameGameBoard.getGamePiece(x, y);
                if (!emo.toString().equals(matchingEmoticons.getLast().toString())) {
                    examineList(matchingEmoticons, allVerticalMatches);
                    matchingEmoticons = new LinkedList<>();
                }
                matchingEmoticons.add(emo);
                if (y == COLUMN_TOP) {
                    examineList(matchingEmoticons, allVerticalMatches);
                    matchingEmoticons = new LinkedList<>();
                }
            }
        }
        return allVerticalMatches;
    }

    private ArrayList<LinkedList<GamePiece>> findHorizontalMatches() {
        LinkedList<GamePiece> matchingEmoticons = new LinkedList<>();
        ArrayList<LinkedList<GamePiece>> allHorizontalMatches = new ArrayList<>();
        GamePiece emo;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            matchingEmoticons.add(gameGameBoard.getGamePiece(ROW_START, y));

            for (int x = (ROW_START + 1); x < COLUMNS; x++) {
                emo = gameGameBoard.getGamePiece(x, y);
                if (!(emo.toString().equals(matchingEmoticons.getLast().toString()))) {
                    examineList(matchingEmoticons, allHorizontalMatches);
                    matchingEmoticons = new LinkedList<>();
                }
                matchingEmoticons.add(emo);
                if (x == ROW_END) {
                    examineList(matchingEmoticons, allHorizontalMatches);
                    matchingEmoticons = new LinkedList<>();
                }
            }
        }
        return allHorizontalMatches;
    }

    private void examineList(LinkedList<GamePiece> matchingEmoticons, ArrayList<LinkedList<GamePiece>> allMatches) {
        if ((matchingEmoticons.size() >= 3) && (allSameType(matchingEmoticons))) {
            allMatches.add(matchingEmoticons);
        }
    }

    private boolean allSameType(LinkedList<GamePiece> consecutiveEmoticons) {
        String previousEmo = consecutiveEmoticons.getFirst().toString();
        String nextEmo;
        for (int i = 1; i < consecutiveEmoticons.size(); i++) {
            nextEmo = consecutiveEmoticons.get(i).toString();
            if (nextEmo.equals(EMPTY) || (!(nextEmo.equals(previousEmo)))) {
                return false;
            } else {
                previousEmo = nextEmo;
            }
        }
        return true;
    }

    private boolean verticalMatchPossible() {
        String type;
        for (int x = ROW_START; x < COLUMNS; x++) {
            for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {

                type = gameGameBoard.getGamePiece(x, y).toString();

                if ((y - 1 >= COLUMN_TOP &&
                        gameGameBoard.getGamePiece(x, y - 1).toString().equals(type) &&
                        verticalA(type, x, y)) ||
                        (y - 2 >= COLUMN_TOP &&
                                gameGameBoard.getGamePiece(x, y - 2).toString().equals(type) &&
                                verticalB(type, x, y))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verticalA(String type, int x, int y) {
        return ((y - 2 >= COLUMN_TOP && verticalAboveA(type, x, y)) ||
                (y + 1 <= COLUMN_BOTTOM && verticalBelowA(type, x, y)));
    }

    /**
     * The condition that (y - 2) must be higher than
     * COLUMN_TOP was  checked in the calling method
     */
    private boolean verticalAboveA(String type, int x, int y) {
        return ((y - 3 >= COLUMN_TOP && gameGameBoard.getGamePiece(x, y - 3).toString().equals(type)) ||
                (x - 1 >= ROW_START && gameGameBoard.getGamePiece(x - 1, y - 2).toString().equals(type)) ||
                (x + 1 < COLUMNS && gameGameBoard.getGamePiece(x + 1, y - 2).toString().equals(type)));
    }

    /**
     * The condition that (y + 1) must be less than
     * COLUMN_BOTTOM was checked in the calling method
     */
    private boolean verticalBelowA(String type, int x, int y) {
        return ((y + 2 <= COLUMN_BOTTOM && gameGameBoard.getGamePiece(x, y + 2).toString().equals(type)) ||
                (x - 1 >= ROW_START && gameGameBoard.getGamePiece(x - 1, y + 1).toString().equals(type)) ||
                (x + 1 < COLUMNS && gameGameBoard.getGamePiece(x + 1, y + 1).toString().equals(type)));
    }

    private boolean verticalB(String type, int x, int y) {
        return ((x - 1 >= ROW_START && gameGameBoard.getGamePiece(x - 1, y - 1).toString().equals(type)) ||
                (x + 1 < COLUMNS && gameGameBoard.getGamePiece(x + 1, y - 1).toString().equals(type)));
    }

    private boolean horizontalMatchPossible() {
        String type;
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < COLUMNS; x++) {

                type = gameGameBoard.getGamePiece(x, y).toString();

                if ((x + 1 < COLUMNS &&
                        gameGameBoard.getGamePiece(x + 1, y).toString().equals(type) &&
                        horizontalA(type, x, y)) ||
                        (x + 2 < COLUMNS && gameGameBoard.getGamePiece(x + 2, y).toString().equals(type) &&
                                horizontalB(type, x, y))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean horizontalA(String type, int x, int y) {
        return ((x + 2 < COLUMNS && horizontalRightA(type, x, y)) ||
                (x - 1 >= ROW_START && horizontalLeftA(type, x, y)));
    }

    /**
     * The condition that (x + 2) must be above
     * below MAX_COLUMNS was checked in the calling method
     */
    private boolean horizontalRightA(String type, int x, int y) {
        return ((x + 3 < COLUMNS && gameGameBoard.getGamePiece(x + 3, y).toString().equals(type)) ||
                (y - 1 >= COLUMN_TOP && gameGameBoard.getGamePiece(x + 2, y - 1).toString().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && gameGameBoard.getGamePiece(x + 2, y + 1).toString().equals(type)));
    }

    /**
     * The condition that (x - 1) must be above equal to or
     * above  ROW_START was checked in the calling method
     */
    private boolean horizontalLeftA(String type, int x, int y) {
        return ((x - 2 >= ROW_START && gameGameBoard.getGamePiece(x - 2, y).toString().equals(type)) ||
                (y - 1 >= COLUMN_TOP && gameGameBoard.getGamePiece(x - 1, y - 1).toString().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && gameGameBoard.getGamePiece(x - 1, y + 1).toString().equals(type)));
    }

    private boolean horizontalB(String type, int x, int y) {
        return ((y - 1 >= COLUMN_TOP && gameGameBoard.getGamePiece(x + 1, y - 1).toString().equals(type)) ||
                (y + 1 <= COLUMN_BOTTOM && gameGameBoard.getGamePiece(x + 1, y + 1).toString().equals(type)));
    }
}
