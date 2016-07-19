package com.example.mark.msccomputerscienceproject;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science final project
 */
public class EmoticonFactoryTest {

    private int x;
    private int y;
    private int emoWidth;
    private int emoHeight;
    private String emoID;
    private int offScreenStartPositionY;

    @Mock
    Bitmap bitmap;

    @Before
    public void setUp() throws Exception {
        x = 2;
        y = 3;
        emoWidth = 20;
        emoHeight = 20;
        emoID = "HAPPY";
        offScreenStartPositionY = 10;
    }

    @Test
    public void testCreateEmoticon() throws Exception {
        Emoticon emo = new EmoticonImpl(x, y, emoWidth, emoHeight, bitmap, emoID, offScreenStartPositionY);
        assertEquals(emo.getEmoticonType(), "HAPPY");
    }
}