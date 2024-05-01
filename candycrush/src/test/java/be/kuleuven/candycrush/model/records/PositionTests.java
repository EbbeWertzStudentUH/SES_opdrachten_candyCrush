package be.kuleuven.candycrush.model.records;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositionTests {

    @Test
    public void negatieve_position_geeft_exception(){
        final BoardSize size = new BoardSize(10,15);
        assertThatThrownBy(() -> {
            new Position(-1, 0, size);
        }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> {
            new Position(0, -1, size);
        }).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void te_grote_position_voor_size_geeft_exception(){
        final BoardSize size = new BoardSize(10,15);
        assertThatThrownBy(() -> {
            new Position(10, 0, size);
        }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> {
            new Position(0, 15, size);
        }).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void toIndex_werkt_correct(){
        final BoardSize size = new BoardSize(10,15);
        final Position position = new Position(5,3, size);
        final int index = position.toIndex();
        assertThat(index).isEqualTo(35);
    }
    @Test
    public void fromIndex_werkt_correct(){
        final BoardSize size = new BoardSize(10,15);
        final int index = 35;
        final Position position = Position.fromIndex(index, size);
        assertThat(position.col()).isEqualTo(5);
        assertThat(position.row()).isEqualTo(3);
    }
    @Test
    public void neighborPositions_werkt_in_midden_bord(){
        final BoardSize size = new BoardSize(10,15);
        final Position position = new Position(5,2, size);
        final Position[] trueNeighbours = {
                new Position(4,1,size),new Position(5,1,size),new Position(6,1,size),
                new Position(4,2,size),                                new Position(6,2,size),
                new Position(4,3,size),new Position(5,3,size),new Position(6,3,size),
        };
        final ArrayList<Position> neighbours = (ArrayList<Position>) position.neighborPositions();
        assertThat(neighbours).containsExactlyInAnyOrder(trueNeighbours);
    }
    @Test
    public void neighborPositions_negeert_positions_buiten_laag_bereik(){
        final BoardSize size = new BoardSize(10,15);
        final Position position = new Position(0,0, size);
        final Position[] trueNeighbours = {
                                                new Position(1,0,size),
                new Position(0,1,size),new Position(1,1,size),
        };
        final ArrayList<Position> neighbours = (ArrayList<Position>) position.neighborPositions();
        assertThat(neighbours).containsExactlyInAnyOrder(trueNeighbours);
    }
    @Test
    public void neighborPositions_negeert_positions_buiten_hoog_bereik(){
        final BoardSize size = new BoardSize(10,15);
        final Position position = new Position(9,14, size);
        final Position[] trueNeighbours = {
                new Position(8,13,size),new Position(9,13,size),
                new Position(8,14,size)
        };
        final ArrayList<Position> neighbours = (ArrayList<Position>) position.neighborPositions();
        assertThat(neighbours).containsExactlyInAnyOrder(trueNeighbours);
    }
    @Test
    public void isLastColumn_true_bij_laatste_kolom(){
        final BoardSize size = new BoardSize(10,15);
        final Position position = new Position(9,2, size);
        assert position.isLastColumn();
    }
    @Test
    public void isLastColumn_false_in_midden(){
        final BoardSize size = new BoardSize(10,15);
        final Position position = new Position(3,2, size);
        assert !position.isLastColumn();
    }



}
