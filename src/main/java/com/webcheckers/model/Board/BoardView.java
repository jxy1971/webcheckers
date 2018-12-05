package com.webcheckers.model.Board;

import java.util.ArrayList;
import java.util.Iterator;

import com.webcheckers.model.Board.Enums.Color;
import com.webcheckers.model.Board.Enums.Type;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;

/**
 * Iterable class used to implement rows in game.ftl and main model
 * for the Checkers Board
 */
public class BoardView implements Iterable<Row> {
    private ArrayList<Row> board;

    /**
     * default constructor that creates an ArrayList of Row
     * objects and calls the method to initialize the board
     */
    public BoardView() {
        board = new ArrayList<Row>();
        this.initBoard();
    }
    public ArrayList<Row> getBoard(){
        return board;
    }

    /**
     * other constructor that is used for displaying
     * proper board configuration for Red player and White player
     * @param board-original board model
     */
    public BoardView(ArrayList<Row> board) {
        this.board = board;
    }

    /**
     * Initializes the board by iterating through every space
     * and sets its properties and starting piece positions
     */
    private void initBoard() {
        for(int i = 0; i < 8; i++) {
            board.add(new Row(i));
            for (int j = 0; j < 8; j++) {
                if ((i+j)%2 == 0)
                    board.get(i).addSpace(new Space(j, false));
                else
                    board.get(i).addSpace(new Space(j, true));
            }
        }

        for (int i = 0; i < 8; i+=2)
            board.get(7).getSpace(i).createPiece(Type.SINGLE, Color.RED);
        for (int i = 1; i < 8; i+=2)
            board.get(6).getSpace(i).createPiece(Type.SINGLE, Color.RED);
        for (int i = 0; i < 8; i+=2)
            board.get(5).getSpace(i).createPiece(Type.SINGLE, Color.RED);
        
        for (int i = 1; i < 8; i+=2)
            board.get(0).getSpace(i).createPiece(Type.SINGLE, Color.WHITE);
        for (int i = 0; i < 8; i+=2)
            board.get(1).getSpace(i).createPiece(Type.SINGLE, Color.WHITE);
        for (int i = 1; i < 8; i+=2)
            board.get(2).getSpace(i).createPiece(Type.SINGLE, Color.WHITE);
    }

    /**
     * Gets the version of the Board that is appropriate for the White player
     * @return new BoardView object
     */
    public BoardView getRedPlayerView()
    {
        return new BoardView(this.board);
    }

    /**
     * Reverses the indexing of original board to display appropriate view for Red player
     * @return new BoardView object
     */
    public BoardView getWhitePlayerView()
    {
        ArrayList<Row> tempBoard = new ArrayList<Row>();
        for (int i = 0; i < 8; i++) {
            tempBoard.add(i, new Row(i));
            for (int j = 0; j < 8; j++) {
                Space s = this.board.get(7-i).createWhiteSpace(7 - j);
                tempBoard.get(i).addSpace(s);
            }
        }
        return new BoardView(tempBoard);

    }

    /**
     * a single Move to move
     * @param singleMove the move
     */
    public void movePiece(Move singleMove) {
        Position start = singleMove.getStart();
        Position end = singleMove.getEnd();

        Space initialSpace = board.get(start.getRow()).getSpace(start.getCell());
        Space finalSpace = board.get(end.getRow()).getSpace(end.getCell());

        if (!finalSpace.isValid()) {
            System.out.println(finalSpace + " error final invalid");
            //return;
        }

        Piece thisPiece;
        if (initialSpace.getPiece() == null) {
            System.out.println(initialSpace + " error initial invalid");
            return;
        } else
            thisPiece = initialSpace.getPiece();

        finalSpace.setPiece(thisPiece);
        initialSpace.removePiece();
        //Obtain a king
        if (end.getRow() == 0 && thisPiece.getType() != Type.KING && thisPiece.getColor() == Color.RED) {
            thisPiece.setKing();
        } else if (end.getRow() == 7 && thisPiece.getType() != Type.KING && thisPiece.getColor() == Color.WHITE) {
            thisPiece.setKing();
        }
    }

    /**
     * removes a piece when it is taken
     * @param position the position that has the piece
     */
    public Piece removeTakenPiece(Position position)
    {
        return board.get(position.getRow()).getSpace(position.getCell()).removePiece();
    }

    /**
     * places a piece on board
     * @param position the position where piece is going
     */
    public void placeNewPiece(Position position, Piece piece) {
        board.get(position.getRow()).getSpace(position.getCell()).setPiece(piece);
    }

    /**
     * gets a space in a position
     * @apram position 
     */
    public Space getSpace(Position position) {
        return board.get(position.getRow()).getSpace(position.getCell());
    }
    
    /**
     * Implementation of hasNext and next iterator functions of this class.
     * Used for iterating and setting properties of every row by the game.ftl file
     * @return new Iterator<Row> object
     */
    @Override
    public Iterator<Row> iterator() {
        return new Iterator<Row>() {

            int indexPosition = 0;

            @Override
            public boolean hasNext() {
                if(indexPosition >= board.size())
                    return false;

                return true;
            }

            @Override
            public Row next() {
                Row val = board.get(indexPosition);
                indexPosition++;
                return val;
            }
        };
    }

    /**
     * Testing function
     * Clears the board of pieces
     */
    public void clearBoard(){
        for (int i = 0; i <= 3; i++) {
            Row row = board.get(i);
            for (int j = 0; j <= 7; j++) {
                if (row.getSpace(j).isColored()) {
                    row.getSpace(j).removePiece();
                }
            }
        }
        for (int i = 7; i >= 5; i--) {
            Row row = board.get(i);
            for (int j = 0; j <= 7; j++) {
                if (row.getSpace(j).isColored()) {
                    row.getSpace(j).removePiece();
                }
            }
        }
    }
    /**
     * Debugging function to make sure underlying code was implemented properly
     */
    /*public void printBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board.get(i).getSpace(j).toString());
            }
            System.out.println();
        }
    }*/

    public ArrayList<Row> getBoardArray(){
        return board;
    }

}
