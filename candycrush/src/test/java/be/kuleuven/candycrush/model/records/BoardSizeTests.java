package be.kuleuven.candycrush.model.records;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BoardSizeTests {

    @Test
    public void negatieve_Size_Gooit_Exception(){
        assertThatThrownBy(() -> {
            new BoardSize(-1, 10);
        }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> {
            new BoardSize(10, -1);
        }).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void nul_Size_Gooit_Exception(){
        assertThatThrownBy(() -> {
            new BoardSize(0, 10);
        }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> {
            new BoardSize(10, 0);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void positions_werkt_correct(){
        final BoardSize size = new BoardSize(3,3);
        final Position[] truePositions = {
                new Position(0,0,size),new Position(1,0,size),new Position(2,0,size),
                new Position(0,1,size),new Position(1,1,size),new Position(2,1,size),
                new Position(0,2,size),new Position(1,2,size),new Position(2,2,size),
        };
        final ArrayList<Position> positions = (ArrayList<Position>) size.positions();
        assertThat(positions).containsExactly(truePositions);
    }

}
