package be.kuleuven.candycrush.model;

/*TODO:
    - Zorg dat deze laatste twee methodes zo algemeen mogelijk zijn qua type, en schrijf telkens een test waarin je hier gebruik van maakt.
    - Gebruik de Board-klasse in je model van Candycrush, waarbij de cellen Candy-objecten zijn.
*/

import be.kuleuven.candycrush.model.interfaces.Candy;
import be.kuleuven.candycrush.model.records.BoardSize;
import be.kuleuven.candycrush.model.records.Position;

import java.util.ArrayList;
import java.util.function.Function;

public class Board<T>{

    private final ArrayList<T> board;

    private BoardSize boardSize;

    public Board(BoardSize boardSize) {
        board = new ArrayList<>();
    }

    //TODO cel op een gegeven positie van het bord op te vragen
    public T getCellAt(Position position){
        validatePosition(position);
        return board.get(position.toIndex());
    }
    //TODO cel op een gegeven positie te vervangen door een meegegeven object
    public void replaceCellAt(Position position, T newCell){
        validatePosition(position);
        board.set(position.toIndex(), newCell);
    }
    //TODO hele bord te vullen via func
    public void fill(Function<Position, T> cellCreator){

    }
    //TODO alle cellen van het huidige bord kopieert naar het meegegeven bord
    //niet dezelfde afmetingen heeft, gooi je een exception
    public void copyTo(Board<T> otherBoard){
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
}

