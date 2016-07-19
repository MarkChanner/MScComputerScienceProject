package com.example.mark.msccomputerscienceproject;

import android.content.Context;
import android.graphics.Bitmap;


public class EmoticonFactory {

    BitmapCreator bitmapCreator;

    public EmoticonFactory(Context context, int emoWidth, int emoHeight) {

    }

    /**
     * Returns one of five emoticons that are chosen at random
     *
     * @return a subclass of AbstractEmoticon (AbstractEmoticon implements Emoticon interface)
     */
    public Emoticon createEmoticon(int x, int y, int emoWidth, int emoHeight, int offScreenStartPositionY) {
        String emoID = null;
        Bitmap bitmap = null;
        return new EmoticonImpl(x, y, emoWidth, emoHeight, bitmap, emoID, offScreenStartPositionY);
    }

    public Emoticon getEmptyEmoticon(int x, int y, int emoticonWidth, int emoticonHeight) {
        return new EmptyEmoticon(x, y, emoticonWidth, emoticonHeight, bitmapCreator.getEmptyBitmap());
    }
}