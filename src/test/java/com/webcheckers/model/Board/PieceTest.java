package com.webcheckers.model.Board;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.webcheckers.model.Board.Enums.Color;
import com.webcheckers.model.Board.Enums.Type;

/**
 * Tests Piece
 */
public class PieceTest {
    
    private Piece KingRed = new Piece(Type.KING, Color.RED);
    private Piece KingWhite = new Piece(Type.KING, Color.WHITE);
    private Piece SingleRed = new Piece(Type.SINGLE, Color.RED);
    private Piece SingleWhite = new Piece(Type.SINGLE, Color.WHITE);

    /**
     * Tests getColor of a Red King Piece
     */
    @Test
    void testKingRed() {
        assertEquals(KingRed.getColor(), Color.RED);
    }

    /**
     * Tests getColor of a Red Single Piece
     */
    @Test
    void testSingleRed(){
        assertEquals(SingleRed.getColor(), Color.RED);
    }

    /**
     * Tests getColor of a White King Piece
     */
    @Test
    void testKingWhite(){
        assertEquals(KingWhite.getColor(), Color.WHITE);
    }

    /**
     * Tests getColor of a White Single Piece
     */
    @Test
    void testSingleWhite(){
        assertEquals(SingleWhite.getColor(), Color.WHITE);
    }

    /**
     * Tests getType of a Red King Piece
     */
    @Test
    void testRedKingType() {
        assertEquals(KingRed.getType(), Type.KING);
    }

    /**
     * Tests getType of a Red Single Piece
     */
    @Test
    void testRedSingleType(){
        assertEquals(SingleRed.getType(), Type.SINGLE);
    }

    /**
     * Tests getType of a White King Piece
     */
    @Test
    void testWhiteKingType(){
        assertEquals(KingWhite.getType(), Type.KING);
    }

    /**
     * Tests getType of a White Single Piece
     */
    @Test
    void testWhiteSingleType(){
        assertEquals(SingleWhite.getType(), Type.SINGLE);
    }

    /**
     * Tests setKing of a Red Single Piece
     */
    @Test
    void testSetRedKing(){
        SingleRed.setKing();
        assertEquals(SingleRed.getType(),Type.KING);
    }

    /**
     * Tests setKing of a White Single Piece
     */
    @Test
    void testSetWhiteKing(){
        SingleWhite.setKing();
        assertEquals(SingleWhite.getType(),Type.KING);
    }

}
