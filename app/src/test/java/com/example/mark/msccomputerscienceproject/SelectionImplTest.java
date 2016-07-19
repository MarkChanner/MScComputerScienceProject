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

    /*@Test
    public void testSelection01Made() throws Exception {

    }

    @Test
    public void testGetSelection01() throws Exception {

    }

    @Test
    public void testSetSelection01() throws Exception {

    }

    @Test
    public void testGetSelection02() throws Exception {

    }

    @Test
    public void testSetSelection02() throws Exception {

    }

    @Test
    public void testSameSelectionMadeTwice() throws Exception {

    }

    @Test
    public void testAdjacentSelections() throws Exception {

    }

    @Test
    public void testSetSelection02ToSelection01() throws Exception {

    }*/
}