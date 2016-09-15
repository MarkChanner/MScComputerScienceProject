package com.example.mark.msccomputerscienceproject.model;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public abstract class GamePieceFactory {

    protected BitmapCreator bitmapCreator;
    protected int tileWidth;
    protected int tileHeight;

    public GamePieceFactory(int tileWidth, int tileHeight) {
        this.bitmapCreator = BitmapCreator.getInstance();
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    /**
     * A factory method which defers instantiation of GamePiece to a subclass
     *
     * @param x                       the x coordinate of the required GamePiece
     * @param y                       the y coordinate of the required GamePiece
     * @param startY the y coordinate for GamePiece to updateBoard from
     * @return GamePiece
     */
    protected abstract GamePiece getRandomGamePiece(int x, int y, int startY);

    /**
     * Returns a random GamePiece object
     *
     * @return GamePiece
     */
    public GamePiece createRandomGamePiece(int x, int y, int startY) {
        return getRandomGamePiece(x, y, startY);
    }

    /**
     * Returns a GamePiece without an ID or updateBoard down position
     *
     * @return GamePiece
     */
    public GamePiece createBlankTile(int x, int y) {
        return new BlankTile(x, y, tileWidth, tileHeight, bitmapCreator.getEmptyBitmap());
    }
}
