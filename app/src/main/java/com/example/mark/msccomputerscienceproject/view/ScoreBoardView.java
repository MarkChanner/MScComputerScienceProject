package com.example.mark.msccomputerscienceproject.view;

import com.example.mark.msccomputerscienceproject.R;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.graphics.*;
import android.widget.TextView;
import android.util.TypedValue;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class ScoreBoardView extends TextView {

    public static final int ZERO = 0;
    private Bitmap scoreBitmap;
    private Paint paint;
    private int scorePositionX;
    private int scorePositionY;
    private int score;

    public ScoreBoardView(Context context, int viewSizeX, int viewSizeY) {
        super(context);
        prepareCanvas(context, viewSizeX, viewSizeY);
    }

    public ScoreBoardView(Context context) {
        super(context);
    }

    private void prepareCanvas(Context context, int viewSizeX, int viewSizeY) {
        scoreBitmap = Bitmap.createBitmap(viewSizeX, viewSizeY, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(scoreBitmap);

        drawBackgroundColour(context, canvas, viewSizeX, viewSizeY);
        drawBorder(canvas, viewSizeX, viewSizeY);
        drawScoreTitle(context, canvas, viewSizeX, viewSizeY);

        scorePositionX = (viewSizeX / 2);
        scorePositionY = (viewSizeY / 2);
        invalidate();
    }

    private void drawBackgroundColour(Context context, Canvas canvas, int viewSizeX, int viewSizeY) {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(context, R.color.scoreboard));
        canvas.drawRect(ZERO, ZERO, viewSizeX, viewSizeY, paint);
    }

    private void drawBorder(Canvas canvas, int viewSizeX, int viewSizeY) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3f);
        paint.setColor(Color.BLACK);
        canvas.drawRect(ZERO, ZERO, viewSizeX, viewSizeY, paint);
    }

    private void drawScoreTitle(Context context, Canvas canvas, int viewSizeX, int viewSizeY) {
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        float scale = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, context.getResources().getDisplayMetrics());
        paint.setTextSize(scale);
        canvas.drawText("SCORE", (viewSizeX / 2), (viewSizeY / 4), paint);
        scale = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30, context.getResources().getDisplayMetrics());
        paint.setTextSize(scale);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(scoreBitmap, ZERO, ZERO, null);
        canvas.drawText("" + score, scorePositionX, scorePositionY, paint);
    }

    public void incrementScore(int points) {
        score += points;
        invalidate();
    }
}
