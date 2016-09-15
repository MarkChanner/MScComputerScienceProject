package com.example.mark.msccomputerscienceproject.model;

/**
 * final class as not to be subclassed
 *
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class LevelManagerImpl implements LevelManager {

    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;
    private int tileWidth;
    private int tileHeight;
    private int level;
    private GamePieceFactory factory;

    public LevelManagerImpl(int tileWidth, int tileHeight, int level) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.level = level;
        setGameLevel(level);
    }

    @Override
    public void setGameLevel(int level) {
        if (level == LEVEL_ONE) {
            factory = new EmoticonFactoryLevel01(tileWidth, tileHeight);
        } else if (level == LEVEL_TWO) {
            factory = new EmoticonFactoryLevel02(tileWidth, tileHeight);
        } else {
            throw new IndexOutOfBoundsException("level out of bounds in setEmoFactory(int)");
        }
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void incrementLevel() {
        level++;
        setGameLevel(level);
    }

    @Override
    public GamePieceFactory getGamePieceFactory() {
        return factory;
    }
}
