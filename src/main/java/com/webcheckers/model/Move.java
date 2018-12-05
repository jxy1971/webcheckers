package com.webcheckers.model;
import java.util.HashSet;

/**
 * This class represents a move that can be made by a player. A move consists
 * of a starting Position and a final one.
 */
public class Move {
    private Position start, end;
    final static int BOARD_MAX = 7;

    /**
     * Constructor for a Move
     * @param start- starting position
     * @param end- final position
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Gets the starting position of this move
     * @return start- starting position
     */
    public Position getStart() {
        return this.start;
    }


    /**
     * Gets the final position of this move
     * @return end- final position
     */
    public Position getEnd() {
        return this.end;
    }

    /**
     * Returns a message whether the move made is valid, a player made a simple
     * move when a jump was available, or the move is straight up invalid.
     * @param possibleMoves- list of moves the player can take
     * @param isJumps- the moves are jump moves
     * @return Message- message describing the outcome of this move
     */
    public Message validateMove(HashSet<Move> possibleMoves, boolean isJumps)
    {
        System.out.println("This move is " + this);
        if(possibleMoves.contains(this))
            return new Message("Valid move", MessageType.info);
        else if(isJumps)
            return new Message("Invalid, must make jump move", MessageType.error);
        else
            return new Message("Invalid move", MessageType.error);
    }

    /**
     * Flips this move when a white player makes a move
     */
    public void flipMove() {
        System.out.println(this);
        this.start = new Position(BOARD_MAX-getStart().getRow(), BOARD_MAX-getStart().getCell());
        this.end = new Position(BOARD_MAX-getEnd().getRow(), BOARD_MAX-getEnd().getCell());
        System.out.println(this);
    }

    /**
     * Returns whether this move is a jump move or not
     * @return boolean- move is a jump or its not
     */
    public boolean isJump()
    {
        int rowsJumped = Math.abs(this.start.getRow() - this.end.getRow());
        return rowsJumped == 2;
    }

    /**
     * Get the position of a piece that was taken after a jump move is made
     * @return takenPiecePos- position of taken piece
     */
    public Position getOpponentPiece()
    {
        Position takenPiecePos;
        int takenPieceRow = (start.getRow() + end.getRow()) / 2;
        int takenPieceCol = (start.getCell() + end.getCell()) / 2;
        takenPiecePos = new Position(takenPieceRow, takenPieceCol);
        return takenPiecePos;
    }

    /**
     * Defining how to compare if a Move is equivalent to another one
     * @param obj- object being passed in
     * @return boolean- whether the two moves are equivalent
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Move other = (Move) obj;
        if(!other.getStart().equals(this.getStart()))
            return false;
        if(!other.getEnd().equals(this.getEnd()))
            return false;
        return true;
    }

    /**
     * Defining hashcode needed for comparing Moves
     * @return result- hashcode calculated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (start.getRow() + start.getCell());
        result = prime * result + (end.getRow() + end.getCell());
        return result;
    }


    @Override
    public String toString()
    {
        return ("Move: Start[" + start.getRow() + "," + start.getCell() + "] End[" +
                end.getRow() + "," + end.getCell() + "]");
    }
}
