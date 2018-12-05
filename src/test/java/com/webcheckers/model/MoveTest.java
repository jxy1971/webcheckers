package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.GameCenter;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

/**
 * Test Move
 */
public class MoveTest {

    private Position start = new Position(2,1);
    private Position end = new Position(1,2);
    private Move m = new Move(start, end);
    private Move mcopy = new Move(start,end);
    private Move m2 = new Move(end, start);
    private Position end2 = new Position(1,3);
    private Position end3 = new Position(4,3);
    private Move m3 = new Move (start, end2);
    private Player p1 = new Player("p1");
    private Player p2 = new Player("p2");
    private CheckerGame game = new CheckerGame(p1, p2);
    private Move mastermove = new Move(start,new Position(1,0));

    /**
     * Test getStart
     */
    @Test
    void testGetStart() {
        assertEquals(m.getStart(), start);
    }

    /**
     * Test getEnd
     */
    @Test
    void testGetEnd() {
        assertEquals(m.getEnd(), end);
    }

    /**
     * Test toString
     */
    @Test
    void testToString() {
        assertEquals(m.toString(), "Move: Start[" + start.getRow() + "," + start.getCell() + "] End[" + end.getRow() + "," + end.getCell() + "]");
    }

    /**
     * Test flipMove
     */
    @Test
    void testFlipMove() {
        m.flipMove();
        assertEquals(m.getStart().getRow(), 5);
        assertEquals(m.getStart().getCell(), 6);
        assertEquals(m.getEnd().getRow(), 6);
        assertEquals(m.getEnd().getCell(), 5);
    }

    /**
     * Test if a move is move
     */
    @Test
    void testObjectEquals(){
        assertEquals(m,m);
    }

    /**
     * Test object null
     */
    @Test
    void testObjectNull(){
        assertNotEquals(m,null);
    }

    /**
     * Test move with not a move
     */
    @Test
    void testObjectNotEquals(){
        assertNotEquals(m,end);
    }

    /**
     * Test not same starting positions
     */
    @Test
    void testObjectSameStart(){
        assertNotEquals(m,m3);
    }

    /**
     * Test if a is a jump
     */
    @Test
    void testIsJump(){
        Move move = new Move(start,end3);
        assertTrue(move.isJump());
    }

    /**
     * Test the Error Message for simple move
     */
    @Test
    void testValidateMoveErrorMessage(){
        Move move = new Move(start,end);
        HashSet<Move> set = new HashSet<Move>();
        assertEquals(move.validateMove(set, false).getType(),MessageType.error);
        assertEquals(move.validateMove(set, true).getType(),MessageType.error);
    }

    /**
     * Test the Error Message for jump move
     */
    @Test
    void testValidateJumpMoveErrorMessage(){
        Move simpleMove = new Move(start,end);
        Move validJumpMove = new Move(start, end3);
        HashSet<Move> set = new HashSet<Move>();
        set.add(validJumpMove);
        String mustJumpErrorText = "Invalid, must make jump move";
        assertEquals(simpleMove.validateMove(set, true).getType(),MessageType.error);
        assertEquals(simpleMove.validateMove(set, true).getText(), mustJumpErrorText);
    }

    /**
     * Test getOpponentPiece
     */
    @Test
    void testGetOpponentPiece(){
        Move move = new Move(start,end3);
        assertEquals(move.getOpponentPiece(),new Position(3,2));
    }
}
