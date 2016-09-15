package com.example.mark.msccomputerscienceproject.view;

import com.example.mark.msccomputerscienceproject.controller.GameActivity;
import com.example.mark.msccomputerscienceproject.controller.GameActivityImpl;
import com.example.mark.msccomputerscienceproject.model.Board;
import com.example.mark.msccomputerscienceproject.model.BoardImpl;
import com.example.mark.msccomputerscienceproject.model.GamePiece;
import com.example.mark.msccomputerscienceproject.R;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.graphics.*;
import android.view.*;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class GameBoardView extends SurfaceView implements Runnable {

    private static final int ROWS = GameActivityImpl.MAX_ROWS;
    private static final int COLUMNS = GameActivityImpl.MAX_COLUMNS;
    private static final int ZERO = 0;
    private final Rect matchRect = new Rect();
    private int viewSizeX;

    private SurfaceHolder surfaceHolder;
    private GameActivity controller;
    private Board board;
    private int tileWidth;
    private int tileHeight;
    private Paint backgroundColour;
    private Paint drawingLine;
    private Paint borderColour;
    private Paint selectionFill;

    private Bitmap gridBitmap;

    private Thread gameViewThread = null;
    volatile boolean running = false;

    public GameBoardView(Context context, int viewSizeX, int viewSizeY, int tileWidth, int tileHeight) {
        super(context);
        this.viewSizeX = viewSizeX;
        this.surfaceHolder = getHolder();
        this.controller = (GameActivity) context;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.board = BoardImpl.getInstance();
        setPaint(context);
        createBackgroundBitmap(viewSizeX, viewSizeY);
    }

    public GameBoardView(Context controller) {
        super(controller);
    }

    private void setPaint(Context context) {
        backgroundColour = new Paint();
        backgroundColour.setColor(ContextCompat.getColor(context, R.color.gameboard));

        selectionFill = new Paint();
        selectionFill.setColor(ContextCompat.getColor(context, R.color.highlightbackground));

        drawingLine = new Paint();
        drawingLine.setColor(Color.BLACK);
        drawingLine.setStyle(Paint.Style.STROKE);
        drawingLine.setStrokeWidth(2f);

        borderColour = new Paint();
        borderColour.setColor(ContextCompat.getColor(context, R.color.border));
        borderColour.setStyle(Paint.Style.STROKE);
        borderColour.setStrokeWidth(8f);
    }

    private void createBackgroundBitmap(int viewSizeX, int viewSizeY) {
        gridBitmap = Bitmap.createBitmap(viewSizeX, viewSizeY, Bitmap.Config.RGB_565);
        Canvas gridCanvas = new Canvas(gridBitmap);

        drawBackgroundColour(gridCanvas, viewSizeX, viewSizeY);
        drawHorizontal(gridCanvas, viewSizeX);
        drawVertical(gridCanvas, viewSizeY);
        drawBorder(gridCanvas, viewSizeX, viewSizeY);

        gridCanvas.drawBitmap(gridBitmap, ZERO, ZERO, null);
    }

    private void drawBackgroundColour(Canvas gridCanvas, int viewSizeX, int viewSizeY) {
        gridCanvas.drawRect(ZERO, ZERO, viewSizeX, viewSizeY, backgroundColour);
    }

    private void drawHorizontal(Canvas gridCanvas, int viewSizeX) {
        for (int i = 0; i < ROWS; i++) {
            gridCanvas.drawLine(ZERO, i * tileHeight, viewSizeX, i * tileHeight, drawingLine);
        }
    }

    private void drawVertical(Canvas gridCanvas, int viewSizeY) {
        for (int i = 0; i < COLUMNS; i++) {
            gridCanvas.drawLine(i * tileWidth, ZERO, i * tileWidth, viewSizeY, drawingLine);
        }
    }

    private void drawBorder(Canvas gridCanvas, int viewSizeX, int viewSizeY) {
        gridCanvas.drawRect(ZERO, ZERO, viewSizeX, viewSizeY, borderColour);
    }

    public void control(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
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
        running = true;
        gameViewThread = new Thread(this);
        gameViewThread.start();
    }

    @Override
    public void run() {
        Canvas canvas;
        while (running) {
            if (surfaceHolder.getSurface().isValid()) {
                canvas = surfaceHolder.lockCanvas();
                controller.updateModel();
                drawGameBoard(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void drawGameBoard(Canvas canvas) {
        if (controller.gameOver()) {
            displayGameOverMessage(canvas);
        } else {
            drawBackground(canvas);
            drawHighlightedTiles(canvas);
            drawEmoticons(canvas);
        }
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(gridBitmap, ZERO, ZERO, null);
    }

    private void drawHighlightedTiles(Canvas canvas) {
        for (int y = ROWS - 1; y >= 0; y--) {
            for (int x = 0; x < COLUMNS; x++) {
                GamePiece emo = board.getGamePiece(x, y);
                if (emo.getHighlight()) {
                    int highlightX = emo.getViewPositionX();
                    int highlightY = emo.getViewPositionY();
                    matchRect.set(highlightX, highlightY, (highlightX + tileWidth), (highlightY + tileHeight));
                    canvas.drawRect(matchRect, selectionFill);
                    canvas.drawRect(matchRect, drawingLine);
                }
            }
        }
    }

    private void drawEmoticons(Canvas canvas) {
        GamePiece emo;
        for (int y = ROWS - 1; y >= 0; y--) {
            for (int x = 0; x < COLUMNS; x++) {
                emo = board.getGamePiece(x, y);
                canvas.drawBitmap(emo.getBitmap(), 5 + emo.getViewPositionX(), emo.getViewPositionY(), null);
            }
        }
    }

    private void displayGameOverMessage(Canvas canvas) {
        canvas.drawRect(ZERO, ZERO, getWidth(), getHeight(), backgroundColour);
        drawingLine.setTextSize(80);
        drawingLine.setStyle(Paint.Style.FILL);
        drawingLine.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("NO MORE MATCHES!", viewSizeX / 2, 100, drawingLine);
        canvas.drawText("TOUCH THE SCREEN", viewSizeX / 2, 300, drawingLine);
        drawingLine.setStyle(Paint.Style.STROKE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controller.handleTouch(event);
        return true;
    }
}