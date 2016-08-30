package com.example.mark.msccomputerscienceproject.model;

import com.example.mark.msccomputerscienceproject.controller.GameActivityImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class BoardImpl implements Board {

    private static final int ROWS = GameActivityImpl.ROWS;
    private static final int COLUMNS = GameActivityImpl.COLUMNS;
    private static final int ROW_START = 0;
    private static final int COLUMN_TOP = 0;
    private static final int COLUMN_BOTTOM = (ROWS - 1);
    private static final int frames = 32;

    private GamePiece[][] emoticons = new GamePiece[COLUMNS][ROWS];
    private static Board instance;

    private BoardImpl() {
    }

    public static synchronized Board getInstance() {
        if (instance == null) {
            instance = new BoardImpl();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GamePiece getGamePiece(int x, int y) throws ArrayIndexOutOfBoundsException {
        if (x >= COLUMNS || y >= ROWS) {
            throw new ArrayIndexOutOfBoundsException("getGamePiece argument larger than board");
        }
        return emoticons[x][y];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGamePiece(int x, int y, GamePiece gamePiece) throws ArrayIndexOutOfBoundsException {
        if (x >= COLUMNS || y >= ROWS) {
            throw new ArrayIndexOutOfBoundsException("setGamePiece argument larger than board");
        }
        this.emoticons[x][y] = gamePiece;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void highlight(int x, int y) {
        emoticons[x][y].setHighlight(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void highlight(MatchContainer matchContainer) {
        highlightMatches(matchContainer.getMatchingX());
        highlightMatches(matchContainer.getMatchingY());
    }

    private void highlightMatches(ArrayList<LinkedList<GamePiece>> matches) {
        for (List<GamePiece> removeList : matches) {
            for (GamePiece emo : removeList) {
                emo.setHighlight(true);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearHighlights() {
        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                emoticons[x][y].setHighlight(false);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        emoticons = new GamePiece[COLUMNS][ROWS];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearGamePieces() {
        for (int y = COLUMN_BOTTOM; y >= COLUMN_TOP; y--) {
            for (int x = ROW_START; x < COLUMNS; x++) {
                emoticons[x][y].setArrayY(ROWS);
                emoticons[x][y].setPixelMovement(frames);
                emoticons[x][y].setDropping(true);
            }
        }
    }
}
