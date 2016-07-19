package com.example.mark.msccomputerscienceproject;

import android.graphics.Bitmap;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science final project
 */
public class EmoticonImplTest {

    @Mock
    Bitmap bitmap;

    @Test
    public void testReturnEmoticonType() throws Exception {
        Emoticon emo = new EmoticonImpl(3, 4, 20, 20, bitmap, "HAPPY", 10);
        assertEquals(emo.getEmoticonType(), "HAPPY");
    }
}