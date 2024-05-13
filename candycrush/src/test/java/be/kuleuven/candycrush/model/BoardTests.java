package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.model.interfaces.Candy;
import be.kuleuven.candycrush.model.records.BoardSize;
import be.kuleuven.candycrush.model.records.Position;
import be.kuleuven.candycrush.model.records.candy.NormalCandy;
import be.kuleuven.candycrush.model.records.candy.Tri_nitrotolueneCandy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class BoardTests {


    @Test
    public void fill_werkt_alle_items_identiek(){
        Board<Candy> board = new Board<>(new BoardSize(5,5));
        Candy candy = new NormalCandy(0);
        board.fill(p -> candy);
        for(Position position : board.getBoardSize().positions()){
            assertThat(board.getCellAtPosition(position)).isEqualTo(candy);
        }
    }
    @Test
    public void fill_werkt_specifieke_waarde_per_position(){
        Board<Candy> board = new Board<>(new BoardSize(5,5));
        Candy candy = new NormalCandy(0);
        Candy candySpeciaal = new NormalCandy(1);
        Position specialPos = new Position(2,3, board.getBoardSize());
        board.fill(p -> p.equals(specialPos) ? candySpeciaal : candy);
        for(Position position : board.getBoardSize().positions()){
            if(position.equals(specialPos)){
                assertThat(board.getCellAtPosition(position)).isEqualTo(candySpeciaal);
            } else {
                assertThat(board.getCellAtPosition(position)).isEqualTo(candy);
            }
        }
    }
    @Test
    public void fill_werkt_specifiek_type_per_position(){
        Board<Candy> board = new Board<>(new BoardSize(5,5));
        NormalCandy candy = new NormalCandy(0);
        Tri_nitrotolueneCandy candySpeciaal = new Tri_nitrotolueneCandy();
        Position specialPos = new Position(2,3, board.getBoardSize());
        board.fill(p -> p.equals(specialPos) ? candySpeciaal : candy);
        for(Position position : board.getBoardSize().positions()){
            if(position.equals(specialPos)){
                assertThat(board.getCellAtPosition(position)).isInstanceOf(Tri_nitrotolueneCandy.class);
            } else {
                assertThat(board.getCellAtPosition(position)).isInstanceOf(NormalCandy.class);
            }
        }
    }

    @Test
    public void copyTo_werkt_zelfde_type(){
        Board<Candy> board = new Board<>(new BoardSize(5,5));
        board.fill(p -> new NormalCandy(1));
        Board<Candy> board2 = new Board<>(new BoardSize(5,5));
        board.copyTo(board2);
        for(Position position : board.getBoardSize().positions()){
            assertThat(board.getCellAtPosition(position)).isEqualTo(board2.getCellAtPosition(position));
        }
    }
    @Test
    public void copyTo_werkt_vanuit_super_type(){
        Board<NormalCandy> board = new Board<>(new BoardSize(5,5));
        board.fill(p -> new NormalCandy(1));
        Board<Candy> board2 = new Board<>(new BoardSize(5,5));
        board.copyTo(board2);
        for(Position position : board.getBoardSize().positions()){
            assertThat(board.getCellAtPosition(position)).isEqualTo(board2.getCellAtPosition(position));
        }
    }

}
