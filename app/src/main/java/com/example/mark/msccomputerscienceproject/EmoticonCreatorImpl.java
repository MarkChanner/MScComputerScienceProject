package com.example.mark.msccomputerscienceproject;

import android.graphics.Bitmap;

import java.util.Random;

public class EmoticonCreatorImpl implements EmoticonCreator {

    private BitmapCreator bitmapCreator;
    private int emoWidth;
    private int emoHeight;

    public EmoticonCreatorImpl(BitmapCreator bitmapCreator, int emoWidth, int emoHeight) {
        this.bitmapCreator = bitmapCreator;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
    }

    /**
     * Returns one of five emoticons that are chosen at random
     *
     * @return EmoticonImpl
     */
    public Emoticon generateRandomEmoticon(int x, int y, int offScreenStartPositionY) {
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
        return new EmoticonImpl(x, y, emoWidth, emoHeight, bitmap, emoID, offScreenStartPositionY);
    }

    public Emoticon generateSpecifiedEmoticon(int x, int y, String emoType) {
        Bitmap bitmap = null;
        switch (emoType) {
            case "ANGRY":
                bitmap = bitmapCreator.getAngryBitmap();
                break;
            case "HAPPY":
                bitmap = bitmapCreator.getHappyBitmap();
                break;
            case "EMBARRASSED":
                bitmap = bitmapCreator.getEmbarrassedBitmap();
                break;
            case "SURPRISED":
                bitmap = bitmapCreator.getSurprisedBitmap();
                break;
            case "SAD":
                bitmap = bitmapCreator.getSadBitmap();
                break;
            default:
                break;
        }
        return new EmoticonImpl(x, y, emoWidth, emoHeight, bitmap, emoType, y);
    }

    public Emoticon generateMockEmoticon(int x, int y) {
        String mockID = ("" + x + y);
        return new MockEmoticon(x, y, emoWidth, emoHeight, bitmapCreator.getMockBitmap(), mockID);
    }

    public Emoticon getEmptyEmoticon(int x, int y) {
        return new EmptyEmoticon(x, y, emoWidth, emoHeight, bitmapCreator.getEmptyBitmap());
    }
}