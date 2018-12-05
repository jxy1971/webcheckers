package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.webcheckers.model.Board.*;

/**
 * Test Position
 */
public class PositionTest {

    int row = 1;
    int col = 1;
    private Position p = new Position(row, col);
    private Position test1 = new Position(0,0);
    private Position test2 = new Position(1,0);
    private Position test3 = new Position(0,1);
    private Space test4 = new Space(0,true);

    /**
     * Test getRow
     */
    @Test
    void testGetRow() {
        assertEquals(p.getRow(), row);
    }

    /**
     * Test getCell
     */
    @Test
    void testGetCell() {
        assertEquals(p.getCell(), col);
    }

    /**
     * Test two positions are unequal
     */
    @Test
    void testPositionNotEqual(){
        assertNotEquals(p, test1);
    }

    /**
     * Test two positions have the same row, not column
     */
    @Test
    void testPositionRowEqual(){
        assertNotEquals(p,test2);
    }

    /**
     * Test two positions have the same column, not row
     */
    @Test
    void testPositionColumnEqual(){
        assertNotEquals(p,test3);
    }

    /**
     * Test if position is null
     */
    @Test
    void testNullEqual(){
        assertNotEquals(p,null);
    }

    /**
     * Test if position is a space
     */
    @Test
    void testNotObjectEqual(){
        assertNotEquals(p,test4);
    }

    /**
     * Test toString prints correctly
     */
    @Test
    void testToString(){
        assertEquals(p.toString(),"[1,1]");
    }

    
}
