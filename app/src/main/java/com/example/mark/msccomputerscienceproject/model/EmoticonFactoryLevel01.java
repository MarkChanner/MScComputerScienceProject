package com.example.mark.msccomputerscienceproject.model;

import android.graphics.Bitmap;

import java.util.Random;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class EmoticonFactoryLevel01 extends GamePieceFactory {

    public EmoticonFactoryLevel01(int emoWidth, int emoHeight) {
        super(emoWidth, emoHeight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected GamePiece getRandomGamePiece(int x, int y, int startY) {
        Random random = new Random();
        String emoID = null;
        Bitmap bitmap = null;
        switch (random.nextInt(5)) {
            case 0:
                emoID = "ANGRY";
                bitmap = bitmapCreator.getAngryBitmap();
                break;
            case 1:
                emoID = "HAPPY";
                bitmap = bitmapCreator.getHappyBitmap();
                break;
            case 2:
                emoID = "EMBARRASSED";
                bitmap = bitmapCreator.getEmbarrassedBitmap();
                break;
            case 3:
                emoID = "SURPRISED";
                bitmap = bitmapCreator.getSurprisedBitmap();
                break;
            case 4:
                emoID = "SAD";
                bitmap = bitmapCreator.getSadBitmap();
                break;
            default:
                break;
        }
        return new Emoticon(x, y, emoWidth, emoHeight, bitmap, emoID, startY);
    }
}