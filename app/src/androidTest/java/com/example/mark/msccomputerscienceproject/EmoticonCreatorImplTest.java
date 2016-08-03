package com.example.mark.msccomputerscienceproject;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.mark.msccomputerscienceproject.emoticon_populator.BitmapCreator;
import com.example.mark.msccomputerscienceproject.emoticon_populator.EmoticonCreatorImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science final project
 */
public class EmoticonCreatorImplTest {

    private int emoWidth;
    private int emoHeight;
    private BitmapCreator bitmapCreator;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        emoWidth = 20;
        emoHeight = 20;
        bitmapCreator = BitmapCreator.getInstance();
        bitmapCreator.prepareScaledBitmaps(context, emoWidth, emoHeight);
    }

    @After
    public void tearDown() throws Exception {
        bitmapCreator = null;
    }

    @Test
    public void testGenerateEmoticon() throws Exception {
        int x = 3;
        int y = 3;
        int offScreenStartPositionY = 10;
        EmoticonCreatorImpl emoCreator = new EmoticonCreatorImpl(bitmapCreator, emoWidth, emoHeight);
        Emoticon emo = emoCreator.createRandomEmoticon(x, y, offScreenStartPositionY);
        assertThat(emo, instanceOf(Emoticon.class));
    }

}