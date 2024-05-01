package be.kuleuven.candycrush.model.records;

import be.kuleuven.candycrush.model.records.candy.NormalCandy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NormalCandyTests {

    @Test
    public void negatieve_color_gooit_exception(){
        assertThatThrownBy(() -> {
            new NormalCandy(-1);
        }).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void color_Groter_dan_3_gooit_exception(){
        assertThatThrownBy(() -> {
            new NormalCandy(4);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
