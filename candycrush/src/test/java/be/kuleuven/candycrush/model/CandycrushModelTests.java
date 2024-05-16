package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.CandycrushApplication;
import be.kuleuven.candycrush.model.interfaces.Candy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class CandycrushModelTests {


    @Test
    public void reset_maakt_een_nieuw_random_bord(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
                                        //v-- clone zodat beiden boards niet intern in geheugen naar zelfde arraylist wijzen
        final Board<Candy> speelbordOrigineel = new Board<>(model.getBoard());
        // 2. Act
        model.reset();
        final Board<Candy> speelbordNaReset = new Board<>(model.getBoard());
        // 3. Assert
        assertThat(speelbordNaReset).isNotEqualTo(speelbordOrigineel);
    }

    @Test
    public void highScore_blijft_bestaan_na_reset(){
        // 1. Arrange
        CandycrushApplication.highscoreOfLoggedInPlayer = 100;
        final CandycrushModel model = new CandycrushModel();
        // 2. Act
        model.reset();
        final int highScoreNaReset = model.getHighScore();
        // 3. Assert
        assertThat(highScoreNaReset).isEqualTo(100);
    }

    @Test
    public void naam_uit_login_word_naam_van_spel(){
        // 1. Arrange
        CandycrushApplication.loggedInPlayer = "Ebbe";
        final CandycrushModel model = new CandycrushModel();
        // 2. Act
        final String naam = model.getSpeler();
        // 3. Assert
        assertThat(naam).isEqualTo("Ebbe");
    }

}
