package be.kuleuven.candycrush.controller;

import be.kuleuven.candycrush.CandycrushApplication;
import be.kuleuven.candycrush.model.CandycrushModel;
import be.kuleuven.candycrush.view.CandycrushView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CandycrushController {

    @FXML
    public Label welcomeLabel;
    public Button StartResetButton;
    @FXML
    private AnchorPane speelbord;
    private CandycrushModel model;
    private CandycrushView view;

    @FXML
    void initialize() {
        StartResetButton.setText("Start");
        welcomeLabel.setText("Hello "+CandycrushApplication.loggedInPlayer+", enjoy the game!");
        model = new CandycrushModel(CandycrushApplication.loggedInPlayer);
        StartResetButton.setOnAction(action -> startOrReset());
    }

    private void startOrReset() {
        if(speelbord.getChildren().isEmpty()){
            view = new CandycrushView(model);
            view.setOnMouseClicked(this::onCandyClicked);
            speelbord.getChildren().add(view);
            StartResetButton.setText("Reset");
        } else {
            model.reset();
        }
        update();
    }

    public void update(){
        view.update();
    }

    public void onCandyClicked(MouseEvent me){
        int candyIndex = view.getIndexOfClicked(me);
        model.candyWithIndexSelected(candyIndex);
        update();
    }

}
