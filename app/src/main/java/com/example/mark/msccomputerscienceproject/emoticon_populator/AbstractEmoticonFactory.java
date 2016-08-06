package com.example.mark.msccomputerscienceproject.emoticon_populator;

import android.content.Context;

import com.example.mark.msccomputerscienceproject.Emoticon;
import com.example.mark.msccomputerscienceproject.EmptyEmoticon;
import com.example.mark.msccomputerscienceproject.MockEmoticon;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public abstract class AbstractEmoticonFactory {

    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;
    protected int emoWidth;
    protected int emoHeight;
    protected BitmapCreator bitmapCreator;

    public AbstractEmoticonFactory(Context context, int level, int emoWidth, int emoHeight) {
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
        initializeBitmapCreator(context, level);
    }

    private void initializeBitmapCreator(Context context, int level) {
        switch (level) {
            case LEVEL_ONE:
                this.bitmapCreator = BitmapCreator.getInstance();
                this.bitmapCreator.prepareScaledBitmaps(context, emoWidth, emoHeight);
                break;
            case LEVEL_TWO:
                this.bitmapCreator = BitmapCreator.getInstance();
                this.bitmapCreator.prepareScaledBitmaps(context, emoWidth, emoHeight);
                break;
            default:
                break;
        }
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
