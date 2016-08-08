package com.example.mark.msccomputerscienceproject;

import com.example.mark.msccomputerscienceproject.model.*;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science final project
 */
public class EmoticonFactoryTest {

    /*private EmoticonCreatorFactory emoCreatorFactory;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        int emoWidth = 20;
        int emoHeight = 20;
        BitmapCreator bitmapCreator = BitmapCreator.getInstance();
        bitmapCreator.prepareScaledBitmaps(context, emoWidth, emoHeight);
        emoCreatorFactory = new EmoticonCreatorFactory(bitmapCreator, emoWidth, emoHeight);
    }

    @Test
    public void testGenerateEmoticon() throws Exception {
        int level = 1;
        int x = 3;
        int y = 3;
        int offScreenStartPositionY = 10;
        AbstractEmoticonCreator emoCreator = emoCreatorFactory.getEmoticonCreator(level);
        Emoticon emo = emoCreator.createRandomEmoticon(x, y, offScreenStartPositionY);
        assertThat(emo, instanceOf(Emoticon.class));
    }*/
}