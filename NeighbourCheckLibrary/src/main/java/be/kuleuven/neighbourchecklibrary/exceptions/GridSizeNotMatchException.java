package be.kuleuven.neighbourchecklibrary.exceptions;

public class GridSizeNotMatchException extends RuntimeException{
    public GridSizeNotMatchException(int width, int height, int size){
        super("The size of the grid: "+size+"does not match the dimensions: "+width+"x"+height);
    }
}
