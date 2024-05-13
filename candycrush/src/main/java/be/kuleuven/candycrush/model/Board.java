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
    }
    //om te clonen voor tests:
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

        //verwijder oude cell
        T oldCell = getCellAtPosition(position);
        if(oldCell != null){
            if(reverseBoard.containsKey(oldCell))
                reverseBoard.get(oldCell).remove(position);
        }
        //voeg nieuwe cell toe
        if (!reverseBoard.containsKey(oldCell)) {
            reverseBoard.put(newCell, new HashSet<>());
        }
        reverseBoard.get(newCell).add(position);
        board.put(position, newCell);
    }

    public Set<Position> getPositionsOfElement(T element){
        Set<Position> positions;
        if(reverseBoard.containsKey(element)){
            positions = reverseBoard.get(element);
        } else {
            positions = new HashSet<>();
        }
        return Collections.unmodifiableSet(positions);
    }


    public void fill(Function<Position, ? extends T> cellCreator){
        board.clear();
        reverseBoard.clear();
        for(Position position : boardSize.positions()){
            replaceCellAtPosition(position, cellCreator.apply(position));
        }
    }
    public void copyTo(Board<? super T> otherBoard){
        if(!otherBoard.boardSize.equals(boardSize)){
            throw new IllegalArgumentException("Boardsize of other board: "+otherBoard.boardSize+" does not match the boardsize of this board:"+boardSize);
        }
        otherBoard.board.clear();
        otherBoard.reverseBoard.clear();
        otherBoard.board.putAll(board);
        otherBoard.reverseBoard.putAll(reverseBoard);
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

