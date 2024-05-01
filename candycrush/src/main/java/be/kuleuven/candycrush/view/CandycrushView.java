package be.kuleuven.candycrush.view;

import be.kuleuven.candycrush.model.CandycrushModel;
import be.kuleuven.candycrush.model.interfaces.Candy;
import be.kuleuven.candycrush.model.records.Position;
import be.kuleuven.candycrush.model.records.candy.*;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CandycrushView extends Region {
    private final CandycrushModel model;
    private final int gridSize;

    public CandycrushView(CandycrushModel model) {
        this.model = model;
        gridSize = 30;  //width and height of grid
        update();
    }

    public void update(){
        getChildren().clear();
        for(Position position : model.getBoard().getBoardSize().positions()){
            Candy candy = model.getBoard().getCellAt(position);
            getChildren().add(makeCandyShape(position, candy));
        }
    }

    public Position getClickedPosition(MouseEvent me){
        int row = (int) me.getY()/gridSize;
        int column = (int) me.getX()/gridSize;
        if (row < model.getBoard().getBoardSize().cols() && column < model.getBoard().getBoardSize().cols()){
            Position clickedPos = new Position(column, row, model.getBoard().getBoardSize());
            System.out.println("Clicked: "+clickedPos);
            return clickedPos;
        }
        return null;
    }

    private Color getNormalCandyColor(int colorNumber){
        return (new Color[]
                {Color.LIGHTBLUE, Color.LEMONCHIFFON, Color.LIGHTGREEN, Color.LIGHTCORAL})
        [colorNumber];
    }
    private Node makeCandyShape(Position position, Candy candy){
        final double candySize = 0.8 * gridSize;
        final Node shape = switch (candy){
            case NormalCandy(int color) -> new Circle(candySize/2, getNormalCandyColor(color));
            case Tri_nitrotolueneCandy ignored -> new Rectangle(candySize, candySize, Color.CRIMSON);
            case FlatLinerCandy ignored -> new Rectangle(candySize, candySize, Color.DARKCYAN);
            case ChocolateGoldCandy ignored -> new Rectangle(candySize, candySize, Color.GOLD);
            case HyperDextroseCandy ignored -> new Rectangle(candySize, candySize, Color.DEEPPINK);
            //default hoef niet want interface is sealed dus andere opties zijn niet mogelijk
        };
        shape.getStyleClass().add("snoepjeRect");
        double offsetCorrection  = shape instanceof Circle ? candySize /2 : 0;
        // ^-- Cirkel heeft origin in centrum terwijl rectangle in linksboven hoek
        shape.setLayoutX(position.col() * gridSize + offsetCorrection);
        shape.setLayoutY(position.row() * gridSize + offsetCorrection);
        return shape;
    }
}
