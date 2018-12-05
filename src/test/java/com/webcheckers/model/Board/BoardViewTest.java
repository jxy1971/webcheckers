package com.webcheckers.model.Board;
import static org.junit.jupiter.api.Assertions.*;
import com.webcheckers.model.Board.Enums.Color;
import com.webcheckers.model.Board.Enums.Type;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.Test;

/**
 * Test BoardView
 */
public class BoardViewTest {
    private BoardView boardView = new BoardView();
    //Moves to get White King
    private Move move1 = new Move(new Position(2,1),new Position(3,2));
    private Move move2 = new Move(new Position(3,2),new Position(4,1));
    private Move move3 = new Move(new Position(4,1),new Position(5,2));
    private Move move4 = new Move(new Position(5,2),new Position(6,1));
    private Move move5 = new Move(new Position(6,1),new Position(7,2));
    private Move move6 = new Move(new Position(7,2),new Position(6,1));

    //Moves to get Red King
    private Move move21 = new Move(new Position(5,2),new Position(4,1));
    private Move move22 = new Move(new Position(4,1),new Position(3,2));
    private Move move23 = new Move(new Position(3,2),new Position(2,1));
    private Move move24 = new Move(new Position(2,1),new Position(1,2));
    private Move move25 = new Move(new Position(1,2),new Position(0,1));
    private Move move26 = new Move(new Position(0,1),new Position(1,2));

    /**
     * Tests making a white piece a king
     */
    @Test
    void testMakeWhiteKing(){
        boardView.getBoard().get(5).getSpace(2).removePiece();
        boardView.getBoard().get(6).getSpace(1).removePiece();
        boardView.getBoard().get(7).getSpace(2).removePiece();
        boardView.movePiece(move1);
        boardView.movePiece(move2);
        boardView.movePiece(move3);
        boardView.movePiece(move4);
        boardView.movePiece(move5);
        assertEquals(boardView.getBoard().get(7).getSpace(2).getPiece().getType(),Type.KING);
        boardView.movePiece(move6);
        boardView.movePiece(move5);
        assertEquals(boardView.getBoard().get(7).getSpace(2).getPiece().getType(),Type.KING);
    }

    /**
     * Tests if own piece can be a king on own space
     */
    @Test
    void testIfOwnCanBeKing(){
        boardView.getBoard().get(6).getSpace(1).removePiece();
        boardView.getBoard().get(7).getSpace(2).removePiece();
        boardView.getBoard().get(6).getSpace(1).createPiece(Type.SINGLE,Color.RED);
        boardView.movePiece(move5);
        assertEquals(boardView.getBoard().get(7).getSpace(2).getPiece().getType(),Type.SINGLE);

        boardView.getBoard().get(1).getSpace(2).removePiece();
        boardView.getBoard().get(0).getSpace(1).removePiece();
        boardView.getBoard().get(1).getSpace(2).createPiece(Type.SINGLE,Color.WHITE);
        boardView.movePiece(move25);
        assertEquals(boardView.getBoard().get(0).getSpace(1).getPiece().getType(),Type.SINGLE);

    }

    /**
     * Tests making red a king
     */
    @Test
    void testMakeRedKing(){
        boardView.getBoard().get(2).getSpace(1).removePiece();
        boardView.getBoard().get(1).getSpace(2).removePiece();
        boardView.getBoard().get(0).getSpace(1).removePiece();
        boardView.movePiece(move21);
        boardView.movePiece(move22);
        boardView.movePiece(move23);
        boardView.movePiece(move24);
        boardView.movePiece(move25);
        assertEquals(boardView.getBoard().get(0).getSpace(1).getPiece().getType(),Type.KING);

        boardView.movePiece(move26);
        boardView.movePiece(move25);
        assertEquals(boardView.getBoard().get(0).getSpace(1).getPiece().getType(),Type.KING);
    }

    /**
     * Tests if getWhitePlayerView returns a boardview
     */
    @Test
    void testGetWhitePlayerView(){
        assertTrue(boardView.getWhitePlayerView() instanceof BoardView);
    }
    /**
     * Tests if getRedPlayerView returns a boardview
     */
    @Test
    void testGetRedPlayerView(){
        assertTrue(boardView.getRedPlayerView() instanceof BoardView);
    }

    /**
     * Tests iterator
     */
    @Test
    void testIterator(){
        assertTrue(boardView.iterator().hasNext());
        assertEquals(boardView.iterator().next().getIndex(),0);
        boardView.getBoard().clear();
        assertFalse(boardView.iterator().hasNext());
    }
}
