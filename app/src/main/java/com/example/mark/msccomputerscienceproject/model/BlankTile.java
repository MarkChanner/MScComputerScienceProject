package com.example.mark.msccomputerscienceproject.model;
import android.graphics.Bitmap;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class BlankTile extends GamePiece {

    public static final String EMOTION_TYPE = "EMPTY";

    public BlankTile(int x, int y, int emoWidth, int emoHeight, Bitmap emptyBitmap) {
        super(x, y, emoWidth, emoHeight, emptyBitmap, EMOTION_TYPE, y);
        super.dropping = false;
    }
}


