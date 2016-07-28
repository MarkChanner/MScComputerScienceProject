package com.example.mark.msccomputerscienceproject;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class SoundManager {

    private static final String TAG = "SoundManager";

    /**
     * Try to keep the sound small and at a low bitrate
     */
    private SoundPool soundPool;

    private int invalidMoveID = -1;
    private int matchFoundID = -1;
    private int angryID = -1;
    private int happyID = -1;
    private int embarrassedID = -1;
    private int surprisedID = -1;
    private int sadID = -1;
    private int mixedEmotionsID = -1;
    public static final String MIXED_EMOTIONS = "MIXED_EMOTIONS";

    public void loadSound(Context context) {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Second parameter specifies priority of sound effect
            descriptor = assetManager.openFd("match_found.ogg");
            matchFoundID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("swap_back.ogg");
            invalidMoveID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("angry.ogg");
            angryID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("happy.ogg");
            happyID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("embarrassed.ogg");
            embarrassedID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("surprised.ogg");
            surprisedID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("sad.ogg");
            sadID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("mixed_emotions.ogg");
            mixedEmotionsID = soundPool.load(descriptor, 0);

        } catch (IOException e) {
            Log.e("Error", "sound file failed to load!");
        }
    }

    public void announceMatchedEmoticons(ArrayList<LinkedList<Emoticon>> matchingX, ArrayList<LinkedList<Emoticon>> matchingY) {
        Log.d(TAG, "in PlayMatchedEmoticons method");
        if (mixedEmotionsSameDirection(matchingX)
                || mixedEmotionsSameDirection(matchingY)
                || mixedEmotionsCrossDirection(matchingX, matchingY)) {
            playSound(MIXED_EMOTIONS);
        } else if (!matchingX.isEmpty()) {
            playSound(matchingX.get(0).getFirst().getEmoticonType());
        } else if (!matchingY.isEmpty()) {
            playSound(matchingY.get(0).getFirst().getEmoticonType());
        }
    }

    private boolean mixedEmotionsSameDirection(ArrayList<LinkedList<Emoticon>> matchingLine) {
        if (!matchingLine.isEmpty() && matchingLine.size() > 1) {
            String emoTypeMarker = matchingLine.get(0).getFirst().getEmoticonType();
            for (int i = 1; i < matchingLine.size(); i++) {
                if (!matchingLine.get(i).getFirst().getEmoticonType().equals(emoTypeMarker)) {
                    Log.d(TAG, "return TRUE from mixedEmoticonsSAMEDirection");
                    return true;
                }
            }
        }
        Log.d(TAG, "return FALSE from mixedEmoticonsSAMEDirection");
        return false;
    }

    private boolean mixedEmotionsCrossDirection(ArrayList<LinkedList<Emoticon>> matchingX, ArrayList<LinkedList<Emoticon>> matchingY) {
        if (!(matchingX.isEmpty() || matchingY.isEmpty())) {
            String emoTypeMarker = matchingX.get(0).getFirst().getEmoticonType();
            for (int i = 0; i < matchingY.size(); i++) {
                if (!emoTypeMarker.equals(matchingY.get(i).getFirst().getEmoticonType())) {
                    Log.d(TAG, "return TRUE from mixedEmoticonsCROSSDirection");
                    return true;
                }
            }
        }
        Log.d(TAG, "return FALSE from mixedEmoticonsSameDirection");
        return false;
    }

    public void playSound(String sound) {
        switch (sound) {
            case "INVALID_MOVE":
                soundPool.play(invalidMoveID, 1, 1, 0, 0, 1);
                break;

            case "MATCH_FOUND":
                soundPool.play(matchFoundID, 1, 1, 0, 0, 1);
                break;

            case "ANGRY":
                soundPool.play(angryID, 1, 1, 0, 0, 1);
                break;

            case "HAPPY":
                soundPool.play(happyID, 1, 1, 0, 0, 1);
                break;

            case "EMBARRASSED":
                soundPool.play(embarrassedID, 1, 1, 0, 0, 1);
                break;

            case "SURPRISED":
                soundPool.play(surprisedID, 1, 1, 0, 0, 1);
                break;

            case "SAD":
                soundPool.play(sadID, 1, 1, 0, 0, 1);
                break;

            case MIXED_EMOTIONS:
                soundPool.play(mixedEmotionsID, 1, 1, 0, 0, 1);
                break;
        }
    }
}