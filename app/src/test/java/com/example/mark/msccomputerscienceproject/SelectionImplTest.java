package com.example.mark.msccomputerscienceproject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science final project
 */
public class SelectionImplTest {

    private Selection selection;
    public static final int X = 0;
    public static final int Y = 1;

    @Before
    public void setUp() throws Exception {
        selection = new SelectionImpl();

    }

    @After
    public void tearDown() throws Exception {
        selection = null;
    }

    @Test
    public void testResetUserSelections() throws Exception {
        selection.setSelection01(1, 2);
        selection.setSelection02(1, 3);
        assertEquals(selection.getSelection01()[X], 1);
        assertEquals(selection.getSelection01()[Y], 2);
        assertEquals(selection.getSelection02()[X], 1);
        assertEquals(selection.getSelection02()[Y], 3);
    }

    @Test
    public void testSelection01Made() throws Exception {
        assertEquals(selection.selection01Made(), false);
        selection.setSelection01(5, 6);
        assertEquals(selection.selection01Made(), true);
    }

    @Test
    public void testSameSelectionMadeTwice() throws Exception {
        selection.setSelection01(3, 3);
        selection.setSelection02(2, 3);
        assertEquals(selection.sameSelectionMadeTwice(), false);
        selection.resetUserSelections();
        assertEquals(selection.getSelection01()[X], -1);
        assertEquals(selection.getSelection02()[Y], -1);
        selection.setSelection01(5, 5);
        selection.setSelection02(5, 5);
        assertEquals(selection.sameSelectionMadeTwice(), true);
    }
/*
    @Test
    public void testAdjacentSelections() throws Exception {

    }

    @Test
    public void testSetSelection02ToSelection01() throws Exception {

    }*/
}