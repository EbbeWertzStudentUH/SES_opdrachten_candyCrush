package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.model.records.BoardSize;
import be.kuleuven.candycrush.model.records.Position;

import java.util.*;
import java.util.function.Function;

public class Board<T>{

    private final Map<Position, T> board;
    private final Map<T, Set<Position>> reverseBoard;

    private final BoardSize boardSize;

    public Board(BoardSize boardSize) {
        this.boardSize = boardSize;
        board = new HashMap<>();
        reverseBoard = new HashMap<>();
        fill(p -> null);
    }
    //om te clonen
    public Board(Board<T> board){
        boardSize = board.boardSize;
        this.board = new HashMap<>(board.board);
        reverseBoard = new HashMap<>(board.reverseBoard);
    }

    public T getCellAtPosition(Position position){
        validatePosition(position);
        return board.get(position);
    }

    public void replaceCellAtPosition(Position position, T newCell){
        validatePosition(position);
        T oldCell = getCellAtPosition(position);
        //als de nieuwe cell geen positions heeft, initialiseer positions
        reverseBoard.computeIfAbsent(newCell, c -> new HashSet<>());
        //voeg position toe aan nieuwe cell
        reverseBoard.get(newCell).add(position);
        //verwijder position van oude cell
        reverseBoard.get(oldCell).remove(position);
        board.put(position, newCell);
    }

    //TODO fill voor reverseboard

    public Set<Position> getPositionsOfElement(T element){
        Set<Position> positions = reverseBoard.get(element);
        return Collections.unmodifiableSet(positions);
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

