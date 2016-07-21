package com.example.mark.msccomputerscienceproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapCreator {

    private static BitmapCreator instance;

    private Bitmap angryBitmap;
    private Bitmap happyBitmap;
    private Bitmap embarrassedBitmap;
    private Bitmap surprisedBitmap;
    private Bitmap sadBitmap;
    private Bitmap emptyBitmap;

    private BitmapCreator() {
    }

    public static synchronized BitmapCreator getInstance() {

    }

    public void prepareScaledBitmaps(Context context, int emoWidth, int emoHeight) {

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

    public Bitmap getEmptyBitmap() {
        return emptyBitmap;
    }
}
