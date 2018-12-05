package com.webcheckers.model.Board;

import com.webcheckers.model.Board.Enums.Color;
import com.webcheckers.model.Board.Enums.Type;

/**
 * An object representing a space on the board
 */
public class Space {
    
    /**
     * the index of the cell
     */
    private int cellIdx;

    /**
     * the piece at this space
     */
    private Piece piece;

    /**
     * if this space is a colored space
     */
    private boolean isColored;

    public Space(int cellIndex, boolean isColored)
    {
        this.cellIdx = cellIndex;
        this.isColored = isColored;
    }

    public Space(Space originalSpace)
    {
        cellIdx = 7 - originalSpace.getCellIdx();
        isColored = originalSpace.isColored;
        this.piece = originalSpace.getPiece();
    }

    /**
     * 
     * @return cell index
     */
    public int getCellIdx()
    {
        return cellIdx;
    }

    /**
     * 
     * @return if the space is valid for a piece
     */
    public boolean isValid()
    {
        return (isColored && piece == null);
    }

    /**
     * Create Piece
     *
     * @param type if a piece is a king or regular
     * @param color the color of the piece
     */
    public void createPiece(Type type, Color color)
    {
        if(this.piece == null)
            this.piece = new Piece(type, color);
    }

    /**
     * @return the piece at this space
     */
    public Piece getPiece()
    {
        return this.piece;
    }

    /**
     * 
     * @param newPiece the piece to put on this space
     */
    public void setPiece(Piece newPiece) {
        this.piece = newPiece;
    }

    /**
     * removes the piece on the space
     */
    public Piece removePiece()
    {
        Piece ret = this.piece;
        this.piece = null;
        return ret;
    }


    @Override
    public String toString() {
        if(this.piece != null){
            return String.format(" Space: " + cellIdx + " Piece: " + this.piece.getType());
        }
        else{
            return String.format(" Space: " + cellIdx + " Piece: NONE");
        }
    }

    public boolean isColored(){return isColored;}

}
