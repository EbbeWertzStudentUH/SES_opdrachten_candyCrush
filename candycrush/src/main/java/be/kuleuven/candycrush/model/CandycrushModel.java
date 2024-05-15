package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.CandycrushApplication;
import be.kuleuven.candycrush.model.interfaces.Candy;
import be.kuleuven.candycrush.model.records.BoardSize;
import be.kuleuven.candycrush.model.records.candy.*;
import be.kuleuven.candycrush.model.records.Position;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void candyWithPositionSelected(Position position){
        Set<List<Position>> matches = findAllMatches();
        for (List<Position> match : matches) {
            clearMatch(match);
        }
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


    private Set<List<Position>> findAllMatches(){
        Stream<List<Position>> horizontal = horizontalStartingPositions()
                .map(this::longestMatchToRight)
                .filter(l -> l.size() >= 3);
        Stream<List<Position>> vertical = verticalStartingPositions()
                .map(this::longestMatchDown)
                .filter(l -> l.size() >= 3);

        return Stream.concat(vertical, horizontal)
                .collect(Collectors.toSet());
    }

    private boolean firstTwoHaveCandy(Candy candy, Stream<Position> positions){
        return positions
                .limit(2)
                .filter(p -> board.getCellAtPosition(p).equals(candy))//beter dan match want:
                .count() >= 2;  //als stream initeel korter dan 2 elementen heeft, komt uit filter ook geen count 2
    }
    private Stream<Position> horizontalStartingPositions(){
        return board.getBoardSize().positions().stream()
                .filter(p -> !firstTwoHaveCandy(board.getCellAtPosition(p), p.walkLeft()));
    }
    private Stream<Position> verticalStartingPositions(){
        return board.getBoardSize().positions().stream()
                .filter(p -> !firstTwoHaveCandy(board.getCellAtPosition(p), p.walkUp()));
    }
    private boolean equalCells(Position pos1, Position pos2){
        return board.getCellAtPosition(pos1).equals(board.getCellAtPosition(pos2));
    }
    private List<Position> longestMatchToRight(Position pos){
        return pos.walkRight()
                .takeWhile(p -> equalCells(pos,p))
                .collect(Collectors.toList());
    }
    private List<Position> longestMatchDown(Position pos){
        return pos.walkDown()
                .takeWhile(p -> equalCells(pos,p))
                .collect(Collectors.toList());
    }


    private void clearMatch(List<Position> match){
        if(match.isEmpty()) return;
        Position pos = match.removeFirst();
        board.replaceCellAtPosition(pos, new EmptyCandy());
        clearMatch(match);
    }

    private Position above(Position pos){
        return new Position(pos.col(), pos.row() -1, board.getBoardSize());
    }

    private void fallDownTo(Position pos, Position bottom){
        if(pos.row() == 0) return;

        Position upPos = above(pos);
        Candy current = board.getCellAtPosition(pos);
        Candy up = board.getCellAtPosition(upPos);
        if(current instanceof EmptyCandy && !(up instanceof EmptyCandy)){
            board.replaceCellAtPosition(bottom, up);
            board.replaceCellAtPosition(upPos, new EmptyCandy());
            bottom = above(bottom);
        }
        fallDownTo(upPos, bottom);
    }

}
