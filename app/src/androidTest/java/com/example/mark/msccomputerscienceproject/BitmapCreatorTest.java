package com.example.mark.msccomputerscienceproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;

import com.example.mark.msccomputerscienceproject.emoticon_populator.BitmapCreator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science final project
 */
public class BitmapCreatorTest {

    private BitmapCreator bitmapCreator;
    private int emoWidth = 20;
    private int emoHeight = 20;

    @Before
    public void setUp() throws Exception {
        bitmapCreator = BitmapCreator.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        bitmapCreator = null;
    }

    @Test
    public void testGetInstance() throws Exception {
        assertThat(bitmapCreator, instanceOf(BitmapCreator.class));
    }

    @Test
    public void testPrepareScaledBitmaps() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        bitmapCreator.prepareScaledBitmaps(context, emoWidth, emoHeight);
        assertNotNull(bitmapCreator.getAngryBitmap());
    }

    @Test
    public void testGetAngryBitmap() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        bitmapCreator.prepareScaledBitmaps(context, emoWidth, emoHeight);
        Bitmap angryBitmap1 = bitmapCreator.getAngryBitmap();

        Bitmap unscaledBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.angry);
        Bitmap angryBitmap2 = Bitmap.createScaledBitmap(unscaledBitmap, emoWidth, emoHeight, false);
        assertTrue(angryBitmap1.sameAs(angryBitmap2));
    }
}