package com.example.mark.msccomputerscienceproject;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.mark.msccomputerscienceproject.emoticon_populator.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class GameControllerImpl extends Activity implements GameController {

    private final static String TAG = "GameControllerImpl";
    public static final int X_MAX = 8;
    public static final int Y_MAX = 7;

    private MusicPlayer music;
    private SoundManager soundManager;
    private int emoWidth;
    private int emoHeight;
    private GameModel gameModel;
    private ScoreBoardView scoreBoardView;
    private GameBoardViewImpl gameBoardView;

    volatile boolean gameEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        LinearLayout screenLayout = (LinearLayout) findViewById(R.id.gameLayout);

        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);

        this.music = new MusicPlayer(this);
        this.soundManager = new SoundManager();
        this.soundManager.loadSound(this);

        int screenSizeX = (size.x - ((screenLayout.getPaddingLeft() * 2) + screenLayout.getPaddingRight()));
        int gameBoardViewSizeX = (int) (screenSizeX * 0.9);
        int gameBoardViewSizeY = (size.y - (screenLayout.getPaddingTop() + screenLayout.getPaddingBottom()));
        int scoreBoardViewSizeX = (int) (screenSizeX * 0.1);
        int scoreBoardViewSizeY = (gameBoardViewSizeY / 3);
        this.emoWidth = gameBoardViewSizeX / X_MAX;
        this.emoHeight = gameBoardViewSizeY / Y_MAX;

        Emoticon[][] emoticons = new AbstractEmoticon[X_MAX][Y_MAX];
        BitmapCreator bitmapCreator = BitmapCreator.getInstance();
        bitmapCreator.prepareScaledBitmaps(this, emoWidth, emoHeight);
        EmoticonCreator emoCreator = new EmoticonCreatorImpl(bitmapCreator, emoWidth, emoHeight);
        GridPopulator populator = new GridPopulatorImpl(emoCreator);
        populator.populateBoard(emoticons);
        this.gameModel = new GameModelImpl(this, populator, new MatchFinder(), emoticons, emoWidth, emoHeight);

        this.scoreBoardView = new ScoreBoardView(this, scoreBoardViewSizeX, gameBoardViewSizeY / 3);
        this.gameBoardView = new GameBoardViewImpl(this, emoticons, gameBoardViewSizeX, gameBoardViewSizeY, emoWidth, emoHeight);
        LinearLayout.LayoutParams boardParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(gameBoardViewSizeX, gameBoardViewSizeY));
        boardParams.setMargins(screenLayout.getPaddingLeft(), 0, gameBoardViewSizeX, 0);
        LinearLayout.LayoutParams scoreParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(scoreBoardViewSizeX, scoreBoardViewSizeY));
        screenLayout.addView(scoreBoardView, scoreParams);
        screenLayout.addView(gameBoardView, boardParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Log.d(TAG, "in onResume()");
        music.startMediaPlayer();
        gameBoardView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Log.d(TAG, "in onPause()");
        music.pauseMediaPlayer();
        if (isFinishing()) {
            music.stopMediaPlayer();
            music.releaseMediaPlayer();
        }
        gameBoardView.pause();
    }

    @Override
    public void handle(MotionEvent event) {
        Log.d(TAG, "handle(MotionEvent");
        int screenX = (int) event.getX();
        int screenY = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!isGameEnded()) {
                gameModel.handleSelection(screenX / emoWidth, screenY / emoHeight);
            } else {
                gameEnded = false;
                gameModel.resetBoard();
            }
        }
    }

    @Override
    public void updateEmoticonCoordinates() {
        gameModel.updateEmoticonSwapCoordinates();
        gameModel.updateEmoticonDropCoordinates();
    }

    @Override
    public void incrementScoreView(int points) {
        scoreBoardView.incrementScore(points);
    }

    @Override
    public void control(int second) {
        gameBoardView.control(second);
    }

    @Override
    public void playSound(ArrayList<LinkedList<Emoticon>> matchingX, ArrayList<LinkedList<Emoticon>> matchingY) {
        soundManager.announceMatchedEmoticons(matchingX, matchingY);
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
    public boolean isGameEnded() {
        return gameEnded;
    }
}