package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.CandycrushApplication;
import be.kuleuven.candycrush.model.interfaces.Candy;
import be.kuleuven.candycrush.model.records.BoardSize;
import be.kuleuven.candycrush.model.records.candy.*;
import be.kuleuven.candycrush.model.records.Position;

import java.util.ArrayList;
import java.util.Random;

public class CandycrushModel {

    private final String speler;

    private final Board<Candy> board;
    private boolean highscoreIsUpdated;
    private int score;
    private int highScore;

    public CandycrushModel() {
        board = new Board<>(new BoardSize(10, 10));
        highScore = CandycrushApplication.highscoreOfLoggedInPlayer;
        speler = CandycrushApplication.loggedInPlayer;
        reset();
    }

    public void reset(){
        board.fill(p -> generateRandomCandy());
        score = 0;
    }

    public static void main(String[] args) {
        CandycrushModel model = new CandycrushModel();
        int i = 1;
        for (Position p : model.board.getBoardSize().positions()) {
            System.out.print(model.board.getCellAtPosition(p));
            if (i % model.board.getBoardSize().cols() == 0) {
                System.out.print("\n");
                i = 1;
            }
            i++;
        }
        System.out.print("\n");
    }
    public String getSpeler() {return speler;}
    public Board<Candy> getBoard() {return board;}
    public int getScore() {return score;}
    public int getHighScore() {return highScore;}
    public boolean highscoreIsUpdated(){return highscoreIsUpdated;}

    public Iterable<Position> getSameNeighbourPositions(Position position){
        Iterable<Position> neighbours = position.neighborPositions();
        ArrayList<Position> sameNeighbours = new ArrayList<>();
        for(Position pos : neighbours){
            Candy candyOnPosition = board.getCellAtPosition(position);
            Candy candyOnPos = board.getCellAtPosition(pos);
            if(candyOnPos.equals(candyOnPosition)){
                sameNeighbours.add(pos);
            }
        }
        return sameNeighbours;
    }

    public void candyWithPositionSelected(Position position){
        //get neighbours
        ArrayList<Position> sameNeighbours;
        sameNeighbours = (ArrayList<Position>) getSameNeighbourPositions(position);
        //give score if > 3
        if(sameNeighbours.size() >= 3){
            addScore(sameNeighbours.size());
            //update neighbours
            for(Position pos : sameNeighbours){
                board.replaceCellAtPosition(pos, generateRandomCandy());
            }
        }
        //update self
        board.replaceCellAtPosition(position, generateRandomCandy());
    }

    private void addScore(int points){
        score += points;
        highscoreIsUpdated = score > highScore;
        if(highscoreIsUpdated){
            highScore = score;
        }
    }

    public Candy generateRandomCandy(){
        final Random random = new Random();
        //greater chance to get normal then special
        final boolean chance7To10ForNormal = random.nextInt(10)<9;
        if(chance7To10ForNormal){
            final int color = random.nextInt(4);
            return new NormalCandy(color);
        } else {
            final int type = random.nextInt(4);
            return specialCandyFromInt(type);
        }
    }

    public Candy specialCandyFromInt(int intValue){
        final Candy[] candies = {
                new Tri_nitrotolueneCandy(),
                new FlatLinerCandy(),
                new ChocolateGoldCandy(),
                new HyperDextroseCandy()};
        return candies[intValue];
    }
}
