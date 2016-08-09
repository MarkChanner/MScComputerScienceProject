package com.example.mark.msccomputerscienceproject.view;

import com.example.mark.msccomputerscienceproject.controller.*;
import com.example.mark.msccomputerscienceproject.model.GameBoard;
import com.example.mark.msccomputerscienceproject.model.Emoticon;
import com.example.mark.msccomputerscienceproject.R;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.graphics.*;
import android.view.*;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class GameBoardView extends SurfaceView implements Runnable {

    public static final String TAG = "GameBoardView";
    public static final int X_MAX = GameControllerImpl.X_MAX;
    public static final int Y_MAX = GameControllerImpl.Y_MAX;
    public static final int ZERO = 0;

    private SurfaceHolder surfaceHolder;
    private GameController controller;
    private GameBoard board;
    private int emoWidth;
    private int emoHeight;

    private final Rect selectionRect = new Rect();
    private final Rect matchRect = new Rect();
    private Paint gameBoardColour;
    private Paint lineColour;
    private Paint borderColour;
    private Paint selectionFill;

    private Bitmap gridBitmap;

    private Thread gameViewThread = null;
    volatile boolean running = false;

    public GameBoardView(Context controller) {
        super(controller);
    }

    public GameBoardView(Context context, GameBoard board, int viewSizeX, int viewSizeY, int emoWidth, int emoHeight) {
        super(context);
        this.surfaceHolder = getHolder();
        this.controller = (GameController) context;
        this.board = board;
        this.emoWidth = emoWidth;
        this.emoHeight = emoHeight;
        setPaint(context);
        createBackgroundBitmap(viewSizeX, viewSizeY);
    }

    private void setPaint(Context context) {
        gameBoardColour = new Paint();
        gameBoardColour.setColor(ContextCompat.getColor(context, R.color.gameboard));

        selectionFill = new Paint();
        selectionFill.setColor(ContextCompat.getColor(context, R.color.highlightbackground));

        lineColour = new Paint();
        lineColour.setColor(Color.BLACK);
        lineColour.setStyle(Paint.Style.STROKE);
        lineColour.setStrokeWidth(2f);

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
        gridCanvas.drawRect(ZERO, ZERO, viewSizeX, viewSizeY, gameBoardColour);
    }

    private void drawHorizontal(Canvas gridCanvas, int viewSizeX) {
        for (int i = 0; i < Y_MAX; i++) {
            gridCanvas.drawLine(ZERO, i * emoHeight, viewSizeX, i * emoHeight, lineColour);
        }
    }

    private void drawVertical(Canvas gridCanvas, int viewSizeY) {
        for (int i = 0; i < X_MAX; i++) {
            gridCanvas.drawLine(i * emoWidth, ZERO, i * emoWidth, viewSizeY, lineColour);
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
            highlightAnySelections(canvas);
            highlightAnyMatches(canvas);
        }

    }

    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(gridBitmap, ZERO, ZERO, null);
    }

    private void highlightAnySelections(Canvas canvas) {
        canvas.drawRect(selectionRect, selectionFill);
        canvas.drawRect(selectionRect, lineColour);
    }

    private void highlightAnyMatches(Canvas canvas) {
        for (int y = Y_MAX - 1; y >= 0; y--) {
            for (int x = 0; x < X_MAX; x++) {
                Emoticon emo = board.getGamePiece(x, y);

                if (emo.isPartOfMatch() || emo.isSelected()) {
                    int highlightX = emo.getViewPositionX();
                    int highlightY = emo.getViewPositionY();
                    matchRect.set(highlightX, highlightY, (highlightX + emoWidth), (highlightY + emoHeight));
                    canvas.drawRect(matchRect, selectionFill);
                    canvas.drawRect(matchRect, lineColour);
                }
                canvas.drawBitmap(emo.getBitmap(), 5 + emo.getViewPositionX(), emo.getViewPositionY(), null);
            }
        }
    }

    private void displayGameOverMessage(Canvas canvas) {
        canvas.drawRect(ZERO, ZERO, getWidth(), getHeight(), gameBoardColour);
        lineColour.setTextSize(80);
        lineColour.setStyle(Paint.Style.FILL);
        lineColour.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Game Over", (emoWidth * 4), 100, lineColour);
        canvas.drawText("Tap to Play Again!", (emoWidth * 4), 300, lineColour);
        lineColour.setStyle(Paint.Style.STROKE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controller.handle(event);
        return true;
    }
}