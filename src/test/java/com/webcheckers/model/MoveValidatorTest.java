package com.webcheckers.model;

import com.webcheckers.model.Board.BoardView;
import com.webcheckers.model.Board.Enums.Color;
import com.webcheckers.model.Board.Enums.Type;
import com.webcheckers.model.Board.MoveValidator;
import com.webcheckers.model.Board.Piece;
import com.webcheckers.model.Board.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the functions of the MoveValidator class
 */
public class MoveValidatorTest {
    Player redPlayer;
    Player whitePlayer;
    MoveValidator validator;
    CheckerGame game;
    BoardView board;

    /**
     * Sets up MoveValidators to test along with other necessary classes/objects
     */
    @BeforeEach
    void setup(){
        redPlayer = new Player("red");
        whitePlayer = new Player("white");
        game = new CheckerGame(redPlayer,whitePlayer);
        board = game.getBoard();
        validator = game.validator;
    }

    /**
     * Tests if getAllPossibleMovesGameStart works at beginning of game
     */
    @Test
    void testGetAllPossibleMovesGameStart(){
        validator.genAllPossibleMoves();
        assertTrue(validator.hasMoves());
        assertEquals(validator.getPossibleMoves().size(),7);

        validator.switchActivePlayer();

        assertFalse(validator.isRedPlayer);
        validator.genAllPossibleMoves();
        assertTrue(validator.hasMoves());
        assertEquals(validator.getPossibleMoves().size(),7);
    }

    /**
     * Tests if getAllPossibleMovesGameStart returns empty set if
     * all pieces of one team is blocked by the opponent's pieces
     */
    @Test
    void testGetAllPossibleMovesGameBlocked(){
        for(int i = 4;i>=3;i--){
            Row row = board.getBoardArray().get(i);
            for(int j = 0;j<=7;j++){
                if(row.getSpace(j).isColored()){
                    row.getSpace(j).setPiece(new Piece(Type.SINGLE, Color.WHITE));
                }
            }
        }
        assertEquals(validator.getPossibleMoves().size(),0);
    }

    /**
     * Tests if jumps are detected when  there is a single row of
     * opponent pieces on the board
     */
    @Test
    void testPossibleJump(){
        int i = 4;
        Row row = board.getBoardArray().get(i);
        for(int j = 0;j<=7;j++){
            if(row.getSpace(j).isColored()){
                row.getSpace(j).setPiece(new Piece(Type.SINGLE,Color.WHITE));
            }
        }
        validator.genAllPossibleMoves();
        assertEquals(validator.getJumpMoves().size(),6);
    }


    /**
     * Tests if getPossibleJumpsOnPosition works with jumps for different positions
     */
    @Test
    void testGetPossibleMovesOnPositionWithJumpsRed() {
        int i = 4;
        Row row = board.getBoardArray().get(i);
        for (int j = 0; j <= 7; j++) {
            if (row.getSpace(j).isColored()) {
                row.getSpace(j).setPiece(new Piece(Type.SINGLE, Color.WHITE));
            }
        }
        Position testPos = new Position(5, 0);
        validator.getPossibleJumpsOnPosition(testPos,false, null);
        assertEquals(validator.getPossibleMoves().size(), 1);

        testPos = new Position(7, 0);
        validator.getPossibleJumpsOnPosition(testPos,false, null);
        assertEquals(validator.getPossibleMoves().size(), 0);

        testPos = new Position(5, 2);
        validator.getPossibleJumpsOnPosition(testPos,false, null);
        assertEquals(validator.getPossibleMoves().size(), 2);
    }

    /**
     * Test all possible jump moves on a certain position for a white piece that has available jumps
     */
    @Test
    void testGetPossibleMovesOnPositionWithJumpsWhite(){
        validator.switchActivePlayer();
        int i = 3;
        Row row = board.getBoardArray().get(i);
        for (int j = 0; j <= 7; j++) {
            if (row.getSpace(j).isColored()) {
                row.getSpace(j).setPiece(new Piece(Type.SINGLE, Color.RED));
            }
        }
        Position testPos = new Position(2, 7);
        validator.getPossibleJumpsOnPosition(testPos,false, null);
        assertEquals(validator.getPossibleMoves().size(), 1);

        testPos = new Position(0, 7);
        validator.getPossibleJumpsOnPosition(testPos,false, null);
        assertEquals(validator.getPossibleMoves().size(), 0);

        testPos = new Position(2, 5);
        validator.getPossibleJumpsOnPosition(testPos,false, null);
        assertEquals(validator.getPossibleMoves().size(), 2);
    }

