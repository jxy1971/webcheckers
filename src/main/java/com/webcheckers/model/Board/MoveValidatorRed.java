package com.webcheckers.model.Board;

import com.webcheckers.model.Board.Enums.Color;
import com.webcheckers.model.Board.Enums.Type;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;

import java.util.ArrayList;
import java.util.HashSet;

public class MoveValidatorRed {
    private MoveValidatorRedKing mvrk;

    /**
     * Constructor for MoveValidatorRed
     */
    public MoveValidatorRed(){
        mvrk = new MoveValidatorRedKing();
    }
    /**
     * Sub-Method of genAllPossibleMovesRed that is called if a red player piece
     * has the possibility of being able to move to space cell-1
     * @param i The row index
     * @param j The cell index
     * @param ckrow The row in front of the space
     * @param kingckrow The row behind the space
     * @param redSpace The space in question
     * @param rows ArrayList representation of the current state of the game board
     */
    public void genAllPossibleMovesRedMinus(int i, int j, Row ckrow, Row kingckrow, Space redSpace, ArrayList<Row> rows, HashSet<Move> simpleMoves, HashSet<Move> jumpMoves){
        if (ckrow != null) {
            if (ckrow.getSpace(j - 1).getPiece() == null) {
                simpleMoves.add(new Move(new Position(i, j), new Position(i - 1, j - 1)));
            } else if (ckrow.getSpace(j - 1).getPiece().getColor() == Color.WHITE && i - 2 >= 0 && j - 2 >= 0) {
                Space ckJumpSpace = rows.get(i - 2).getSpace(j - 2);
                if (ckJumpSpace.getPiece() == null) {
                    jumpMoves.add(new Move(new Position(i, j), new Position(i - 2, j - 2)));
                }
            }
        }
        if(redSpace.getPiece().getType()== Type.KING) {
            if (kingckrow != null) {
                mvrk.genAllPossibleMovesRedMinusKing(i, j, kingckrow, rows, simpleMoves, jumpMoves);
            }
        }
    }

    /**
     * Sub-Method of genAllPossibleMovesRed that is called if a red player piece
     * has the possibility of being able to move to space cell+1
     * @param i The row index
     * @param j The cell index
     * @param ckrow The row in front of the space
     * @param kingckrow The row behind the space
     * @param redSpace The space in question
     * @param rows ArrayList representation of the current state of the game board
     */
     public void genAllPossibleMovesRedPlus(int i, int j, Row ckrow, Row kingckrow, Space redSpace, ArrayList<Row> rows, HashSet<Move> simpleMoves, HashSet<Move> jumpMoves){
        if(ckrow != null) {
            if (ckrow.getSpace(j + 1).getPiece() == null) {
                simpleMoves.add(new Move(new Position(i, j), new Position(i - 1, j + 1)));
            } else if (ckrow.getSpace(j + 1).getPiece().getColor() == Color.WHITE && i - 2 >= 0 && j + 2 <= 7) {
                Space ckJumpSpace = rows.get(i - 2).getSpace(j + 2);
                if (ckJumpSpace.getPiece() == null) {
                    jumpMoves.add(new Move(new Position(i, j), new Position(i - 2, j + 2)));
                }
            }
        }
        if(redSpace.getPiece().getType()==Type.KING && kingckrow !=null){
            mvrk.genAllPossibleMovesRedPlusKing(i,j,kingckrow,rows, simpleMoves, jumpMoves);
        }
    }

    /**
     * Sub-method of getPossibleHumpsOnPositionRed that is called when the piece given
     * has the possibility of moving onto a space at cell-1
     * @param row The row of the piece
     * @param cell The cell of the piece
     * @param pos The position of the piece
     * @param rows ArrayList representation of the current state of the game board
     * @param cantReturn ArrayList of positions the piece cannot jump back to
     * @param noAdd Whether or not a possible position can be added to jumpMoves
     * @param isKing Whether or not the piece is king
     */
    public void getPossibleJumpsOnPositionRedMinus(int row,int cell, Position pos, ArrayList<Row> rows, ArrayList<Position> cantReturn, boolean noAdd, boolean isKing, HashSet<Move> jumpMoves){
        if (row - 1 >= 0) {
            if (rows.get(row - 1).getSpace(cell - 1).getPiece() != null) {
                if (row - 2 >= 0 && cell - 2 >= 0 && rows.get(row - 1).getSpace(cell - 1).getPiece().getColor() == Color.WHITE) {
                    if (rows.get(row - 2).getSpace(cell - 2).getPiece() == null) {
                        if(cantReturn!=null) {
                            for (Position ck: cantReturn) {
                                if (ck.getRow()==row-2 && ck.getCell()==cell-2) {
                                    noAdd = true;
                                }
                            }
                        }
                        if(!noAdd) {
                            jumpMoves.add(new Move(pos, new Position(row - 2, cell - 2)));
                        }
                        noAdd = false;
                    }
                }
            }
        }
        if(isKing){
            mvrk.getPossibleJumpsOnPositionRedMinusKing(row,cell,pos,rows,cantReturn,noAdd,jumpMoves);
        }
    }

    /**
     * Sub-method of getPossibleHumpsOnPositionRed that is called when the piece given
     * has the possibility of moving onto a space at cell+1
     * @param row The row of the piece
     * @param cell The cell of the piece
     * @param pos The position of the piece
     * @param rows ArrayList representation of the current state of the game board
     * @param cantReturn ArrayList of positions the piece cannot jump back to
     * @param noAdd Whether or not a possible position can be added to jumpMoves
     * @param isKing Whether or not the piece is king
     */
    public void getPossibleJumpsOnPositionRedPlus(int row,int cell, Position pos, ArrayList<Row> rows, ArrayList<Position> cantReturn, boolean noAdd, boolean isKing, HashSet<Move> jumpMoves){
        if (row - 1 >= 0) {
            if (rows.get(row - 1).getSpace(cell + 1).getPiece() != null) {
                if ((row - 2 >= 0 && cell + 2 <= 7) &&
                        rows.get(row - 1).getSpace(cell + 1).getPiece().getColor() == Color.WHITE) {
                    if (rows.get(row - 2).getSpace(cell + 2).getPiece() == null) {
                        if(cantReturn!=null) {
                            for (Position ck: cantReturn) {
                                if (ck.getRow()==row-2 && ck.getCell()==cell+2) {
                                    noAdd = true;
                                }
                            }
                        }
                        if(!noAdd) {
                            jumpMoves.add(new Move(pos, new Position(row - 2, cell + 2)));
                        }
                        noAdd = false;
                    }
                }
            }
        }
        if(isKing){
            mvrk.getPossibleJumpsOnPositionRedPlusKing(row,cell,pos,rows,cantReturn,noAdd,jumpMoves);
        }
    }


}
