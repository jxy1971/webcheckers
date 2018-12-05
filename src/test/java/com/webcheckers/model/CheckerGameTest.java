package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Stack;

import com.webcheckers.model.Board.Enums.Type;
import com.webcheckers.model.Board.Piece;
import com.webcheckers.model.CheckerGame.GameWinner;
import com.webcheckers.model.Board.Row;
import com.webcheckers.model.Board.Enums.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit Test for {@link CheckerGameTest} component
 * 
 */
@Tag("Model-Tier")
public class CheckerGameTest {

    /**
     * component under test
     */
    private CheckerGame CuT;

    Player red;
    Player white;

    ArrayList<ArrayList<Move>> allMoves;
    ArrayList<ArrayList<Piece>> piecesTaken;
    ArrayList<Piece> pieceMoved;

    /**
     * Setup the CheckerGame for testing
     */
    @BeforeEach
    public void setup() {
        red = new Player("red");
        white = new Player("white");
        allMoves = new ArrayList<ArrayList<Move>>();
        piecesTaken = new ArrayList<ArrayList<Piece>>();
        pieceMoved = new ArrayList<Piece>();
        CuT = new CheckerGame(red, white, allMoves, piecesTaken, pieceMoved);
    }

    /**
     * Test if a player has moves
     */
    @Test
    public void playerHasMoves(){
        assertTrue(CuT.playerHasMoves());
    }

    /**
     * Test
     */
    @Test
    public void popMovesEmpty(){
        Stack<Move> copy = (Stack<Move>)CuT.getMoveStack().clone();
        try {
            CuT.getMoveStack().pop();
        } catch (Exception e) {
            //TODO: handle exception
        }
        assertEquals(copy, CuT.getMoveStack());
    }

    /**
     * Test getBoard
     */
    @Test
    public void getBoard(){
        assertNotNull(CuT.getBoard());
    }

    /**
     * Test getAllMoves
     */
    @Test
    public void getAllMoves() {
        assertNotNull(CuT.getAllMoves());
    }

    /**
     * Test getPiecesTaken
     */
    @Test
    public void getPiecesTaken() {
        assertNotNull(CuT.getPiecesTaken());
    }

    /**
     * Test getPieceMoved
     */
    @Test
    public void getPieceMoved() {
        assertNotNull(CuT.getPieceMoved());
    }

    /**
     * test getMoveCtr
     */
    @Test
    public void getMoveCtr() {
        assertNotNull(CuT.getMoveCtr());
    }

    /**
     * Test if white player resigned
     */
    @Test
    public void whitePlayerResigned(){
        CuT.playerResigned(white);
        assertEquals(GameWinner.red, CuT.getWinner());
    }

    /**
     * Test if red player resigned
     */
    @Test
    void redPlayerResigned(){
        CuT.playerResigned(red);
        assertEquals(GameWinner.white,CuT.getWinner());
    }

    /**
     * Test resigning works
     */
    @Test
    void testResignation(){
        CuT.playerResigned(new Player(null));
    }

    /**
     * Test getOpponentWhite
     */
    @Test
    public void getOpponentWhite(){
        Player p = CuT.getOpponent(white);
        assertEquals(CuT.getRedPlayer(), p);
    }

    /**
     * Test getOpponent
     */
    @Test
    public void getOpponent(){
        Player p = CuT.getOpponent(new Player("Player3"));
        assertNull(p);
    }

    /**
     * Test if available moves after a move
     */
    @Test
    public void submitTurnHasMoves(){
        Message m = CuT.submitTurn();
        assertEquals("Can't submit turn while jump still available", m.getText());
    }

    /**
     * Test submit turn after a move
     */
    @Test
    public void testSubmitTurnMovesMade(){
        Color c = CuT.getActiveColor();
        CuT.pushMove(new Move(new Position(2,1), new Position(3,2)));
        Message m = CuT.submitTurn();
        Stack s = CuT.getMoveStack();
        assertEquals(0, s.size());
        assertNotEquals(c, CuT.getActiveColor());
    }

    /**
     * Test switching turns
     */
    @Test
    void testSwitchCurrentTurn(){
        assertEquals(CuT.getActiveColor(),Color.RED);
        CuT.switchCurrentTurn();
        assertEquals(CuT.getActiveColor(),Color.WHITE);
        assertNotNull(CuT.submitTurn());
        CuT.switchCurrentTurn();
        assertEquals(CuT.getActiveColor(),Color.RED);
     }

    /**
     * Test Player Red has no pieces and loses
     */
    @Test
    void testPlayerRedNoPiecesLose(){
        CuT.playerNoPiecesLose();
        assertEquals(CuT.getWinner(),GameWinner.ingame);
        CuT.setNumRed(0);
        CuT.playerNoPiecesLose();
        assertEquals(CuT.getWinner(),GameWinner.white);
    }

    /**
     * Test Plyer White has no pieces and loses
     */
    @Test
    void testPlayerWhiteNoPiecesLose(){
        CuT.playerNoPiecesLose();
        assertEquals(CuT.getWinner(),GameWinner.ingame);
        CuT.setNumWhite(0);
        CuT.playerNoPiecesLose();
        assertEquals(CuT.getWinner(),GameWinner.red);
    }

    /**
     * Test if starting player (Red) is their turn
     */
    @Test
    void testIsMyTurn(){
        assertTrue(CuT.isMyTurn(red));
        assertFalse(CuT.isMyTurn(white));
    }

    /**
     * Test submit turn for white player
     */
    @Test
    public void whitePlayerFlip(){
        CuT.switchCurrentTurn();
        Move move = new Move(new Position(2,1), new Position(3,2));
        CuT.pushMove(move);
        Message msg = CuT.submitTurn();
        assertEquals("All moves submitted", msg.getText());
    }

    /**
     * Tests if jumpMove works as intended
     */
    @Test
    public void jumpMove(){
        Move m1 = new Move(new Position(2,2), new Position(3, 3));
        CuT.submitTurn();
        Move m2 = new Move(new Position(5, 5), new Position(4, 4));
        CuT.submitTurn();
        Move m3 = new Move(new Position(3, 3), new Position(5, 5));
        CuT.submitTurn();
        CuT.getBoard().getBoardArray().get(4).getSpace(4).setPiece(new Piece(Type.SINGLE,Color.RED));
        Move m4 = new Move(new Position(4,4),new Position(6,6));
        CuT.getMoveStack().clear();
        CuT.pushMove(m4);
        CuT.submitTurn();
        ArrayList<Row> rows = CuT.getBoard().getBoard();
        assertEquals(rows.get(4).getSpace(4).getPiece(), null);
    }

    /**
     * Test next turn for replay
     */
    @Test
    public void nextTurn() {
        Move m = new Move(new Position(2,1), new Position(4,3));
        CuT.pushMove(m);
        CuT.submitTurn();
        Message msg = CuT.nextTurn();
        assertEquals("true", msg.getText());
    }

    /**
     * Test previous turn for replay
     */
    @Test
    public void prevTurn() {
        Move m = new Move(new Position(2,1), new Position(4,3));
        CuT.pushMove(m);
        CuT.submitTurn();
        CuT.nextTurn();
        Message msg = CuT.previousTurn();
        assertEquals("true", msg.getText());
    }
    
}
