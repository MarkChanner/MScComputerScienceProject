package com.example.mark.msccomputerscienceproject.emoticon_populator;

import android.content.Context;

import com.example.mark.msccomputerscienceproject.Emoticon;
import com.example.mark.msccomputerscienceproject.EmptyEmoticon;
import com.example.mark.msccomputerscienceproject.MockEmoticon;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public abstract class AbstractEmoticonFactory {

    protected int emoWidth;
    protected int emoHeight;
    protected BitmapCreator bitmapCreator;

    public AbstractEmoticonFactory(Context context, int emoWidth, int emoHeight) {
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
        this.bitmapCreator = BitmapCreator.getInstance();
        this.bitmapCreator.prepareScaledBitmaps(context, emoWidth, emoHeight);
    }

    public abstract Emoticon createRandomEmoticon(int x, int y, int offScreenStartPositionY);

    public abstract Emoticon createSpecifiedEmoticon(int x, int y, String emoType);

    public Emoticon createMockEmoticon(int x, int y) {
        String mockID = ("" + x + y);
        return new MockEmoticon(x, y, emoWidth, emoHeight, bitmapCreator.getMockBitmap(), mockID);
    }

    public Emoticon createEmptyEmoticon(int x, int y) {
        return new EmptyEmoticon(x, y, emoWidth, emoHeight, bitmapCreator.getEmptyBitmap());
    }

}
