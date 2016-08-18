package com.example.mark.msccomputerscienceproject.TestPackage;

import com.example.mark.msccomputerscienceproject.model.Emoticon;
import com.example.mark.msccomputerscienceproject.model.GamePiece;

import android.graphics.Bitmap;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science final project
 */
public class EmoticonTest {

    @Mock
    Bitmap bitmap;

    @Test
    public void testReturnEmoticonType() throws Exception {
        final int emoWidth = 20;
        final int emoHeight = 20;
        final String HAPPY = "HAPPY";
        final int offScreenStartPositionY = -10;
        GamePiece emo = new Emoticon(3, 4, emoWidth, emoHeight, bitmap, HAPPY, offScreenStartPositionY);
        assertEquals("HAPPY", emo.getEmoType());
    }
}