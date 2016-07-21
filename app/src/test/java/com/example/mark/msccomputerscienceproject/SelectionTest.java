package com.example.mark.msccomputerscienceproject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science final project
 */
public class SelectionTest {

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
        assertEquals(1, selection.getSelection01()[X]);
        assertEquals(2, selection.getSelection01()[Y]);
        assertEquals(1, selection.getSelection02()[X]);
        assertEquals(3, selection.getSelection02()[Y]);
        selection.resetUserSelections();
        assertEquals(-1, selection.getSelection01()[X]);
        assertEquals(-1, selection.getSelection02()[Y]);
    }

    @Test
    public void testSelection01Made() throws Exception {
        assertEquals(false, selection.selection01Made());
        selection.setSelection01(5, 6);
        assertEquals(true, selection.selection01Made());
    }

    @Test
    public void testSameSelectionMadeTwice() throws Exception {
        selection.setSelection01(3, 3);
        selection.setSelection02(2, 3);
        assertEquals(false, selection.sameSelectionMadeTwice());
        selection.resetUserSelections();
        selection.setSelection01(5, 5);
        selection.setSelection02(5, 5);
        assertEquals(true, selection.sameSelectionMadeTwice());
    }

    @Test
    public void testAdjacentSelections() throws Exception {
        selection.setSelection01(4, 3);
        selection.setSelection02(6, 3);
        assertEquals(false, selection.adjacentSelections());
        selection.resetUserSelections();
        selection.setSelection01(4, 3);
        selection.setSelection02(5, 3);
        assertEquals(true, selection.adjacentSelections());
    }

    @Test
    public void testSetSelection02ToSelection01() throws Exception {
        selection.setSelection01(5, 5);
        selection.setSelection02(1, 1);
        selection.secondSelectionBecomesFirstSelection();
        assertEquals(1, selection.getSelection01()[X]);
        assertEquals(1, selection.getSelection01()[Y]);
        assertEquals(-1, selection.getSelection02()[X]);
        assertEquals(-1, selection.getSelection02()[Y]);
    }
}