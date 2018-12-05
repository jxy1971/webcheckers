package com.webcheckers.model;

/**
 * coordinates of a cell
 */
public class Position {
    // coordinates
    private int row, cell;

    /**
     * constructor
     * @param row x
     * @param cell y
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * the row of this position
     * @return int
     */
    public int getRow() {
        return this.row;
    }

    /**
     * the column of this position
     * @return int
     */
    public int getCell() {
        return this.cell;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (other.getRow() != this.getRow())
            return false;
        if (other.getCell() != this.getCell())
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return ("[" + this.getRow() + "," + this.getCell() + "]");
    }
}
