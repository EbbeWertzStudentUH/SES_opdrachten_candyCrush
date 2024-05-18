package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.CandycrushApplication;
import be.kuleuven.candycrush.model.interfaces.Candy;
import be.kuleuven.candycrush.model.records.BoardSize;
import be.kuleuven.candycrush.model.records.PosPair;
import be.kuleuven.candycrush.model.records.candy.*;
import be.kuleuven.candycrush.model.records.Position;
import javafx.css.Match;
import javafx.geometry.Pos;
import javafx.util.Pair;

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
    public CandycrushModel(BoardSize size) {
        board = new Board<>(size);
        speler = null;
    }
    public CandycrushModel(CandycrushModel model) {
        board = new Board<>(model.board);
        highScore = 0;
        score = 0;
        speler = null;
    }

    public void reset(){
        board.fill(p -> generateRandomCandy());
        score = 0;
    }

    public String getSpeler() {return speler;}
    public Board<Candy> getBoard() {return board;}
    public int getScore() {return score;}
    public int getHighScore() {return highScore;}
    public boolean highscoreIsUpdated(){return highscoreIsUpdated;}

    public void candyWithPositionSelected(Position position){
        updateBoard();
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

    /*  =========================================================
            RECURSIE
        ========================================================= */

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
                .filter(p -> !firstTwoHaveCandy(board.getCellAtPosition(p), p.walkLeft()))
                .filter(p -> !(board.getCellAtPosition(p) instanceof EmptyCandy));
    }
    private Stream<Position> verticalStartingPositions(){
        return board.getBoardSize().positions().stream()
                .filter(p -> !firstTwoHaveCandy(board.getCellAtPosition(p), p.walkUp()))
                .filter(p -> !(board.getCellAtPosition(p) instanceof EmptyCandy));
    }
    private boolean equalCells(Position pos1, Position pos2){
        if(board.getCellAtPosition(pos1) instanceof EmptyCandy)  return false;
        if(board.getCellAtPosition(pos2) instanceof EmptyCandy)  return false;
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

    private void fullFallDown(Position bottomRowPos){
        fallDownTo(bottomRowPos,bottomRowPos);
        if(bottomRowPos.isLastColumn()) return;
        fullFallDown(bottomRowPos.right());
    }

    private boolean updateBoard(){
        Set<List<Position>> matches = findAllMatches();
        //als er niets te updaten is return false
        if(matches.isEmpty()) return false;
        Set<Position> clearedPositions = new HashSet<>();
        //deze for loops nog vervangen door recursie geeft stack overflow error
        for (List<Position> match : matches) {
            int scoreToAdd = 0;
            clearMatch(match);
            for(Position p : match){
                //set returnt false bij duplicaat
                // -> kan daarmee voorkomen om bij L of kruis vorm dubbel score te tellen
                boolean noOverlap = clearedPositions.add(p);
                if(noOverlap) scoreToAdd++;
            }
            addScore(scoreToAdd);
        }
        //falldownto achteraf omdat na fall matches ongeldig kunnen worden
        BoardSize size = board.getBoardSize();
        fullFallDown(new Position(0, size.rows()-1, size));
        updateBoard();
        return true;
    }


    private void clearMatch(List<Position> match){
        if(match.isEmpty()) return;
        List<Position> matchCopy = new ArrayList<>(match);
        Position pos = matchCopy.removeFirst();
        board.replaceCellAtPosition(pos, new EmptyCandy());
        clearMatch(matchCopy);
    }

    private void fallDownTo(Position pos, Position bottom){
        if(pos.row() == 0) return;

        Position upPos = pos.above();
        Candy currentCandy = board.getCellAtPosition(pos);
        Candy upCandy = board.getCellAtPosition(upPos);
        //als pos een gat is en candy erboven niet, laat candy vallen naar bottom
        //bottom gaat 1 omhoog

        if(currentCandy instanceof EmptyCandy && !(upCandy instanceof EmptyCandy)){
            board.replaceCellAtPosition(bottom, upCandy);
            board.replaceCellAtPosition(upPos, new EmptyCandy());
            bottom = bottom.above();
        }
        //als boven pos geen candy is, valt niks, dus bottom blijft staan
        //volgend candy zal dus zover mogelijk vallen omdat bottom zo laag mogelijk blijft staan
        fallDownTo(upPos, bottom);
    }

    /*  =========================================================
            BACKTRACKING
        ========================================================= */

    public Pair<Integer,List<PosPair>> maximizeScore() {
        List<PosPair> validSwicthes = allValidSwitches();
        //als geen validSwicthes meer mogelijk zijn -> return
        if(validSwicthes.isEmpty()) return new Pair<>(0,new ArrayList<>());

        //kijk voor elke pair wat de uitkomst is als deze de startswitch zou zijn
        //en kies maximum daaruit
        List<PosPair> bestSequence = new ArrayList<>();
        PosPair bestPair = null;
        int bestScore = 0;
        for(PosPair pair : validSwicthes){
            CandycrushModel clone = new CandycrushModel(this); //zodat this-object niet wordt aangepast
            //execute de switch
            final int scoreForPair = clone.executeSwitchWithUpdate(pair);
            //zoek opnieuw de beste sequence na die swicth
            final Pair<Integer,List<PosPair>> cloneResult = clone.maximizeScore();
            final int cloneScore = cloneResult.getKey();
            final List<PosPair> cloneSequence = cloneResult.getValue();
            //update best
            if(isOtherBetter(bestSequence, bestScore, cloneSequence, cloneScore+scoreForPair)){
                bestScore = cloneScore+scoreForPair;
                bestPair = pair;
                bestSequence = cloneSequence;
            }
        }
        List<PosPair> totalSequence = new ArrayList<>();
        totalSequence.add(bestPair);
        totalSequence.addAll(bestSequence);
        return new Pair<>(bestScore, totalSequence);
    }

    //acties op board

    private void switchCandies(PosPair pair){
        Candy candy1 = board.getCellAtPosition(pair.p1());
        Candy candy2 = board.getCellAtPosition(pair.p2());
        board.replaceCellAtPosition(pair.p1(), candy2);
        board.replaceCellAtPosition(pair.p2(), candy1);
    }

    //checkers

    private boolean isValidSwicth(PosPair pair){
        return isValidPair(pair) && matchAfterSwitch(pair);
    }

    private boolean matchAfterSwitch(PosPair pair){
        switchCandies(pair);
        boolean match = !findAllMatches().isEmpty();
        switchCandies(pair);
        return match;
    }

    private boolean isValidPair(PosPair pair){
        Candy candy1 = board.getCellAtPosition(pair.p1());
        Candy candy2 = board.getCellAtPosition(pair.p2());
        if(candy1 instanceof EmptyCandy || candy2 instanceof EmptyCandy){
            return false;
        }
        return !candy1.equals(candy2);
    }

    //andere helpers

    private List<PosPair> allValidSwitches(){
        List<PosPair> pairs = new ArrayList<>();
        for(Position pos : board.getBoardSize().positions()){
            if(!pos.isLastColumn()){
                PosPair horizontal = new PosPair(pos, pos.right());
                if(isValidSwicth(horizontal)) pairs.add(horizontal);
            }
            if(!pos.isLastRow()){
                PosPair vertical = new PosPair(pos, pos.below());
                if(isValidSwicth(vertical)) pairs.add(vertical);
            }
        }
        return pairs;
    }

    private boolean isOtherBetter(List<PosPair> sequence, int score, List<PosPair> otherSequence, int otherScore){
        //return degene die beste score heeft
        if(score != otherScore) return otherScore > score;
        //als beiden zelfde score hebben, return met kortste sequence
        return otherSequence.size() < sequence.size();
    }

    private int executeSwitchWithUpdate(PosPair pair){
        final int initScore = score;
        switchCandies(pair);
        updateBoard();
        return score - initScore;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        System.out.println("Model1:");
        CandycrushModel model1 = createBoardFromString("""
                @@o#
                o*#o
                @@**
                *#@@""");
        Pair<Integer, List<PosPair>> pairs1 = model1.maximizeScore();
        System.out.println(pairs1);
        System.out.println("Model2:");
        CandycrushModel model2 = createBoardFromString("""
                #oo##
                #@o@@
                *##o@
                @@*@o
                **#*o""");
        Pair<Integer, List<PosPair>> pairs2 = model2.maximizeScore();
        System.out.println(pairs2);
        System.out.println("Model3:");
        CandycrushModel model3 = createBoardFromString("""
                #@#oo@
                @**@**
                o##@#o
                @#oo#@
                @*@**@
                *#@##*""");
        Pair<Integer, List<PosPair>> pairs3 = model3.maximizeScore();
        System.out.println(pairs3);
    }
    public static CandycrushModel createBoardFromString(String configuration) {
        var lines = configuration.toLowerCase().lines().toList();
        BoardSize size = new BoardSize(lines.size(), lines.getFirst().length());
        CandycrushModel model = new CandycrushModel(size); // deze moet je zelf voorzien
        for (int row = 0; row < lines.size(); row++) {
            var line = lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                Candy candy = characterToCandy(line.charAt(col));
                Position pos = new Position(col, row, size);
                model.getBoard().replaceCellAtPosition(pos, candy);
            }
        }
        return model;
    }

    private static Candy characterToCandy(char c) {
        return switch(c) {
            case '.' -> new EmptyCandy();
            case 'o' -> new NormalCandy(0);
            case '*' -> new NormalCandy(1);
            case '#' -> new NormalCandy(2);
            case '@' -> new NormalCandy(3);
            default -> throw new IllegalArgumentException("Unexpected value: " + c);
        };
    }
}
