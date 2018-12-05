package com.webcheckers.model.Board;
import static org.junit.jupiter.api.Assertions.*;
import com.webcheckers.model.Board.Enums.Color;
import com.webcheckers.model.Board.Enums.Type;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for Space
 */
class SpaceTest {
    private Space white = new Space(0,false);
    private Space black = new Space(1,true);
    private Piece piece = new Piece(Type.SINGLE,Color.RED);
    private Piece nopiece = new Piece(null,null);

    /**
     * Tests a simple empty space
     */
    @Test
    void testEmptySpace(){
        assertEquals(white.getCellIdx(),0);
    }

    /**
     * Test creating a piece onto the space
     */
    @Test
    void testCreatePiece(){
        black.createPiece(Type.SINGLE,Color.RED);
        assertEquals(black.getPiece().getColor(),piece.getColor());
        black.createPiece(Type.SINGLE,Color.WHITE);
        assertEquals(black.getPiece().getColor(),piece.getColor());
    }
    /**
     * Tests if the space with a piece on it is valid
     */
    @Test
    void testTakenSpace(){
        black.createPiece(Type.SINGLE,Color.RED);
        assertFalse(black.isValid());
    }

    /**
     * Test if an invalid space can place a piece
     */
    @Test
    void testIsValidFalse(){
        assertFalse(white.isValid());
    }

    /**
     * Test removing a piece on a space
     */
    @Test
    void testRemoveSpacePiece(){
        black.removePiece();
        assertTrue(black.isValid());
    }
    /**
     * Test setting a piece to a space
     */
    @Test
    void testSetPiece(){
        assertTrue(black.isValid());
        black.setPiece(piece);
        assertEquals(black.getPiece().getType(),piece.getType());
        assertEquals(black.getPiece().getColor(),piece.getColor());
        assertFalse(black.isValid());
        black.setPiece(nopiece);
        assertNull(black.getPiece().getType());
        assertNull(black.getPiece().getColor());
    }

    /**
     * Test toString
     */
    @Test
    void testToString(){
        black.setPiece(piece);
        assertEquals(black.toString()," Space: 1 Piece: SINGLE");
        assertEquals(white.toString()," Space: 0 Piece: NONE");
    }
}
