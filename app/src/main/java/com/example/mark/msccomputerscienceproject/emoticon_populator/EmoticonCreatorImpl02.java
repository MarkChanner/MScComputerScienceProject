package com.example.mark.msccomputerscienceproject.emoticon_populator;

import android.graphics.Bitmap;
import com.example.mark.msccomputerscienceproject.*;
import java.util.Random;

public class EmoticonCreatorImpl02 implements EmoticonCreator {

    private BitmapCreator bitmapCreator;
    private int emoWidth;
    private int emoHeight;

    public EmoticonCreatorImpl02(BitmapCreator bitmapCreator, int emoWidth, int emoHeight) {
        this.bitmapCreator = bitmapCreator;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
    }

    /**
     * Returns one of five emoticons that are chosen at random
     *
     * @return EmoticonImpl
     */
    public Emoticon createRandomEmoticon(int x, int y, int offScreenStartPositionY) {
        Random random = new Random();
        String emoID = null;
        Bitmap bitmap = null;
        switch (random.nextInt(5)) {
            case 0:
                emoID = "ANGRY2";
                bitmap = bitmapCreator.getAngry2Bitmap();
                break;
            case 1:
                emoID = "HAPPY2";
                bitmap = bitmapCreator.getHappy2Bitmap();
                break;
            case 2:
                emoID = "EMBARRASSED2";
                bitmap = bitmapCreator.getEmbarrassed2Bitmap();
                break;
            case 3:
                emoID = "SURPRISED2";
                bitmap = bitmapCreator.getSurprised2Bitmap();
                break;
            case 4:
                emoID = "SAD2";
                bitmap = bitmapCreator.getSad2Bitmap();
                break;
            default:
                break;
        }
        return new EmoticonImpl(x, y, emoWidth, emoHeight, bitmap, emoID, offScreenStartPositionY);
    }

    public Emoticon createSpecifiedEmoticon(int x, int y, String emoType) {
        Bitmap bitmap = null;
        switch (emoType) {
            case "ANGRY2":
                bitmap = bitmapCreator.getAngry2Bitmap();
                break;
            case "HAPPY2":
                bitmap = bitmapCreator.getHappy2Bitmap();
                break;
            case "EMBARRASSED2":
                bitmap = bitmapCreator.getEmbarrassed2Bitmap();
                break;
            case "SURPRISED2":
                bitmap = bitmapCreator.getSurprised2Bitmap();
                break;
            case "SAD2":
                bitmap = bitmapCreator.getSad2Bitmap();
                break;
            default:
                break;
        }
        return new EmoticonImpl(x, y, emoWidth, emoHeight, bitmap, emoType, y);
    }

    public Emoticon createMockEmoticon(int x, int y) {
        String mockID = ("" + x + y);
        return new MockEmoticon(x, y, emoWidth, emoHeight, bitmapCreator.getMockBitmap(), mockID);
    }

    public Emoticon createEmptyEmoticon(int x, int y) {
        return new EmptyEmoticon(x, y, emoWidth, emoHeight, bitmapCreator.getEmptyBitmap());
    }
}