package be.kuleuven.candycrush.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import be.kuleuven.candycrush.CandycrushApplication;
import be.kuleuven.neighbourchecklibrary.CheckNeighboursInGrid;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;

public class CandycrushModel {
    private final String speler;
    private final ArrayList<Integer> speelbord;
    private final int width;
    private final int height;

    private int score;

    private int highScore;

    private boolean highscoreIsUpdated;



    public CandycrushModel() {
        speelbord = new ArrayList<>();
        width = 10;
        height = 10;
        highScore = CandycrushApplication.highscoreOfLoggedInPlayer;
        speler = CandycrushApplication.loggedInPlayer;
        reset();
    }
    public void reset(){
        speelbord.clear();

        for (int i = 0; i < width*height; i++){
            Random random = new Random();
            int randomGetal = random.nextInt(5) + 1;
            speelbord.add(randomGetal);
        }
        score = 0;
    }

    public static void main(String[] args) {
        CandycrushModel model = new CandycrushModel();
        int i = 1;
        Iterator<Integer> iter = model.getSpeelbord().iterator();
        while(iter.hasNext()){
            int candy = iter.next();
            System.out.print(candy);
            if(i% model.getWidth()==0){
                System.out.print("\n");
                i = 1;
            }
            i++;
        }
        System.out.print("\n");

    }
    public String getSpeler() {
        return speler;
    }

    public ArrayList<Integer> getSpeelbord() {
        return speelbord;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void candyWithIndexSelected(int index){
        if (index != -1){
            Random random = new Random();
            int randomGetal = random.nextInt(5) + 1;

            //check neighbours ===============
            final ArrayList<Integer> sameNeighbours;
            sameNeighbours = (ArrayList<Integer>) CheckNeighboursInGrid.getSameNeighboursIds(speelbord, width, height, index);
            if(sameNeighbours.size() >= 3){
                addScore(sameNeighbours.size());
                updateNeighbours(sameNeighbours);
            }
            //================================

            speelbord.set(index,randomGetal);
        }else{
            System.out.println("model:candyWithIndexSelected:indexWasMinusOne");
        }
    }

    private void updateNeighbours(ArrayList<Integer> neighbours){
        Random random = new Random();
        for(int index : neighbours){
            speelbord.set(index, random.nextInt(5) + 1);
        }
    }

    private void addScore(int points){
        score += points;
        highscoreIsUpdated = score > highScore;
        if(highscoreIsUpdated){
            highScore = score;
        }
    }

    public int getIndexFromRowColumn(int row, int column) {
        return column+row*width;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public boolean highscoreIsUpdated(){
        return highscoreIsUpdated;
    }
}
