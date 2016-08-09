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
    private int pointsX;
    private int pointsY;
    private int score;

    public ScoreBoardView(Context context, int viewX, int viewY) {
        super(context);
        prepareCanvas(context, viewX, viewY);
    }

    private void prepareCanvas(Context context, int viewX, int viewY) {
        scoreBitmap = Bitmap.createBitmap(viewX, viewY, Bitmap.Config.RGB_565);
        Canvas tempCanvas = new Canvas(scoreBitmap);

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(context, R.color.scoreboard));
        tempCanvas.drawRect(ZERO, ZERO, viewX, viewY, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3f);
        paint.setColor(Color.BLACK);
        tempCanvas.drawRect(ZERO, ZERO, viewX, viewY, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        float scale = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, context.getResources().getDisplayMetrics());
        paint.setTextSize(scale);
        tempCanvas.drawText("SCORE", (viewX / 2), (viewY / 4), paint);

        scale = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30, context.getResources().getDisplayMetrics());
        paint.setTextSize(scale);
        pointsX = (viewX / 2);
        pointsY = (viewY / 2);
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(scoreBitmap, ZERO, ZERO, null);
        canvas.drawText("" + score, pointsX, pointsY, paint);
    }

    public void incrementScore(int points) {
        score += points;
        invalidate();
    }
}
