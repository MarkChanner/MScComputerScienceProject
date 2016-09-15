package com.example.mark.msccomputerscienceproject.model;
import android.graphics.Bitmap;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class BlankTile extends GamePiece {

    private static final String EMOTION_TYPE = "EMPTY";

    public BlankTile(int x, int y, int tileWidth, int tileHeight, Bitmap emptyBitmap) {
        super(x, y, tileWidth, tileHeight, emptyBitmap, EMOTION_TYPE, y);
        super.dropping = false;
    }
}