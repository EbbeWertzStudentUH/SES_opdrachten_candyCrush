package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.model.records.BoardSize;
import be.kuleuven.candycrush.model.records.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Board<T>{

    private final Map<Position, T> board;

    private final BoardSize boardSize;

    public Board(BoardSize boardSize) {
        this.boardSize = boardSize;
        board = new HashMap<>();
    }
    //om te clonen
    public Board(Board<T> board){
        this.boardSize = board.boardSize;
        this.board = new HashMap<>(board.board);
    }

    public T getCellAt(Position position){
        validatePosition(position);
        return board.get(position);
    }
    public void replaceCellAt(Position position, T newCell){
        validatePosition(position);
        board.put(position, newCell);
    }
    public void fill(Function<Position, ? extends T> cellCreator){
        board.clear();
        for(Position position : boardSize.positions()){
            board.put(position, cellCreator.apply(position));
        }
    }
    public void copyTo(Board<? super T> otherBoard){
        if(!otherBoard.boardSize.equals(boardSize)){
            throw new IllegalArgumentException("Boardsize of other board: "+otherBoard.boardSize+" does not match the boardsize of this board:"+boardSize);
        }
        for(Position position : boardSize.positions()){
            T item = board.get(position);
            otherBoard.board.put(position, item);
        }
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

