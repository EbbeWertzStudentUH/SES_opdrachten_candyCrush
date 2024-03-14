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
        int currentIndex = 0;
        int valueOnIndexToCheck = 0;
        final Coordinate coordOfIndexToCheck = new Coordinate(indexToCheck, width);
        for (int currentValue : grid) {

            if (indexToCheck == currentIndex) {
                valueOnIndexToCheck = currentValue;
            }
            final Coordinate currentCoord = new Coordinate(currentIndex, width);
            if(currentCoord.isNeighbour(coordOfIndexToCheck)){
                neighbours.put(currentIndex, currentValue);
            }
            currentIndex++;
        }
        final int size = currentIndex;
        //error als dimenties niet kloppen
        if(size != width*height){   //current index = size van de grid na de while loop klaar is
            throw new GridSizeNotMatchException(width, height, size);
        }
        //verwijder indexen zonder zelfde value of buiten grid size:
        return filterOnEqualValue(neighbours, valueOnIndexToCheck);
    }

    private static ArrayList<Integer> filterOnEqualValue(HashMap<Integer, Integer> neighbours, int valueOnIndexToCheck) {
        ArrayList<Integer> result = new ArrayList<>();
        //return niet keyset van hashmap want hashmap kan tijdens for niet ge-modified.
        //Anders moeten 2 loops -> minder efficient. Daarom aparte arraylist
        for(HashMap.Entry<Integer, Integer> entry : neighbours.entrySet()){
            if(entry.getValue().equals(valueOnIndexToCheck)){
                //equals ipv == omdat Integer een wrapper class is en dus een eigen ref heeft
                result.add(entry.getKey());
            }
        }
        return result;
    }

    private static class Coordinate{
        protected int x;
        protected int y;

        protected Coordinate(int index, int width){
            x = index % width;
            y = index / width;
        }
        protected boolean isNeighbour(Coordinate coordinate){
            int deltaX = Math.abs(coordinate.x - x);
            int deltaY = Math.abs(coordinate.y - y);
            boolean equal = deltaX == 0 && deltaY == 0;
            return !equal && deltaY <= 1 && deltaX <= 1;
        }
    }



}