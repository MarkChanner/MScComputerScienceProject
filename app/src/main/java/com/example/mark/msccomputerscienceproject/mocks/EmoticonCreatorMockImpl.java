package com.example.mark.msccomputerscienceproject.mocks;

import android.graphics.Bitmap;

import com.example.mark.msccomputerscienceproject.model.BitmapCreator;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class EmoticonCreatorMockImpl implements EmoticonCreatorMock {

    protected int emoWidth;
    protected int emoHeight;
    protected BitmapCreator bitmapCreator;

    public EmoticonCreatorMockImpl(BitmapCreator bitmapCreator, int emoWidth, int emoHeight) {
        this.bitmapCreator = bitmapCreator;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
    }

    @Override
    public MockEmoticon createMockEmoticon(int x, int y) {
        String mockID = ("" + x + y);
        return new MockEmoticon(x, y, emoWidth, emoHeight, bitmapCreator.getMockBitmap(), mockID);
    }

    @Override
    public MockEmoticon createSpecificMockEmoticon(int x, int y, String emoType) {
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
        return new MockEmoticon(x, y, emoWidth, emoHeight, bitmap, emoType);
    }
}