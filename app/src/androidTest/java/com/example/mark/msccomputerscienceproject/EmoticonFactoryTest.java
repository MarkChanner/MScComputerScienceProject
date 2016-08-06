package com.example.mark.msccomputerscienceproject;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.mark.msccomputerscienceproject.emoticon_populator.AbstractEmoticonFactory;
import com.example.mark.msccomputerscienceproject.emoticon_populator.BitmapCreator;
import com.example.mark.msccomputerscienceproject.emoticon_populator.EmoticonFactoryLevel01;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science final project
 */
public class EmoticonFactoryTest {

    private Context context;
    private int emoWidth;
    private int emoHeight;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        emoWidth = 20;
        emoHeight = 20;
    }

    @Test
    public void testGenerateEmoticon() throws Exception {
        int x = 3;
        int y = 3;
        int offScreenStartPositionY = 10;
        BitmapCreator bc = BitmapCreator.getInstance();
        AbstractEmoticonFactory emoCreator = new EmoticonFactoryLevel01(bc, emoWidth, emoHeight);
        Emoticon emo = emoCreator.createRandomEmoticon(x, y, offScreenStartPositionY);
        assertThat(emo, instanceOf(Emoticon.class));
    }

}