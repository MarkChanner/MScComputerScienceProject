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

    public static final String INVALID_MOVE = "INVALID_MOVE";
    public static final String MATCH_FOUND = "MATCH_FOUND";
    public static final String MIXED_EMOTIONS = "MIXED_EMOTIONS";

    public static final String ANGRY = "ANGRY";
    public static final String HAPPY = "HAPPY";
    public static final String EMBARRASSED = "EMBARRASSED";
    public static final String SURPRISED = "SURPRISED";
    public static final String SAD = "SAD";

    public static final String ANGRY2 = "ANGRY2";
    public static final String HAPPY2 = "HAPPY2";
    public static final String EMBARRASSED2 = "EMBARRASSED2";
    public static final String SURPRISED2 = "SURPRISED2";
    public static final String SAD2 = "SAD2";

    /*public static final String AFRAID = "AFRAID";
    public static final String CRYING = "CRYING";
    public static final String GRUMPY = "GRUMPY";
    public static final String INNOCENT = "INNOCENT";
    public static final String TIRED = "TIRED";*/

    /**
     * Try to keep the sound small and at a low bitrate
     */
    private SoundPool soundPool;
    private int swap_back = -1;
    private int matchFoundID = -1;
    private int angryID = -1;
    private int happyID = -1;
    private int embarrassedID = -1;
    private int surprisedID = -1;
    private int sadID = -1;

    private int angry2ID = -1;
    private int happy2ID = -1;
    private int embarrassed2ID = -1;
    private int surprised2ID = -1;
    private int sad2ID = -1;

    /*private int afraidID = -1;
    private int cryingID = -1;
    private int grumpyID = -1;
    private int innocentID = -1;
    private int tiredID = -1;*/

    private int mixedEmotionsID = -1;

    public void loadSound(Context context) {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Second parameter specifies priority of sound effect
            descriptor = assetManager.openFd("match_found.ogg");
            matchFoundID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("swap_back.ogg");
            swap_back = soundPool.load(descriptor, 0);

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

            descriptor = assetManager.openFd("angry2.ogg");
            angry2ID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("happy2.ogg");
            happy2ID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("embarrassed2.ogg");
            embarrassed2ID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("surprised2.ogg");
            surprised2ID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("sad2.ogg");
            sad2ID = soundPool.load(descriptor, 0);


            /*descriptor = assetManager.openFd("afraid.ogg");
            afraidID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("crying.ogg");
            cryingID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("grumpy.ogg");
            grumpyID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("innocent.ogg");
            innocentID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("tired.ogg");
            tiredID = soundPool.load(descriptor, 0);*/

            descriptor = assetManager.openFd("mixed_emotions.ogg");
            mixedEmotionsID = soundPool.load(descriptor, 0);

        } catch (IOException e) {
            Log.e("Error", "sound file failed to load!");
        }
    }

    public void announceMatchedEmoticons(ArrayList<LinkedList<Emoticon>> matchingX, ArrayList<LinkedList<Emoticon>> matchingY) {
        //Log.d(TAG, "in PlayMatchedEmoticons method");
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
                    //Log.d(TAG, "return TRUE from mixedEmoticonsSAMEDirection");
                    return true;
                }
            }
        }
        //Log.d(TAG, "return FALSE from mixedEmoticonsSAMEDirection");
        return false;
    }

    private boolean mixedEmotionsCrossDirection(ArrayList<LinkedList<Emoticon>> matchingX, ArrayList<LinkedList<Emoticon>> matchingY) {
        if (!(matchingX.isEmpty() || matchingY.isEmpty())) {
            String emoTypeMarker = matchingX.get(0).getFirst().getEmoticonType();
            for (int i = 0; i < matchingY.size(); i++) {
                if (!emoTypeMarker.equals(matchingY.get(i).getFirst().getEmoticonType())) {
                    //Log.d(TAG, "return TRUE from mixedEmoticonsCROSSDirection");
                    return true;
                }
            }
        }
       // Log.d(TAG, "return FALSE from mixedEmoticonsSameDirection");
        return false;
    }

    public void playSound(String sound) {
        switch (sound) {
            case INVALID_MOVE:
                soundPool.play(swap_back, 1, 1, 0, 0, 1);
                break;

            case MATCH_FOUND:
                soundPool.play(matchFoundID, 1, 1, 0, 0, 1);
                break;

            case ANGRY:
                soundPool.play(angryID, 1, 1, 0, 0, 1);
                break;

            case HAPPY:
                soundPool.play(happyID, 1, 1, 0, 0, 1);
                break;

            case EMBARRASSED:
                soundPool.play(embarrassedID, 1, 1, 0, 0, 1);
                break;

            case SURPRISED:
                soundPool.play(surprisedID, 1, 1, 0, 0, 1);
                break;

            case SAD:
                soundPool.play(sadID, 1, 1, 0, 0, 1);
                break;

            case ANGRY2:
                soundPool.play(angry2ID, 1, 1, 0, 0, 1);
                break;

            case HAPPY2:
                soundPool.play(happy2ID, 1, 1, 0, 0, 1);
                break;

            case EMBARRASSED2:
                soundPool.play(embarrassed2ID, 1, 1, 0, 0, 1);
                break;

            case SURPRISED2:
                soundPool.play(surprised2ID, 1, 1, 0, 0, 1);
                break;

            case SAD2:
                soundPool.play(sad2ID, 1, 1, 0, 0, 1);
                break;

            /*case AFRAID:
                soundPool.play(afraidID, 1, 1, 0, 0, 1);
                break;

            case CRYING:
                soundPool.play(cryingID, 1, 1, 0, 0, 1);
                break;

            case GRUMPY:
                soundPool.play(grumpyID, 1, 1, 0, 0, 1);
                break;

            case INNOCENT:
                soundPool.play(innocentID, 1, 1, 0, 0, 1);
                break;

            case TIRED:
                soundPool.play(tiredID, 1, 1, 0, 0, 1);
                break;*/

            case MIXED_EMOTIONS:
                soundPool.play(mixedEmotionsID, 1, 1, 0, 0, 1);
                break;
        }
    }
}