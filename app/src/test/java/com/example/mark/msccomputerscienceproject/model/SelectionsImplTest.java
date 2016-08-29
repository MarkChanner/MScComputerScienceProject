package com.example.mark.msccomputerscienceproject.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mark Channer for Birkbeck MSc Computer Science final project
 */
public class SelectionsImplTest {

    private Selections selections;
    public static final int X = 0;
    public static final int Y = 1;

    @Before
    public void setUp() throws Exception {
        selections = new SelectionsImpl();
    }

    @After
    public void tearDown() throws Exception {
        selections = null;
    }

    @Test
    public void testResetUserSelections() throws Exception {
        selections.setSelection01(1, 2);
        selections.setSelection02(1, 3);
        assertEquals(1, selections.getSelection01()[X]);
        assertEquals(2, selections.getSelection01()[Y]);
        assertEquals(1, selections.getSelection02()[X]);
        assertEquals(3, selections.getSelection02()[Y]);
        selections.reset();
        assertEquals(-2, selections.getSelection01()[X]);
        assertEquals(-2, selections.getSelection01()[Y]);
        assertEquals(-1, selections.getSelection02()[X]);
        assertEquals(-1, selections.getSelection02()[Y]);
    }

    @Test
    public void testSelection01Made() throws Exception {
        assertEquals(false, selections.selection01Made());
        selections.setSelection01(5, 6);
        assertEquals(true, selections.selection01Made());
    }

    @Test
    public void testSameSelectionMadeTwice() throws Exception {
        selections.setSelection01(3, 3);
        selections.setSelection02(2, 3);
        assertEquals(false, selections.sameSelectionMadeTwice());
        selections.reset();
        selections.setSelection01(5, 5);
        selections.setSelection02(5, 5);
        assertEquals(true, selections.sameSelectionMadeTwice());
    }

    @Test
    public void testAdjacentSelections() throws Exception {
        selections.setSelection01(4, 3);
        selections.setSelection02(6, 3);
        assertEquals(true, selections.areNotAdjacent());
        selections.reset();
        selections.setSelection01(4, 3);
        selections.setSelection02(5, 3);
        assertEquals(false, selections.areNotAdjacent());
    }

    @Test
    public void testSetSelection02ToSelection01() throws Exception {
        selections.setSelection01(5, 5);
        selections.setSelection02(7, 7);
        selections.secondSelectionBecomesFirstSelection();
        assertEquals(7, selections.getSelection01()[X]);
        assertEquals(7, selections.getSelection01()[Y]);
        assertEquals(-1, selections.getSelection02()[X]);
        assertEquals(-1, selections.getSelection02()[Y]);
    }
}