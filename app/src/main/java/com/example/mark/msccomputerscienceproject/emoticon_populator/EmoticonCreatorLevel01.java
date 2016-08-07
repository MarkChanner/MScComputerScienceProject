package com.example.mark.msccomputerscienceproject.emoticon_populator;

import android.graphics.Bitmap;

import com.example.mark.msccomputerscienceproject.*;

import java.util.Random;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class EmoticonCreatorLevel01 extends AbstractEmoticonCreator {

    public EmoticonCreatorLevel01(BitmapCreator bitmapCreator, int emoWidth, int emoHeight) {
        super(bitmapCreator, emoWidth, emoHeight);
    }

    /**
     * Returns one of five emoticons that are chosen at random
     *
     * @return EmoticonImpl
     */
    @Override
    public Emoticon createRandomEmoticon(int x, int y, int offScreenStartPositionY) {
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

    @Override
    public Emoticon createSpecifiedEmoticon(int x, int y, String emoType) {
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
}