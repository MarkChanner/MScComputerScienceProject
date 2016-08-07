package com.example.mark.msccomputerscienceproject.sound;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class MusicPlayer {

    private final static String TAG = "MusicPlayer";
    private MediaPlayer mediaPlayer;

    public MusicPlayer(Context context) {
        mediaPlayer = new MediaPlayer();
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor musicDescriptor = assetManager.openFd("shroom_ridge.ogg");
            mediaPlayer.setDataSource(musicDescriptor.getFileDescriptor(),
                    musicDescriptor.getStartOffset(), musicDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            mediaPlayer = null;
            Log.e(TAG, "IOException in MusicPlayer constructor: " + e);
            e.printStackTrace();
        }
    }

    public void startMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void pauseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stopMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}