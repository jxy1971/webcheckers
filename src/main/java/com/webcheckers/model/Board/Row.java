package com.webcheckers.model.Board;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Iterable class used to implement spaces in game.ftl
 */
public class Row implements Iterable<Space> {
    private int index;
    private ArrayList<Space> spaces;

    /**
     * Constructor that creates a new ArrayList of Space objects
     * and sets the index of this row
     * @param index- identifies this particular row
     */
    public Row(int index)
    {
        this.index = index;
        spaces = new ArrayList<Space>();
    }

    /**
     * Gets the index of this row
     * @return index- id of this particular row
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * adds a new space to the spaces array
     * @param newSpace- added space object
     */
    public void addSpace(Space newSpace)
    {
        spaces.add(newSpace);
    }

    /**
     * Using the index of a space within a row return that Space object
     * @param index- index of the space needed to be returned
     * @return Space object at index
     */
    public Space getSpace(int index) {
        return spaces.get(index);
    }

    public Space createWhiteSpace(int index)
    {
        Space newSpace = new Space(spaces.get(index));
        return newSpace;
    }

    /**
     * Implementation of hasNext and next iterator functions of this class.
     * Used for iterating and setting properties of every Space by the game.ftl file
     * @return new Iterator<Space> object
     */
    @Override
    public Iterator<Space> iterator() {
        return new Iterator<Space>() {

            int indexPosition = 0;

            @Override
            public boolean hasNext() {
                if(indexPosition >= spaces.size())
                    return false;

                return true;
            }

            @Override
            public Space next() {
                Space val = spaces.get(indexPosition);
                indexPosition++;
                return val;
            }

        };
    }

    /**
     * Function used to print useful information about this class's instance
     * @return formatted String
     */
    @Override
    public String toString() {
        return String.format("Row: " + index);
    }
}
