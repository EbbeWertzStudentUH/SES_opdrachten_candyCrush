package be.kuleuven.candycrush.model.records;

import java.util.ArrayList;

public record Position(int col, int row, BoardSize boardSize) {

    public Position{
        int w = boardSize.cols();
        int h = boardSize.rows();
        boolean validH = isInBounds(col, w);
        boolean validV = isInBounds(row, h);
        if(!(validH && validV)){
            throw new IllegalArgumentException(this+" is not in the range of "+boardSize);
        }
    }

    public int toIndex(){
        return row * boardSize().cols() + col;
    }

    public static Position fromIndex(int index, BoardSize size){
        final int col = index % size.cols();
        final int row  = index / size.cols();
        return new Position(col, row, size);
    }

    boolean isLastColumn(){
        return col == boardSize().cols() - 1;
    }

    public Iterable<Position> neighborPositions(){
        final ArrayList<Position> neighbours = new ArrayList<>();

        for(int xoffset = -1; xoffset <= 1; xoffset++){
            final int x = col + xoffset;

            if(!isInBounds(x, boardSize().cols())){
                continue;
            }

            for(int yoffset = -1; yoffset <= 1; yoffset++) {
                final int y = row + yoffset;

                final boolean isThisPosition = x == col && y == row;
                if(isInBounds(y, boardSize().rows()) && !isThisPosition){
                    neighbours.add(new Position(x, y, boardSize));
                }
            }
        }
        return neighbours;
    }

    private boolean isInBounds(int num, int lim){
        return num >= 0 && num < lim;
    }
}
