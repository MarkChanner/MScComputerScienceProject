package com.example.mark.msccomputerscienceproject.TestPackage;

import android.graphics.Bitmap;

import com.example.mark.msccomputerscienceproject.Emoticon;
import com.example.mark.msccomputerscienceproject.EmoticonImpl;

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
        Emoticon emo = new EmoticonImpl(3, 4, 20, 20, bitmap, "HAPPY", 10);
        assertEquals("HAPPY", emo.getEmoticonType());
    }
}