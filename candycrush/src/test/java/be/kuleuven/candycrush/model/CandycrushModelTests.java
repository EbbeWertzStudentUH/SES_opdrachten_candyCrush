package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.CandycrushApplication;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class CandycrushModelTests {


    //Om de CI van de yml file te testen:
    @Test
    public void faal(){
        assert false;
    }


    //score na reset en highscore testen kunnen niet omdat die afhankelijk zijn van
    //het krijgen van score, dat in andere testen getest word

    //1
    @Test
    public void reset_maakt_een_nieuw_random_bord(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
        //moet kopie maken want arraylist is niet immutable.
        //Na de reset zal de "speelbordOrigineel" ook geupdate zijn omdat die nog steeds
        //refereert naar hetzelfde object in geheugen, waardoor alle nieuwe waarden
        //ook veranderd zijn in speelbordOrigineel.
        final ArrayList<Integer> speelbordOrigineel = kopieVanArrayList(model.getSpeelbord());
        // 2. Act
        model.reset();
        final ArrayList<Integer> speelbordNaReset = kopieVanArrayList(model.getSpeelbord());
        // 3. Assert
        assertThat(speelbordNaReset).isNotEqualTo(speelbordOrigineel);
    }

    //2
    @Test
    public void krijg_1_punt_per_kapot_snoepje_normaal(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
        zetRijOpSpelBord(model, 0, new int[]{2,1,2,1,1,1,1,1,1,1});
        zetRijOpSpelBord(model, 1, new int[]{1,2,2,1,1,1,1,1,1,1});
        zetRijOpSpelBord(model, 2, new int[]{2,2,1,1,1,1,1,1,1,1});
        final int indexToCheck = model.getIndexFromRowColumn(1, 1);
        // 2. Act
        model.candyWithIndexSelected(indexToCheck);
        final int punten = model.getScore();
        // 3. Assert
        assertThat(punten).isEqualTo(5);
    }

    //3
    @Test
    public void krijg_geen_punten_als_minder_dan_3_snoepjes(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
        zetRijOpSpelBord(model, 0, new int[]{2,1,2,1,1,1,1,1,1,1});
        zetRijOpSpelBord(model, 1, new int[]{1,2,1,1,1,1,1,1,1,1});
        zetRijOpSpelBord(model, 2, new int[]{1,1,1,1,1,1,1,1,1,1});
        final int indexToCheck = model.getIndexFromRowColumn(1, 1);
        // 2. Act
        model.candyWithIndexSelected(indexToCheck);
        final int punten = model.getScore();
        // 3. Assert
        assertThat(punten).isEqualTo(0);
    }

    //4
    @Test
    public void exception_als_index_te_groot(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
        final int indexToCheck = 10*10 + 1;
        // 2. Act + assert
        assertThatThrownBy(() -> {
            model.candyWithIndexSelected(indexToCheck);
        }).isInstanceOf(IndexOutOfBoundsException.class);
    }

    //5

    @Test
    public void width_en_height_matchen_speelbord(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
        final int width = model.getWidth();
        final int height = model.getHeight();
        final ArrayList<Integer> speelbord = model.getSpeelbord();
        // 2. Act

        // 3. Assert
        assertThat(speelbord.size()).isEqualTo(width*height);
    }

    //6

    @Test
    public void highScore_word_niet_gereset(){
        // 1. Arrange
        CandycrushApplication.highscoreOfLoggedInPlayer = 100;
        final CandycrushModel model = new CandycrushModel();
        // 2. Act
        model.reset();
        final int highScoreNaReset = model.getHighScore();
        // 3. Assert
        assertThat(highScoreNaReset).isEqualTo(100);
    }

    //7

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

    //8

    @Test
    public void index_plus_1_is_geen_neighbour_als_die_over_rechterrand_van_grid_wrapt_naar_volgende_rij(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
        zetRijOpSpelBord(model, 0, new int[]{1,1,1,1,1,1,1,1,2,2});
        zetRijOpSpelBord(model, 1, new int[]{1,1,1,1,1,1,1,1,2,2});
        zetRijOpSpelBord(model, 2, new int[]{2,1,1,1,1,1,1,1,2,2});
        final int indexToCheck = model.getIndexFromRowColumn(1, 8);
        // 2. Act
        model.candyWithIndexSelected(indexToCheck);
        final int punten = model.getScore();
        // 3. Assert
        assertThat(punten).isNotEqualTo(6);
        assertThat(punten).isEqualTo(5);
    }

    //9

    @Test
    public void checkt_geen_vakjes_met_te_kleine_index__AKA_linksboven_de_grid(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
        zetRijOpSpelBord(model, 0, new int[]{2,2,1,1,1,1,1,1,1,1});
        zetRijOpSpelBord(model, 1, new int[]{2,2,1,1,1,1,1,1,1,1});
        final int indexToCheck = model.getIndexFromRowColumn(0, 0);
        // 2. Act
        model.candyWithIndexSelected(indexToCheck);
        final int punten = model.getScore();
        // 3. Assert
        assertThat(punten).isEqualTo(3);
    }

    //10

    @Test
    public void checkt_geen_vakjes_met_te_kleine_index__AKA_rechtsboven_de_grid(){
        // 1. Arrange
        final CandycrushModel model = new CandycrushModel();
        zetRijOpSpelBord(model, 8, new int[]{1,1,1,1,1,1,1,1,2,2});
        zetRijOpSpelBord(model, 9, new int[]{1,1,1,1,1,1,1,1,2,2});
        final int indexToCheck = model.getIndexFromRowColumn(9, 9);
        // 2. Act
        model.candyWithIndexSelected(indexToCheck);
        final int punten = model.getScore();
        // 3. Assert
        assertThat(punten).isEqualTo(3);
    }


    private ArrayList<Integer> kopieVanArrayList(ArrayList<Integer> lijst){
        return new ArrayList<>(lijst);
    }
    private void zetRijOpSpelBord(CandycrushModel model,int rijIndex, int[] rij){
        ArrayList<Integer> spelBord = model.getSpeelbord();
        int kolomIndex = 0;
        for(int i : rij){
            int index = model.getIndexFromRowColumn(rijIndex, kolomIndex);
            spelBord.set(index, i);
            kolomIndex ++;
        }
    }

}
