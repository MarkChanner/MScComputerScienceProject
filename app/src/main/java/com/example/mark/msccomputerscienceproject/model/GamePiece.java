package com.example.mark.msccomputerscienceproject.model;

import android.graphics.Bitmap;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public abstract class GamePiece {

    public static final int DIVISOR = 2;
    private int arrayX;
    private int arrayY;
    private int emoWidth;
    private int emoHeight;
    private Bitmap bitmap;
    private String emoticonType;
    private int screenPositionX;
    private int screenPositionY;
    private int pixelMovement;

    volatile boolean dropping;
    volatile boolean swappingUp;
    volatile boolean swappingDown;
    volatile boolean swappingRight;
    volatile boolean swappingLeft;
    volatile boolean isPartOfMatch;
    volatile boolean isSelected;

    public GamePiece(int arrayX, int arrayY, int emoWidth, int emoHeight, Bitmap bitmap, String emoType, int offScreenStartPositionY) {
        this.arrayX = arrayX;
        this.arrayY = arrayY;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
        this.bitmap = bitmap;
        this.emoticonType = emoType;
        screenPositionX = (arrayX * emoWidth);
        screenPositionY = (offScreenStartPositionY * emoHeight);
        pixelMovement = (emoHeight / 8);
        dropping = true;
    }

    public boolean isSwapping() {
        if (swappingUp) {
            return true;
        } else if (swappingDown) {
            return true;
        } else if (swappingLeft) {
            return true;
        } else if (swappingRight) {
            return true;
        } else {
            return false;
        }
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

    public void setIsSelected(boolean bool) {
        if (!isDropping()) {
            isSelected = bool;
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsPartOfMatch(boolean bool) {
        isPartOfMatch = bool;
    }

    public boolean isPartOfMatch() {
        return isPartOfMatch;
    }

    public void setDropping(boolean bool) {
        dropping = bool;
    }


    public void dropEmoticon() {
        int newPosition = (arrayY * emoHeight);
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
        int newPosition = emoHeight * arrayY;
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
        int newPosition = emoHeight * arrayY;
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
        int newPosition = emoWidth * arrayX;
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
        int newPosition = emoWidth * arrayX;
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

    public String getEmoType() {
        return emoticonType;
    }

    public void setPixelMovement(int pixelMovement) {
        this.pixelMovement = pixelMovement;
    }
}
