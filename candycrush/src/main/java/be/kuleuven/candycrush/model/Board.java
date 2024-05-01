package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.model.records.BoardSize;
import be.kuleuven.candycrush.model.records.Position;

import java.util.ArrayList;
import java.util.function.Function;

public class Board<T>{

    private final ArrayList<T> board;

    private final BoardSize boardSize;

    public Board(BoardSize boardSize) {
        this.boardSize = boardSize;
        board = new ArrayList<>();
    }
    //om te clonen
    public Board(Board<T> board){
        this.boardSize = board.boardSize;
        this.board = new ArrayList<>(board.board);
    }

    public T getCellAt(Position position){
        validatePosition(position);
        return board.get(position.toIndex());
    }
    public void replaceCellAt(Position position, T newCell){
        validatePosition(position);
        board.set(position.toIndex(), newCell);
    }
    public void fill(Function<Position, ? extends T> cellCreator){
        board.clear();
        for(Position position : boardSize.positions()){
            board.add(cellCreator.apply(position));
        }
    }
    public void copyTo(Board<? super T> otherBoard){
        if(!otherBoard.boardSize.equals(boardSize)){
            throw new IllegalArgumentException("Boardsize of other board: "+otherBoard.boardSize+" does not match the boardsize of this board:"+boardSize);
        }
        otherBoard.board.clear();
        otherBoard.board.addAll(board);
    }

    private void validatePosition(Position position){
        if(! position.boardSize().equals(boardSize)){
            throw new IllegalArgumentException("Position: "+position+" does not match the boardsize of the board");
        }
    }

    public BoardSize getBoardSize() {
        return boardSize;
    }

}

