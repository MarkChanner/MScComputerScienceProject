package com.example.mark.msccomputerscienceproject.model;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science project
 */
public class EmoticonFactoryTest {

    private int emoWidth;
    private int emoHeight;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        emoWidth = 20;
        emoHeight = 20;
        BitmapCreator bitmapCreator = BitmapCreator.getInstance();
        bitmapCreator.prepareScaledBitmaps(context, emoWidth, emoHeight);
    }

    @Test
    public void testGenerateEmoticon() throws Exception {
        int x = 3;
        int y = 3;
        int offScreenStartPositionY = 10;
        GamePieceFactory emoFactory01 = new EmoticonFactoryLevel01(emoWidth, emoHeight);
        GamePieceFactory emoFactory02 = new EmoticonFactoryLevel01(emoWidth, emoHeight);
        GamePiece emo01 = emoFactory01.createRandomGamePiece(x, y, offScreenStartPositionY);
        GamePiece emo02 = emoFactory02.createRandomGamePiece(x, y, offScreenStartPositionY);
        assertThat(emo01, instanceOf(Emoticon.class));
        assertThat(emo02, instanceOf(Emoticon.class));
    }
}