    /**
     * Set up board for testing
     */
    void setupBoard(){
        for(int i = 0;i<=3;i++){
            Row row = board.getBoardArray().get(i);
            for(int j = 0;j<=7;j++){
                if(row.getSpace(j).isColored()){
                    row.getSpace(j).removePiece();
                }
            }
        }
        for(int i = 7;i>=5;i--){
            Row row = board.getBoardArray().get(i);
            for(int j = 0;j<=7;j++){
                if(row.getSpace(j).isColored()){
                    row.getSpace(j).removePiece();
                }
            }
        }
    }

    /**
     * Test possible king moves
     */
    @Test
    void testKingMoves(){
        setupBoard();
        Row row = board.getBoardArray().get(5);
        row.getSpace(2).setPiece(new Piece(Type.KING,Color.RED));
        validator.genAllPossibleMoves();
        assertEquals(validator.getPossibleMoves().size(),4);
        row.getSpace(2).removePiece();
        row = board.getBoardArray().get(0);
        row.getSpace(1).setPiece(new Piece(Type.KING,Color.RED));
        validator.genAllPossibleMoves();
        assertEquals(validator.getPossibleMoves().size(),2);
        validator.switchActivePlayer();
        row = board.getBoardArray().get(2);
        row.getSpace(1).setPiece(new Piece(Type.KING,Color.WHITE));
        validator.genAllPossibleMoves();
        assertEquals(validator.getPossibleMoves().size(),4);
        row.getSpace(1).removePiece();
        row = board.getBoardArray().get(7);
        row.getSpace(2).setPiece(new Piece(Type.KING,Color.WHITE));
        validator.genAllPossibleMoves();
        assertEquals(validator.getPossibleMoves().size(),2);
    }

