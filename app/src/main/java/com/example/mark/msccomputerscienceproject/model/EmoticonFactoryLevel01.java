package com.example.mark.msccomputerscienceproject.model;

import android.graphics.Bitmap;

import java.util.Random;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class EmoticonFactoryLevel01 extends GamePieceFactory {

    public static final String ANGRY = "ANGRY";
    public static final String HAPPY = "HAPPY";
    public static final String EMBARRASSED = "EMBARRASSED";
    public static final String SAD = "SAD";
    public static final String SURPRISED = "SURPRISED";

    public EmoticonFactoryLevel01(int tileWidth, int tileHeight) {
        super(tileWidth, tileHeight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected GamePiece getRandomGamePiece(int x, int y, int startY) {
        Random random = new Random();
        String type = null;
        Bitmap bitmap = null;
        switch (random.nextInt(5)) {
            case 0:
                type = ANGRY;
                bitmap = bitmapCreator.getAngryBitmap();
                break;
            case 1:
                type = HAPPY;
                bitmap = bitmapCreator.getHappyBitmap();
                break;
            case 2:
                type = EMBARRASSED;
                bitmap = bitmapCreator.getEmbarrassedBitmap();
                break;
            case 3:
                type = SURPRISED;
                bitmap = bitmapCreator.getSurprisedBitmap();
                break;
            case 4:
                type = SAD;
                bitmap = bitmapCreator.getSadBitmap();
                break;
            default:
                break;
        }
        return new Emoticon(x, y, tileWidth, tileHeight, bitmap, type, startY);
    }
}