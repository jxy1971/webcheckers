package com.webcheckers.model.Board;


import com.webcheckers.model.Board.Enums.Color;
import com.webcheckers.model.Board.Enums.Type;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;

import java.util.*;

public class MoveValidator {
    private BoardView board;
    public boolean isRedPlayer;
    private HashSet<Move> simpleMoves;
    private HashSet<Move> jumpMoves;
    private MoveValidatorRed mvr;
    private MoveValidatorWhite mvw;

    /**
     * Constructor for MoveValidator
     * @param board Boardview
     * @param isRed Whether or not the active player is red
     */
    public MoveValidator(BoardView board, boolean isRed) {
        this.board = board;
        mvr = new MoveValidatorRed();
        mvw = new MoveValidatorWhite();
        this.isRedPlayer = isRed;
        simpleMoves = new HashSet<>();
        jumpMoves = new HashSet<>();
    }

    /**
     * Checks to see if a player is able to move or jump
     * @return Whether or not the player is able to move or jump
     */
    public boolean genAllPossibleMoves() {
        simpleMoves.clear();
        jumpMoves.clear();
        ArrayList<Row> rows = board.getBoardArray();
        if(isRedPlayer){
            return genAllPossibleMovesRed(rows);
        }
        else{
            return genAllPossibleMovesWhite(rows);
        }
    }

    /**
     * Sub-method of genAllPossiblesMoves that is called when it is red player's turn
     * Branches out to other methods
     * @param rows ArrayList representation of the current state of the game board
     * @return Whether or not the player is able to move or jump
     */
    private boolean genAllPossibleMovesRed(ArrayList<Row> rows){
        Space redSpace = null;
        for (int i = 7; i >= 0; i--) {
            Row row = rows.get(i);
            Row ckrow = null;
            if (i-1>=0) {
                ckrow = rows.get(i - 1);
            }
            Row kingckrow = null;
            if(i+1<=7){
                kingckrow = rows.get(i+1);
            }

            for (int j = 0; j < 8; j++) {
                if (row.getSpace(j).isColored() && row.getSpace(j).getPiece() != null && row.getSpace(j).getPiece().getColor() == Color.RED) {
                    redSpace = row.getSpace(j);
                } else {
                    redSpace = null;
                }
                if (redSpace != null) {
                    if (j - 1 >= 0) {
                        mvr.genAllPossibleMovesRedMinus(i,j,ckrow,kingckrow,redSpace,rows,simpleMoves,jumpMoves);
                    }
                    if (j + 1 <= 7) {
                        mvr.genAllPossibleMovesRedPlus(i,j,ckrow,kingckrow,redSpace,rows, simpleMoves, jumpMoves); }
                }
            }
        }
        return !simpleMoves.isEmpty() || !jumpMoves.isEmpty();
    }

    /**
     * Sub-method of genAllPossiblesMoves that is called when it is white player's turn
     * Branches out to other methods
     * @param rows ArrayList representation of the current state of the game board
     * @return Whether or not the player is able to move or jump
     */
    private boolean genAllPossibleMovesWhite(ArrayList<Row> rows) {
        Space whiteSpace = null;
        for (int i = 0; i <= 7; i++) {
            Row row = rows.get(i);
            Row ckrow = null;
            if (i+1<=7) {
                ckrow = rows.get(i + 1);
            }
            Row kingckrow = null;
            if(i-1>=0){
                kingckrow = rows.get(i-1);
            }
            for (int j = 0; j < 8; j++) {
                if (row.getSpace(j).isColored() && row.getSpace(j).getPiece() != null && row.getSpace(j).getPiece().getColor() == Color.WHITE) {
                    whiteSpace = row.getSpace(j);
                } else {
                    whiteSpace = null;
                }
                if (whiteSpace != null) {
                    if (j - 1 >= 0) {
                        mvw.genAllPossibleMovesWhiteMinus(i,j,ckrow,kingckrow,whiteSpace,rows,simpleMoves,jumpMoves);
                    }
                    if (j + 1 <= 7) {
                        mvw.genAllPossibleMovesWhitePlus(i,j,ckrow,kingckrow,whiteSpace,rows,simpleMoves,jumpMoves);
                    }
                }
            }
        }
        return !simpleMoves.isEmpty() || !jumpMoves.isEmpty();
    }


