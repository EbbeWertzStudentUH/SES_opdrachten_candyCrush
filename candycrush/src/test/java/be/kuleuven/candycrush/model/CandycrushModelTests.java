package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.CandycrushApplication;
import be.kuleuven.candycrush.model.interfaces.Candy;
import be.kuleuven.candycrush.model.records.candy.NormalCandy;
import be.kuleuven.candycrush.model.records.Position;
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
    public void krijg_1_punt_per_kapot_snoepje_normaal(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
        zetRijOpSpelBord(model, 0, new int[]{2,1,2,1,1,1,1,1,1,1});
        zetRijOpSpelBord(model, 1, new int[]{1,2,2,1,1,1,1,1,1,1});
        zetRijOpSpelBord(model, 2, new int[]{2,2,1,1,1,1,1,1,1,1});
        final Position positionToCheck = new Position(1,1, model.getBoard().getBoardSize());
        // 2. Act
        model.candyWithPositionSelected(positionToCheck);
        final int punten = model.getScore();
        // 3. Assert
        assertThat(punten).isEqualTo(5);
    }

    @Test
    public void krijg_geen_punten_als_minder_dan_3_snoepjes(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
        zetRijOpSpelBord(model, 0, new int[]{2,1,2,1,1,1,1,1,1,1});
        zetRijOpSpelBord(model, 1, new int[]{1,2,1,1,1,1,1,1,1,1});
        zetRijOpSpelBord(model, 2, new int[]{1,1,1,1,1,1,1,1,1,1});
        final Position positionToCheck = new Position(1,1, model.getBoard().getBoardSize());
        // 2. Act
        model.candyWithPositionSelected(positionToCheck);
        final int punten = model.getScore();
        // 3. Assert
        assertThat(punten).isEqualTo(0);
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

    @Test
    public void positie_met_index_plus_1_is_geen_neighbour_als_die_over_rechterrand_van_grid_wrapt_naar_volgende_rij(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
        zetRijOpSpelBord(model, 0, new int[]{1,1,1,1,1,1,1,1,2,2});
        zetRijOpSpelBord(model, 1, new int[]{1,1,1,1,1,1,1,1,2,2});
        zetRijOpSpelBord(model, 2, new int[]{2,1,1,1,1,1,1,1,2,2});
        final Position positionToCheck = new Position(1,8, model.getBoard().getBoardSize());
        // 2. Act
        model.candyWithPositionSelected(positionToCheck);
        final int punten = model.getScore();
        // 3. Assert
        assertThat(punten).isNotEqualTo(6);
        assertThat(punten).isEqualTo(5);
    }

    @Test
    public void checkt_geen_vakjes_met_te_kleine_position__AKA_linksboven_de_grid(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
        zetRijOpSpelBord(model, 0, new int[]{2,2,1,1,1,1,1,1,1,1});
        zetRijOpSpelBord(model, 1, new int[]{2,2,1,1,1,1,1,1,1,1});
        final Position positionToCheck = new Position(0,0, model.getBoard().getBoardSize());
        // 2. Act
        model.candyWithPositionSelected(positionToCheck);
        final int punten = model.getScore();
        // 3. Assert
        assertThat(punten).isEqualTo(3);
    }

    @Test
    public void checkt_geen_vakjes_met_te_kleine_index__AKA_rechtsboven_de_grid(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
        zetRijOpSpelBord(model, 8, new int[]{1,1,1,1,1,1,1,1,2,2});
        zetRijOpSpelBord(model, 9, new int[]{1,1,1,1,1,1,1,1,2,2});
        final Position positionToCheck = new Position(9,9, model.getBoard().getBoardSize());
        // 2. Act
        model.candyWithPositionSelected(positionToCheck);
        final int punten = model.getScore();
        // 3. Assert
        assertThat(punten).isEqualTo(3);
    }

    private void zetRijOpSpelBord(CandycrushModel model,int rijIndex, int[] rij){
        Board<Candy> spelBord = model.getBoard();
        int kolomIndex = 0;
        for(int i : rij){
            final Position pos = new Position(rijIndex,kolomIndex, model.getBoard().getBoardSize());
            spelBord.replaceCellAt(pos, new NormalCandy(i));
            kolomIndex ++;
        }
    }

}