    /**
     * Test possible king jump moves
     */
    @Test
    void testKingJumps(){
        setupBoard();
        Row row = board.getBoardArray().get(5);
        row.getSpace(2).setPiece(new Piece(Type.KING,Color.RED));
        row = board.getBoardArray().get(4);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE,Color.WHITE));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE,Color.WHITE));
        row = board.getBoardArray().get(6);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE,Color.WHITE));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE,Color.WHITE));
        validator.genAllPossibleMoves();
        assertEquals(validator.getJumpMoves().size(),4);
        validator.switchActivePlayer();
        row = board.getBoardArray().get(5);
        row.getSpace(2).setPiece(new Piece(Type.KING,Color.WHITE));
        row = board.getBoardArray().get(4);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE,Color.RED));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE,Color.RED));
        row = board.getBoardArray().get(6);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE,Color.RED));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE,Color.RED));
        validator.genAllPossibleMoves();
        assertEquals(validator.getJumpMoves().size(),4);
    }

    /**
     * Test all possible king jumps at a position for a piece with available jumps
     */
    @Test
    void testKingJumpsOnPosition(){
        setupBoard();
        Row row = board.getBoardArray().get(5);
        row.getSpace(2).setPiece(new Piece(Type.KING,Color.RED));
        row = board.getBoardArray().get(4);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE,Color.WHITE));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE,Color.WHITE));
        row = board.getBoardArray().get(6);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE,Color.WHITE));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE,Color.WHITE));
        validator.getPossibleJumpsOnPosition(new Position(5,2),true, null);
        assertEquals(validator.getJumpMoves().size(),4);
        validator.switchActivePlayer();
        row = board.getBoardArray().get(5);
        row.getSpace(2).setPiece(new Piece(Type.KING,Color.WHITE));
        row = board.getBoardArray().get(4);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE,Color.RED));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE,Color.RED));
        row = board.getBoardArray().get(6);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE,Color.RED));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE,Color.RED));
        validator.getPossibleJumpsOnPosition(new Position(5,2),true, null);
        assertEquals(validator.getJumpMoves().size(),4);
        validator.printSet();
    }

    /**
     * Test the king jump move with the CantReturn array list
     */
    @Test
    void testKingJumpsOnPositionWithCantReturn() {
        setupBoard();
        ArrayList<Position> cantReturn = new ArrayList<>();
        Row row = board.getBoardArray().get(5);
        row.getSpace(2).setPiece(new Piece(Type.KING, Color.RED));
        row = board.getBoardArray().get(4);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        cantReturn.add(new Position(3, 0));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        cantReturn.add(new Position(3, 4));
        row = board.getBoardArray().get(6);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        cantReturn.add(new Position(7, 0));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        cantReturn.add(new Position(7, 4));
        validator.getPossibleJumpsOnPosition(new Position(5, 2), true, cantReturn);
        assertEquals(validator.getJumpMoves().size(), 0);
        validator.switchActivePlayer();
        row = board.getBoardArray().get(5);
        row.getSpace(2).setPiece(new Piece(Type.KING, Color.WHITE));
        row = board.getBoardArray().get(4);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE, Color.RED));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE, Color.RED));
        row = board.getBoardArray().get(6);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE, Color.RED));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE, Color.RED));
        validator.getPossibleJumpsOnPosition(new Position(5, 2), true, cantReturn);
        assertEquals(validator.getJumpMoves().size(), 0);
        validator.hasMoves();
    }

    /**
     * Test if there are any possible moves
     */
    @Test
    void testNoPossibleMoves(){
        setupBoard();
        assertFalse(validator.genAllPossibleMoves());
        validator.switchActivePlayer();
        assertFalse(validator.genAllPossibleMoves());
    }

    /**
     * Test if the pieces are too close to the edge to jump
     */
    @Test
    void testTooCloseToEdges(){
        board.clearBoard();
        Row row = board.getBoardArray().get(0);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        row = board.getBoardArray().get(1);
        row.getSpace(2).setPiece(new Piece(Type.SINGLE, Color.RED));
        validator.genAllPossibleMoves();
        assertEquals(validator.getPossibleMoves().size(),1);
        board.clearBoard();
        row = board.getBoardArray().get(3);
        row.getSpace(0).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        row = board.getBoardArray().get(4);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE, Color.RED));
        validator.genAllPossibleMoves();
        assertEquals(validator.getPossibleMoves().size(),1);
    }

    /**
     * Test if a jump move can be completed if there is a piece behind the opponents piece
     */
    @Test
    void testBlockJump(){
        board.clearBoard();
        Row row = board.getBoardArray().get(5);
        row.getSpace(2).setPiece(new Piece(Type.KING, Color.RED));
        row = board.getBoardArray().get(4);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        row = board.getBoardArray().get(6);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        row = board.getBoardArray().get(3);
        row.getSpace(0).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        row.getSpace(4).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        row = board.getBoardArray().get(7);
        row.getSpace(0).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        row.getSpace(4).setPiece(new Piece(Type.SINGLE, Color.WHITE));
        assertFalse(validator.genAllPossibleMoves());
        row = board.getBoardArray().get(5);
        row.getSpace(2).setPiece(new Piece(Type.KING, Color.WHITE));
        row = board.getBoardArray().get(4);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE, Color.RED));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE, Color.RED));
        row = board.getBoardArray().get(6);
        row.getSpace(1).setPiece(new Piece(Type.SINGLE, Color.RED));
        row.getSpace(3).setPiece(new Piece(Type.SINGLE, Color.RED));
        row = board.getBoardArray().get(3);
        row.getSpace(0).setPiece(new Piece(Type.SINGLE, Color.RED));
        row.getSpace(4).setPiece(new Piece(Type.SINGLE, Color.RED));
        row = board.getBoardArray().get(7);
        row.getSpace(0).setPiece(new Piece(Type.SINGLE, Color.RED));
        row.getSpace(4).setPiece(new Piece(Type.SINGLE, Color.RED));
        validator.switchActivePlayer();
        assertFalse(validator.genAllPossibleMoves());
    }



}
