package com.example.mark.msccomputerscienceproject.mocks;

import android.graphics.Bitmap;

import com.example.mark.msccomputerscienceproject.model.AbstractEmoticon;
import com.example.mark.msccomputerscienceproject.model.Emoticon;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class MockEmoticon extends AbstractEmoticon implements Emoticon {

    public MockEmoticon(int x, int y, int emoWidth, int emoHeight, Bitmap mockBitmap, String emoticonType) {
        super(x, y, emoWidth, emoHeight, mockBitmap, emoticonType, y);
        super.dropping = false;
    }
}



