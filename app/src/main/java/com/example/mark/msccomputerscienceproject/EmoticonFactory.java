package com.example.mark.msccomputerscienceproject;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Random;

public class EmoticonFactory {

    BitmapCreator bitmapCreator;

    public EmoticonFactory(Context context, int emoWidth, int emoHeight) {
        this.bitmapCreator = BitmapCreator.getInstance();
        this.bitmapCreator.prepareScaledBitmaps(context, emoWidth, emoHeight);
    }

    /**
     * Returns one of five emoticons that are chosen at random
     *
     * @return a subclass of AbstractEmoticon (AbstractEmoticon implements Emoticon interface)
     */
    public Emoticon createEmoticon(int x, int y, int emoWidth, int emoHeight, int offScreenStartPositionY) {
        Random random = new Random();
        String emoID = null;
        Bitmap bitmap = null;
        switch (random.nextInt(5)) {
            case 0:
                emoID = "ANGRY";
                bitmap = bitmapCreator.getAngryBitmap();
                break;
            case 1:
                emoID = "DELIGHTED";
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
                emoID = "UPSET";
                bitmap = bitmapCreator.getSadBitmap();
                break;
            default:
                break;
        }
        return new EmoticonImpl(x, y, emoWidth, emoHeight, bitmap, emoID, offScreenStartPositionY);
    }

    public Emoticon getEmptyEmoticon(int x, int y, int emoticonWidth, int emoticonHeight) {
        return new EmptyEmoticon(x, y, emoticonWidth, emoticonHeight, bitmapCreator.getEmptyBitmap());
    }
}