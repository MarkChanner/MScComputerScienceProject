package com.example.mark.msccomputerscienceproject;

import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceView;
import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class GameBoardView extends SurfaceView implements Runnable {

    public static final String TAG = "GameBoardView";
    public static final int X_MAX = GameControllerImpl.X_MAX;
    public static final int Y_MAX = GameControllerImpl.Y_MAX;
    public static final int ZERO = 0;

    private final Rect highlightSelectionRect = new Rect();
    private final Rect highlightMatchRect = new Rect();
    private SurfaceHolder surfaceHolder;
    private GameController controller;
    private Emoticon[][] emoticons;
    private int emoWidth;
    private int emoHeight;

    private Paint boardColour;
    private Paint gridLineColour;
    private Paint borderColour;
    private Paint selectionFill;
    private Bitmap gridBitmap;
    private Thread gameViewThread = null;
    volatile boolean running = false;

    public GameBoardView(Context controller) {
        super(controller);
    }

    public GameBoardView(Context controller, Emoticon[][] emoticons, int viewSizeX, int viewSizeY, int emoWidth, int emoHeight) {
        super(controller);
        this.controller = (GameController) controller;
        this.emoticons = emoticons;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
        surfaceHolder = getHolder();
        prepareCanvas(controller, viewSizeX, viewSizeY);
    }

    private void prepareCanvas(Context context, int viewSizeX, int viewSizeY) {
        // Log.d(TAG, "in prepareCanvas(Context, int, int)");
        boardColour = new Paint();
        boardColour.setColor(ContextCompat.getColor(context, R.color.gameboard));

        selectionFill = new Paint();
        selectionFill.setColor(ContextCompat.getColor(context, R.color.highlightbackground));

        gridLineColour = new Paint();
        gridLineColour.setColor(Color.BLACK);
        gridLineColour.setStyle(Paint.Style.STROKE);
        gridLineColour.setStrokeWidth(2f);

        borderColour = new Paint();
        borderColour.setColor(ContextCompat.getColor(context, R.color.border));
        borderColour.setStyle(Paint.Style.STROKE);
        borderColour.setStrokeWidth(8f);

        gridBitmap = Bitmap.createBitmap(viewSizeX, viewSizeY, Bitmap.Config.RGB_565);
        Canvas gridCanvas = new Canvas(gridBitmap);
        gridCanvas.drawRect(ZERO, ZERO, viewSizeX, viewSizeY, boardColour);

        for (int i = 0; i < X_MAX; i++) {
            gridCanvas.drawLine(i * emoWidth, ZERO, i * emoWidth, viewSizeY, gridLineColour); // Vertical
        }
        for (int i = 0; i < Y_MAX; i++) {
            gridCanvas.drawLine(ZERO, i * emoHeight, viewSizeX, i * emoHeight, gridLineColour); // Horizontal
        }
        gridCanvas.drawRect(ZERO, ZERO, viewSizeX, viewSizeY, borderColour);
        gridCanvas.drawBitmap(gridBitmap, ZERO, ZERO, null);
    }

    public void control(int ms) {
        //  Log.d(TAG, "in control(int)");
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //  Log.d(TAG, "in pause()");
        running = false;
        while (true) {
            try {
                gameViewThread.join();
                return;
            } catch (InterruptedException e) {
                // try again
            }
        }
    }

    public void resume() {
        //  Log.d(TAG, "in resume()");
        running = true;
        gameViewThread = new Thread(this);
        gameViewThread.start();
    }

    @Override
    public void run() {
        // Log.d(TAG, "in run()");
        Canvas canvas;
        while (running) {
            if (surfaceHolder.getSurface().isValid()) {
                canvas = surfaceHolder.lockCanvas();
                controller.updateEmoticonCoordinates();
                drawBoard(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void drawBoard(Canvas canvas) {
        if (!controller.isGameEnded()) {
            canvas.drawBitmap(gridBitmap, ZERO, ZERO, null); // Draws background
            canvas.drawRect(highlightSelectionRect, selectionFill);
            canvas.drawRect(highlightSelectionRect, gridLineColour);

            for (int y = Y_MAX - 1; y >= 0; y--) {
                for (int x = 0; x < X_MAX; x++) {
                    Emoticon e = emoticons[x][y];

                    if (e.isPartOfMatch() || e.isSelected()) {
                        int emoToHighlightX = e.getViewPositionX();
                        int emoToHighlightY = e.getViewPositionY();

                        highlightMatchRect.set(emoToHighlightX, emoToHighlightY, (emoToHighlightX + emoWidth), (emoToHighlightY + emoHeight));
                        canvas.drawRect(highlightMatchRect, selectionFill);
                        canvas.drawRect(highlightMatchRect, gridLineColour);
                    }
                    canvas.drawBitmap(e.getBitmap(), 5 + e.getViewPositionX(), e.getViewPositionY(), null);
                }
            }
        } else {
            canvas.drawRect(ZERO, ZERO, getWidth(), getHeight(), boardColour);
            gridLineColour.setTextSize(80);
            gridLineColour.setStyle(Paint.Style.FILL);
            gridLineColour.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Game Over", (emoWidth * 4), 100, gridLineColour);
            canvas.drawText("Tap to Play Again!", (emoWidth * 4), 300, gridLineColour);
            gridLineColour.setStyle(Paint.Style.STROKE);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "in GameBoardView onTouchEvent(MotionEvent)");
        controller.handle(event);
        return true;
    }
}