    /**
     * Gets the possible jumps a piece of a certain position can take
     * @param pos The position of the piece in question
     * @param isKing Whether or not the piece is king
     * @param cantReturn ArrayList of positions the piece cannot jump back to.
     * @return whether or not there are any possible jumps for the piece
     */
    public boolean getPossibleJumpsOnPosition(Position pos, Boolean isKing, ArrayList<Position> cantReturn) {
        simpleMoves.clear();
        jumpMoves.clear();
        ArrayList<Row> rows = board.getBoardArray();
        if(isRedPlayer){
            return getPossibleJumpsOnPositionRed(rows,pos,isKing, cantReturn);
        }
        else{
            return getPossibleJumpsOnPositionWhite(rows,pos, isKing, cantReturn);
        }
    }

    /**
     * Sub-method of getPossibleJumpsOnPosition that is called when the piece given
     * was the red player's.
     * @param rows ArrayList representation of the current state of the game board
     * @param pos The position of the piece in question
     * @param isKing Whether or not the piece is king
     * @param cantReturn ArrayList of positions the piece cannot jump back to
     * @return whether or not there are any possible jumps for the piece
     */
    public boolean getPossibleJumpsOnPositionRed(ArrayList<Row> rows, Position pos, boolean isKing,ArrayList<Position> cantReturn){
        boolean noAdd = false;
        int row = pos.getRow();
        int cell = pos.getCell();
        if (cell - 1 >= 0) {
            mvr.getPossibleJumpsOnPositionRedMinus(row,cell,pos,rows,cantReturn,noAdd,isKing,jumpMoves);
            noAdd = false;
        }
        if (cell + 1 <= 7) {
            mvr.getPossibleJumpsOnPositionRedPlus(row,cell,pos,rows,cantReturn,noAdd,isKing,jumpMoves);
        }

        return !jumpMoves.isEmpty();
    }

    /**
     * Sub-method of getPossibleJumpsOnPosition that is called when the piece given
     * was the white player's.
     * @param rows ArrayList representation of the current state of the game board
     * @param pos The position of the piece in question
     * @param isKing Whether or not the piece is king
     * @param cantReturn ArrayList of positions the piece cannot jump back to
     * @return whether or not there are any possible jumps for the piece
     */
    private boolean getPossibleJumpsOnPositionWhite(ArrayList<Row> rows, Position pos, boolean isKing, ArrayList<Position> cantReturn){
        boolean noAdd = false;
        int row = pos.getRow();
        int cell = pos.getCell();
        if (cell - 1 >= 0) {
            mvw.getPossibleJumpsOnPositionWhiteMinus(row,cell,pos,rows,cantReturn,noAdd,isKing,jumpMoves);
            noAdd = false;
        }
        if (cell + 1 <= 7) {
            mvw.getPossibleJumpsOnPositionWhitePlus(row,cell,pos,rows,cantReturn,noAdd,isKing,jumpMoves);
        }
        return !jumpMoves.isEmpty();
    }


    /**
     * Prints whether or not the sets simpleMoves and jumpMoves
     * are empty or  have moves within them
     */
    public void printSet()
    {
        if(simpleMoves.isEmpty())
            System.out.println("No simple moves");
        else {
            System.out.println("Simple Moves");
            for (Move move : simpleMoves)
                System.out.println(move);
        }
        if(jumpMoves.isEmpty())
            System.out.println("No jump moves");
        else {
            System.out.println("Jump Moves");
            for (Move move : jumpMoves)
                System.out.println(move);
        }
    }

    /**
     * Gets jumpMoves set
     * @return jumpMoves set
     */
    public HashSet<Move> getJumpMoves(){return jumpMoves;}

    /**
     * Checks whether or not the player has moves
     * @return whether or not the player has moves
     */
    public boolean hasMoves() { return !simpleMoves.isEmpty() || !jumpMoves.isEmpty(); }

    /**
     * Gets set of simpleMoves if no jumpMoves are available,
     * otherwise gets set of jumpMoves
     * @return
     */
    public HashSet<Move> getPossibleMoves()
    {
        if(jumpMoves.isEmpty())
            return simpleMoves;
        else
            return jumpMoves;
    }

    /**
     * Checks if jumpMoves are available
     * @return whether or not jumpMoves are available
     */
    public boolean jumpMoveAvailable() { return !jumpMoves.isEmpty(); }

    /**
     * Switches the active player
     */
    public void switchActivePlayer()
    {
        simpleMoves.clear();
        jumpMoves.clear();
        if(isRedPlayer)
            isRedPlayer = false;
        else
            isRedPlayer = true;
    }
}
