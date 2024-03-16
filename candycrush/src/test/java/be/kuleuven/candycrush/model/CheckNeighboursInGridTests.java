package be.kuleuven.candycrush.model;

import be.kuleuven.neighbourchecklibrary.CheckNeighboursInGrid;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static be.kuleuven.neighbourchecklibrary.CheckNeighboursInGrid.getSameNeighboursIds;
import static org.assertj.core.api.Assertions.assertThat;

public class CheckNeighboursInGridTests {

    @Test
    public void returnt_niet_diagonale_neighbours(){
        // 1. Arrange
        int[] grid = {  1,1,2,1,
                        1,2,2,2,
                        1,1,2,1};

        // 2. Act
        int[] result =getSaneNeighBoursMetIntArray(grid, 4, 3, 6);
        // 3. Assert
        assertThat(result).isEqualTo(new int[]{2, 5, 7, 10});
    }

    private int[] getSaneNeighBoursMetIntArray(int[] grid, int width, int height, int indexToCheck){
        ArrayList<Integer> gridArrayList = new ArrayList<>();
        for(int i : grid){
            gridArrayList.add(i);
        }
        ArrayList<Integer> resultArrayList = (ArrayList<Integer>) getSameNeighboursIds(gridArrayList, width, height, indexToCheck);
        int[] result = new int[resultArrayList.size()];
        int index = 0;
        for(int i : resultArrayList){
            result[index] = i;
            index++;
        }
        return result;
    }

}
