package com.webcheckers.model.Board;

import com.webcheckers.model.Board.Enums.Color;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;

import java.util.ArrayList;
import java.util.HashSet;

public class MoveValidatorWhiteKing {
    /**
     * Sub-Method of genAllPossibleMovesWhiteMinus that is called if a white player piece
     * is king and has the possibility of being able to move to space cell-1
     * @param i The row index
     * @param j The cell index
     * @param kingckrow The row behind the space
     * @param rows ArrayList representation of the current state of the game board
     */
    public void genAllPossibleMovesWhiteMinusKing(int i, int j, Row kingckrow, ArrayList<Row> rows, HashSet<Move> simpleMoves, HashSet<Move> jumpMoves){
        if(kingckrow.getSpace(j - 1).getPiece() == null){
            simpleMoves.add(new Move(new Position(7 - i, 7 - j), new Position(7 - i + 1, 7 - j + 1)));
        } else if (kingckrow.getSpace(j-1).getPiece().getColor() == Color.RED && i - 2 >= 0 && j - 2 >= 0){
            Space kingckJumpSpace = rows.get(i - 2).getSpace(j-2);
            if (kingckJumpSpace.getPiece() == null){
                jumpMoves.add(new Move(new Position(7-i,7-j),new Position(7-i+2,7-j+2)));
            }
        }
    }

    /**
     * Sub-Method of genAllPossibleMovesWhitePlus that is called if a white player piece
     * is king and has the possibility of being able to move to space cell+1
     * @param i The row index
     * @param j The cell index
     * @param kingckrow The row behind the space
     * @param rows ArrayList representation of the current state of the game board
     */
    public void genAllPossibleMovesWhitePlusKing(int i, int j, Row kingckrow, ArrayList<Row> rows, HashSet<Move> simpleMoves, HashSet<Move> jumpMoves){
        if(kingckrow.getSpace(j + 1).getPiece() == null){
            simpleMoves.add(new Move(new Position(7 - i, 7 - j), new Position(7 - i + 1, 7 - j - 1)));
        } else if (kingckrow.getSpace(j+1).getPiece().getColor() == Color.RED && i - 2 >= 0 && j + 2 <= 7){
            Space kingckJumpSpace = rows.get(i - 2).getSpace(j+2);
            if (kingckJumpSpace.getPiece() == null){
                jumpMoves.add(new Move(new Position(7-i,7-j),new Position(7-i+2,7-j-2)));
            }
        }
    }

    /**
     * Sub-method of getPossibleJumpsOnPositionWhiteMinus that is called when the piece given
     * is a king
     * @param row The row of the piece
     * @param cell The cell of the piece
     * @param pos The position of the piece
     * @param rows ArrayList representation of the current state of the game board
     * @param cantReturn ArrayList of positions the piece cannot jump back to
     * @param noAdd Whether or not a possible position can be added to jumpMoves
     */
    public void getPossibleJumpsOnPositionWhitedMinusKing(int row,int cell,Position pos, ArrayList<Row> rows, ArrayList<Position> cantReturn, boolean noAdd, HashSet<Move> jumpMoves){
        if (row - 1 >= 0) {
            if (row - 2 >= 0 && cell - 2 >= 0 &&
                    rows.get(row - 1).getSpace(cell - 1).getPiece() != null) {
                if (rows.get(row - 1).getSpace(cell - 1).getPiece().getColor() == Color.RED &&
                        rows.get(row - 2).getSpace(cell - 2).getPiece() == null) {
                    if(cantReturn!=null) {
                        for (Position ck: cantReturn) {
                            if (ck.getRow()==row-2 && ck.getCell()==cell-2) {
                                noAdd = true;
                            }
                        }
                    }
                    if(!noAdd) {
                        jumpMoves.add(new Move(new Position(7 - row, 7 - cell), new Position(7 - row + 2, 7 - cell + 2)));
                    }
                }
            }
        }
    }

    /**
     * Sub-method of getPossibleJumpsOnPositionWhitePlus that is called when the piece given
     * is a king
     * @param row The row of the piece
     * @param cell The cell of the piece
     * @param pos The position of the piece
     * @param rows ArrayList representation of the current state of the game board
     * @param cantReturn ArrayList of positions the piece cannot jump back to
     * @param noAdd Whether or not a possible position can be added to jumpMoves
     */
    public void getPossibleJumpsOnPositionWhitedPlusKing(int row,int cell,Position pos, ArrayList<Row> rows, ArrayList<Position> cantReturn, boolean noAdd, HashSet<Move> jumpMoves){
        if (row - 1 >= 0) {
            if (row - 2 >= 0 && cell + 2 <= 7 &&
                    rows.get(row - 1).getSpace(cell + 1).getPiece() != null) {
                if (rows.get(row - 1).getSpace(cell + 1).getPiece().getColor() == Color.RED &&
                        rows.get(row - 2).getSpace(cell + 2).getPiece() == null) {
                    if(cantReturn!=null) {
                        for (Position ck: cantReturn) {
                            if (ck.getRow()==row-2 && ck.getCell()==cell+2) {
                                noAdd = true;
                            }
                        }
                    }
                    if(!noAdd) {
                        jumpMoves.add(new Move(new Position(7 - row, 7 - cell), new Position(7 - row + 2, 7 - cell - 2)));
                    }
                }
            }
        }
    }
}
