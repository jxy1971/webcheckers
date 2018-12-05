package com.webcheckers.model.Board;

import com.webcheckers.model.Board.Enums.Color;
import com.webcheckers.model.Board.Enums.Type;

/*
 * Object for checkers piece. Has a type (single or king)
 * and color (white or red)
 */

public class Piece {
    private Type type;
    private Color color;


    // Constructor
    public Piece(Type type, Color color)
    {
        this.type = type;
        this.color = color;
    }

    // Getters
    public Type getType()
    {
        return type;
    }

    public Color getColor()
    {
        return color;
    }

    public void setKing()
    {
        this.type = Type.KING;
    }

    public void setSingle() {
        this.type = Type.SINGLE;
    }

}
