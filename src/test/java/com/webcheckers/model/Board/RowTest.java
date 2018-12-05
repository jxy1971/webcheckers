package com.webcheckers.model.Board;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Test Row
 */
public class RowTest {
    private Row row0 = new Row(0);
    private Row row1 = new Row(1);

    /**
     * Test getIndex
     */
    @Test
    void testGetIndex(){
        assertEquals(row0.getIndex(),0);
        assertEquals(row1.getIndex(),1);
    }

    /**
     * Test iterator
     */
    @Test
    void testIterator(){
        assertFalse(row0.iterator().hasNext());
        assertFalse(row1.iterator().hasNext());
        row0.addSpace(new Space(0,true));
        assertTrue(row0.iterator().hasNext());
        assertEquals(row0.iterator().next().getCellIdx(),0);
    }

    /**
     * Test toString
     */
    @Test
    void testToString(){
        assertEquals(row0.toString(),"Row: 0");
    }
}
