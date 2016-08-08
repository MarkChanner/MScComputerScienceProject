package com.example.mark.msccomputerscienceproject.mocks;

import android.graphics.Bitmap;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class MockEmoticonImpl extends MockAbstractEmoticon{

    public MockEmoticonImpl(int x, int y, int emoWidth, int emoHeight, Bitmap mockBitmap, String emoticonType) {
        super(x, y, emoWidth, emoHeight, mockBitmap, emoticonType);
        super.dropping = false;
    }

}



