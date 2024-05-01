package be.kuleuven.candycrush.model.records;

import java.util.ArrayList;

public record BoardSize(int cols, int rows) {

    public BoardSize{
        if(0 >= cols){
            throw new IllegalArgumentException("number of cols:" + cols +" must be greater then 0");
        }
        if(0 >= rows){
            throw new IllegalArgumentException("number of cols:" + cols +" must be greater then 0");
        }
    }

    public int to1DSize(){
        return cols*rows;
    }

    public Iterable<Position> positions(){
        final ArrayList<Position> positions = new ArrayList<>();
        for(int row=0; row<rows; row++){
            for(int col = 0; col< cols; col++){
                positions.add(new Position(col, row, this));
            }
        }
        return positions;
    }
}
