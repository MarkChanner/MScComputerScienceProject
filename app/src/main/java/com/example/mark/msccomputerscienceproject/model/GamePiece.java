package com.example.mark.msccomputerscienceproject.model;

import android.graphics.Bitmap;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public abstract class GamePiece {

    public static final int DIVISOR = 2;
    private int arrayX;
    private int arrayY;
    private int pieceWidth;
    private int pieceHeight;
    private Bitmap bitmap;
    private String type;
    private int screenPositionX;
    private int screenPositionY;
    private int pixelMovement;

    volatile boolean dropping;
    volatile boolean swappingUp;
    volatile boolean swappingDown;
    volatile boolean swappingRight;
    volatile boolean swappingLeft;
    volatile boolean highlight;

    public GamePiece(int arrayX, int arrayY, int pieceWidth, int pieceHeight, Bitmap bitmap, String type, int offScreenStartPositionY) {
        this.arrayX = arrayX;
        this.arrayY = arrayY;
        this.pieceWidth = pieceWidth;
        this.pieceHeight = pieceHeight;
        this.bitmap = bitmap;
        this.type = type;
        screenPositionX = (arrayX * pieceWidth);
        screenPositionY = (offScreenStartPositionY * pieceHeight);
        pixelMovement = (pieceHeight / 8);
        dropping = true;
    }

    public void setHighlight(boolean bool) {
        if (!isDropping()) {
            highlight = bool;
        }
    }

    public boolean getHighlight() {
        return highlight;
    }

    public boolean isSwapping() {
        return (swappingUp || swappingDown || swappingLeft || swappingRight);
    }

    public void updateSwapping() {
        if (swappingUp) {
            swapUp();
        } else if (swappingDown) {
            swapDown();
        } else if (swappingRight) {
            swapRight();
        } else if (swappingLeft) {
            swapLeft();
        }
    }

    public boolean isDropping() {
        return dropping;
    }

    public void updateDropping() {
        if (dropping) {
            dropEmoticon();
        }
    }

    public void setDropping(boolean bool) {
        dropping = bool;
    }


    public void dropEmoticon() {
        int newPosition = (arrayY * pieceHeight);
        int pixelRate = pixelMovement;
        while (screenPositionY + pixelRate > newPosition) {
            pixelRate /= DIVISOR;
        }
        screenPositionY += pixelRate;
        if (screenPositionY >= newPosition) {
            dropping = false;
        }
    }

    public void setSwappingUp(boolean bool) {
        swappingUp = bool;
    }

    public void swapUp() {
        int newPosition = pieceHeight * arrayY;
        int pixelRate = pixelMovement;
        while (screenPositionY - pixelRate < newPosition) {
            pixelRate /= DIVISOR;
        }
        screenPositionY -= pixelRate;
        if (screenPositionY <= newPosition) {
            swappingUp = false;
        }
    }

    public void setSwappingDown(boolean bool) {
        swappingDown = bool;
    }

    public void swapDown() {
        int newPosition = pieceHeight * arrayY;
        int pixelRate = pixelMovement;
        while (screenPositionY + pixelRate > newPosition) {
            pixelRate /= DIVISOR;
        }
        screenPositionY += pixelRate;
        if (screenPositionY >= newPosition) {
            swappingDown = false;
        }
    }

    public void setSwappingRight(boolean bool) {
        swappingRight = bool;
    }

    public void swapRight() {
        int newPosition = pieceWidth * arrayX;
        int pixelRate = pixelMovement;
        while (screenPositionX + pixelRate > newPosition) {
            pixelRate /= DIVISOR;
        }
        screenPositionX += pixelRate;
        if (screenPositionX >= newPosition) {
            swappingRight = false;
        }
    }

    public void setSwappingLeft(boolean bool) {
        swappingLeft = bool;
    }

    public void swapLeft() {
        int newPosition = pieceWidth * arrayX;
        int pixelRate = pixelMovement;
        while (screenPositionX - pixelRate < newPosition) {
            pixelRate /= DIVISOR;
        }
        screenPositionX -= pixelRate;
        if (screenPositionX <= newPosition) {
            swappingLeft = false;
        }
    }

    public int getArrayX() {
        return arrayX;
    }

    public void setArrayX(int arrayX) {
        this.arrayX = arrayX;
    }

    public int getArrayY() {
        return arrayY;
    }

    public void setArrayY(int arrayY) {
        this.arrayY = arrayY;
    }

    public int getViewPositionX() {
        return screenPositionX;
    }

    public int getViewPositionY() {
        return screenPositionY;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setPixelMovement(int pixelMovement) {
        this.pixelMovement = pixelMovement;
    }

    @Override
    public String toString() {
        return type;
    }
}
