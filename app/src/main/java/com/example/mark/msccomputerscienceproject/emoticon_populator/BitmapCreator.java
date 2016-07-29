package com.example.mark.msccomputerscienceproject.emoticon_populator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mark.msccomputerscienceproject.R;

public class BitmapCreator {

    private static BitmapCreator instance;

    private Bitmap angryBitmap;
    private Bitmap happyBitmap;
    private Bitmap embarrassedBitmap;
    private Bitmap surprisedBitmap;
    private Bitmap sadBitmap;
    private Bitmap emptyBitmap;
    private Bitmap mockBitmap;

    private BitmapCreator() {
    }

    public static synchronized BitmapCreator getInstance() {
        if (instance == null) {
            instance = new BitmapCreator();
        }
        return instance;
    }

    public void prepareScaledBitmaps(Context context, int emoWidth, int emoHeight) {
        Bitmap temp;
        temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_tile);
        emptyBitmap = Bitmap.createScaledBitmap(temp, emoWidth, emoHeight, false);
        emptyBitmap.eraseColor(android.graphics.Color.TRANSPARENT);

        temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.angry);
        angryBitmap = Bitmap.createScaledBitmap(temp, emoWidth, emoHeight, false);

        temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.happy);
        happyBitmap = Bitmap.createScaledBitmap(temp, emoWidth, emoHeight, false);

        temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.embarrassed);
        embarrassedBitmap = Bitmap.createScaledBitmap(temp, emoWidth, emoHeight, false);

        temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.surprised);
        surprisedBitmap = Bitmap.createScaledBitmap(temp, emoWidth, emoHeight, false);

        temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.sad);
        sadBitmap = Bitmap.createScaledBitmap(temp, emoWidth, emoHeight, false);

        temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.mock);
        mockBitmap = Bitmap.createScaledBitmap(temp, emoWidth, emoHeight, false);
    }

    public Bitmap getAngryBitmap() {
        return angryBitmap;
    }

    public Bitmap getHappyBitmap() {
        return happyBitmap;
    }

    public Bitmap getEmbarrassedBitmap() {
        return embarrassedBitmap;
    }

    public Bitmap getSurprisedBitmap() {
        return surprisedBitmap;
    }

    public Bitmap getSadBitmap() {
        return sadBitmap;
    }

    public Bitmap getMockBitmap() {
        return mockBitmap;
    }

    public Bitmap getEmptyBitmap() {
        return emptyBitmap;
    }

}
