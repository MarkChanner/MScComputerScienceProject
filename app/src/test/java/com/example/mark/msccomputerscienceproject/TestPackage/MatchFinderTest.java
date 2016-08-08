package com.example.mark.msccomputerscienceproject.TestPackage;

import com.example.mark.msccomputerscienceproject.model.*;
import android.graphics.Bitmap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science final project
 */
public class MatchFinderTest {

    /*public static final int X_MAX = GameModelImpl.X_MAX;
    public static final int Y_MAX = GameModelImpl.Y_MAX;
    public static final int ROW_START = 0;
    public static final int COLUMN_TOP = 0;

    private Emoticon[][] emoticons;
    private Bitmap bitmap = BitmapCreator.getInstance().getEmptyBitmap();
    private MatchFinder matchFinder;
    private ArrayList<LinkedList<Emoticon>> matchingX;
    private ArrayList<LinkedList<Emoticon>> matchingY;

    @Before
    public void setUp() throws Exception {
        emoticons = new AbstractEmoticon[X_MAX][Y_MAX];
        matchFinder = new MatchFinder();
        int counter = 0;
        for (int x = ROW_START; x < X_MAX; x++) {
            for (int y = COLUMN_TOP; y < Y_MAX; y++) {
                if (emoticons[x][y] == null) {
                    emoticons[x][y] = new MockEmoticon(x, y, 20, 20, bitmap, "" + counter);
                    counter++;
                }
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        emoticons = null;
        matchFinder = null;
        matchingX = null;
        matchingY = null;
    }

    @Test
    public void testFindVerticalMatches() throws Exception {
        emoticons[2][1] = new MockEmoticon(2, 1, 20, 20, bitmap, "HAPPY");
        emoticons[2][2] = new MockEmoticon(2, 2, 20, 20, bitmap, "HAPPY");
        emoticons[2][3] = new MockEmoticon(2, 3, 20, 20, bitmap, "HAPPY");
        matchingX = matchFinder.findVerticalMatches(emoticons);

        assertEquals(1, matchingX.size());
        assertEquals(3, matchingX.get(0).size());
        assertEquals("HAPPY", matchingX.get(0).get(0).getEmoticonType());
        assertEquals("HAPPY", matchingX.get(0).get(1).getEmoticonType());
        assertEquals("HAPPY", matchingX.get(0).get(2).getEmoticonType());
    }

    @Test
    public void testFindHorizontalMatches() throws Exception {
        emoticons[3][3] = new MockEmoticon(3, 3, 20, 20, bitmap, "SURPRISED");
        emoticons[4][3] = new MockEmoticon(4, 3, 20, 20, bitmap, "SURPRISED");
        emoticons[5][3] = new MockEmoticon(5, 3, 20, 20, bitmap, "SURPRISED");
        emoticons[6][3] = new MockEmoticon(6, 3, 20, 20, bitmap, "SURPRISED");
        matchingY = matchFinder.findHorizontalMatches(emoticons);

        assertEquals(1, matchingY.size());
        assertEquals(4, matchingY.get(0).size());
        assertEquals("SURPRISED", matchingY.get(0).get(0).getEmoticonType());
        assertEquals("SURPRISED", matchingY.get(0).get(1).getEmoticonType());
        assertEquals("SURPRISED", matchingY.get(0).get(2).getEmoticonType());
        assertEquals("SURPRISED", matchingY.get(0).get(3).getEmoticonType());
    }

    @Test
    public void testAnotherVerticalMatchPossible() throws Exception {
        assertFalse(matchFinder.anotherMatchPossible(emoticons));
        emoticons[2][1] = new MockEmoticon(2, 1, 20, 20, bitmap, "HAPPY");
        emoticons[2][2] = new MockEmoticon(2, 2, 20, 20, bitmap, "HAPPY");
        emoticons[2][4] = new MockEmoticon(2, 4, 20, 20, bitmap, "HAPPY");
        assertTrue(matchFinder.anotherMatchPossible(emoticons));
    }

    @Test
    public void testAnotherHorizontalMatchPossible() throws Exception {
        emoticons[3][3] = new MockEmoticon(3, 3, 20, 20, bitmap, "SURPRISED");
        emoticons[5][3] = new MockEmoticon(5, 3, 20, 20, bitmap, "SURPRISED");
        emoticons[6][3] = new MockEmoticon(6, 3, 20, 20, bitmap, "SURPRISED");
        assertTrue(matchFinder.anotherMatchPossible(emoticons));
    }*/
}
