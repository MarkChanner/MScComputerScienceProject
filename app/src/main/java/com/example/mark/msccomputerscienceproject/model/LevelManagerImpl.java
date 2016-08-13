package com.example.mark.msccomputerscienceproject.model;

/**
 * final class as not to be subclassed
 *
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class LevelManagerImpl implements LevelManager {

    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;
    private int emoWidth;
    private int emoHeight;
    private int level;
    private GamePieceFactory emoFactory;

    public LevelManagerImpl(int emoWidth, int emoHeight, int level) {
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
        this.level = level;
        setGameLevel(level);
    }

    @Override
    public void setGameLevel(int level) {
        if (level == LEVEL_ONE) {
            emoFactory = new EmoticonFactoryLevel01(emoWidth, emoHeight);
        } else if (level == LEVEL_TWO) {
            emoFactory = new EmoticonFactoryLevel02(emoWidth, emoHeight);
        } else {
            throw new IndexOutOfBoundsException("level out of bounds in setEmoFactory(int)");
        }
    }

    @Override
    public int getGameLevel() {
        return level;
    }

    @Override
    public void incrementLevel() {
        level++;
        setGameLevel(level);

    }

    @Override
    public GamePieceFactory getGamePieceFactory() {
        return emoFactory;
    }
}
