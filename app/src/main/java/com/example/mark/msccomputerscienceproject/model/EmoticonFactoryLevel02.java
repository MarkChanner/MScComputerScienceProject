package com.example.mark.msccomputerscienceproject.model;

import android.graphics.Bitmap;

import java.util.Random;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class EmoticonFactoryLevel02 extends AbstractEmoticonFactory {

    public EmoticonFactoryLevel02(BitmapCreator bitmapCreator, int emoWidth, int emoHeight) {
        super(bitmapCreator, emoWidth, emoHeight);
    }

    /**
     * Returns one of five emoticons that are chosen at random
     *
     * @return EmoticonImpl
     */
    @Override
    protected Emoticon createRandomEmoticon(int x, int y, int offScreenStartPositionY) {
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
}