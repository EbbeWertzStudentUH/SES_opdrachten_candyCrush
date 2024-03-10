package be.kuleuven.neighbourchecklibrary;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

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
        ArrayList<Integer> neighbours = new ArrayList<>();  //extra handig want arraylist inherit al van Iterable
        while(grid.iterator().hasNext()){
            final int currentValue = grid.iterator().next();

        }
        return neighbours;
    }

    private int[] indexNaarCoordinaat(int width, int height, int index){
        final int totaleSize = width*height;
        if(index >= totaleSize || index < 0){
            throw new IndexOutOfBoundsException("index "+index+" ligt buiten "+width+"x"+height+" grid");
        }
        int[] coordinaat = new int[2];  //index 0=x, 1=y
        coordinaat[0] = index / width;
        coordinaat[1] = index % width;
        return coordinaat;
    }

}