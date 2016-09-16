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

    private int tileWidth;
    private int tileHeight;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        tileWidth = 20;
        tileHeight = 20;
        BitmapCreator bitmapCreator = BitmapCreator.getInstance();
        bitmapCreator.prepareScaledBitmaps(context, tileWidth, tileHeight);
    }

    @Test
    public void testGenerateEmoticon() throws Exception {
        int x = 3;
        int y = 3;
        int offScreenStartPositionY = 10;
        GamePieceFactory emoFactory01 = new EmoticonFactoryLevel01(tileWidth, tileHeight);
        GamePieceFactory emoFactory02 = new EmoticonFactoryLevel01(tileWidth, tileHeight);
        GamePiece emo01 = emoFactory01.createRandomGamePiece(x, y, offScreenStartPositionY);
        GamePiece emo02 = emoFactory02.createRandomGamePiece(x, y, offScreenStartPositionY);
        assertThat(emo01, instanceOf(Emoticon.class));
        assertThat(emo02, instanceOf(Emoticon.class));
    }
}