package be.kuleuven.neighbourchecklibrary;


import be.kuleuven.neighbourchecklibrary.exceptions.GridSizeNotMatchException;

import java.util.ArrayList;
import java.util.HashMap;


public class CheckNeighboursInGrid {
    /**
     * This method takes a 1D Iterable and an element in the array and gives back an iterable containing the indexes of all neighbours with the same value as the specified element
     *@return - Returns a 1D Iterable of ints, the Integers represent the indexes of all neighbours with the same value as the specified element on index 'indexToCheck'.
     *@param grid - This is a 1D Iterable containing all elements of the grid. The elements are integers.
     *@param width - Specifies the width of the grid.
     *@param height - Specifies the height of the grid (extra for checking if 1D grid is complete given the specified width)
     *@param indexToCheck - Specifies the index of the element which neighbours that need to be checked
     */
    public static Iterable<Integer> getSameNeighboursIds(Iterable<Integer> grid ,int width, int height, int indexToCheck){
        //hashmap: key=index, value=value
        final HashMap<Integer, Integer> neighbours = new HashMap<>();
        int gridSize = 0;
        //y neighbour = index +- width
        //x neighbour = index +- 1
        while(grid.iterator().hasNext()){
            final int currentValue = grid.iterator().next();

            gridSize ++;
        }
        //error als dimenties niet kloppen
        if(gridSize != width*height){   //current index = size van de grid na de while loop klaar is
            throw new GridSizeNotMatchException(width, height, gridSize);
        }
        //verwijder neighbours zonder zelfde waarde
        return neighbours.keySet();
    }

}