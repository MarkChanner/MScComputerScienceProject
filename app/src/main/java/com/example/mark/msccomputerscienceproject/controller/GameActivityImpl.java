package com.example.mark.msccomputerscienceproject.controller;

import com.example.mark.msccomputerscienceproject.model.*;
import com.example.mark.msccomputerscienceproject.view.*;
import com.example.mark.msccomputerscienceproject.sound.*;
import com.example.mark.msccomputerscienceproject.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.graphics.Point;
import android.view.Display;
import android.view.ViewGroup;
import android.view.MotionEvent;
import android.util.Log;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public final class GameActivityImpl extends Activity implements GameActivity {

    private final static String TAG = "GameControllerImpl";
    public static final int MAX_ROWS = 7;
    public static final int MAX_COLUMNS = 8;

    private MusicPlayer music;
    private SoundManager soundManager;
    private GameModel gameModel;
    private GameBoardView gameBoardView;
    private ScoreBoardView scoreBoardView;
    private int tileWidth;
    private int tileHeight;
    volatile boolean gameEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        LinearLayout screenLayout = (LinearLayout) findViewById(R.id.gameLayout);

        this.music = new MusicPlayer(this);
        this.soundManager = new SoundManager();
        this.soundManager.loadSound(this);

        // Gets screen dimensions and uses them to specify View and Bitmap dimensions
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        int screenSizeX = (size.x - ((screenLayout.getPaddingLeft() * 2) + screenLayout.getPaddingRight()));
        int gameBoardViewSizeX = (int) (screenSizeX * 0.9);
        int gameBoardViewSizeY = (size.y - (screenLayout.getPaddingTop() + screenLayout.getPaddingBottom()));
        int scoreBoardViewSizeX = (int) (screenSizeX * 0.1);
        int scoreBoardViewSizeY = (gameBoardViewSizeY / 3);
        this.tileWidth = gameBoardViewSizeX / MAX_COLUMNS;
        this.tileHeight = gameBoardViewSizeY / MAX_ROWS;

        // Instantiates Model and View objects
        BitmapCreator bitmapCreator = BitmapCreator.getInstance();
        bitmapCreator.prepareScaledBitmaps(this, tileWidth, tileHeight);
        int level = 1;
        LevelManager levelManager = new LevelManagerImpl(tileWidth, tileHeight, level);
        this.gameModel = new GameModelImpl(this, levelManager);
        this.gameBoardView = new GameBoardView(this, gameBoardViewSizeX, gameBoardViewSizeY, tileWidth, tileHeight);
        this.scoreBoardView = new ScoreBoardView(this, scoreBoardViewSizeX, scoreBoardViewSizeY);

        // Sets layout of Views
        LinearLayout.LayoutParams boardParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(gameBoardViewSizeX, gameBoardViewSizeY));
        boardParams.setMargins(screenLayout.getPaddingLeft(), 0, gameBoardViewSizeX, 0);
        LinearLayout.LayoutParams scoreParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(scoreBoardViewSizeX, scoreBoardViewSizeY));

        // ScoreBoardView must be added to Layout before GameBoardView
        screenLayout.addView(scoreBoardView, scoreParams);
        screenLayout.addView(gameBoardView, boardParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        music.startMediaPlayer();
        gameBoardView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        music.pauseMediaPlayer();
        if (isFinishing()) {
            music.stopMediaPlayer();
            music.releaseMediaPlayer();
        }
        gameBoardView.pause();
    }

    @Override
    public void handleTouch(MotionEvent event) {
        Log.d(TAG, "handleTouch(MotionEvent)");
        int screenX = (int) event.getX();
        int screenY = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!gameOver()) {
                gameModel.handleSelection(screenX / tileWidth, screenY / tileHeight);
            } else {
                gameEnded = false;
                gameModel.resetGame();
            }
        }
    }

    @Override
    public void updateModel() {
        gameModel.updateLogic();
    }

    @Override
    public void controlGameBoardView(int second) {
        gameBoardView.control(second);
    }

    @Override
    public void updateScoreBoardView(int points) {
        scoreBoardView.incrementScore(points);
    }

    @Override
    public void playSound(MatchContainer matchContainer) {
        soundManager.announceMatchedEmoticons(matchContainer);
    }

    @Override
    public void playSound(String sound) {
        soundManager.playSound(sound);
    }

    @Override
    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    @Override
    public boolean gameOver() {
        return gameEnded;
    }
}