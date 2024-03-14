package be.kuleuven.candycrush.controller;

import be.kuleuven.candycrush.CandycrushApplication;
import be.kuleuven.candycrush.model.CandycrushModel;
import be.kuleuven.candycrush.model.LoginDataBaseModel;
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
    public Label scoreLbl;
    public Label hiScoreLbl;
    @FXML
    private AnchorPane speelbord;
    private CandycrushModel model;
    private CandycrushView view;

    private LoginDataBaseModel loginDataBaseModel;

    @FXML
    void initialize() {
        loginDataBaseModel = new LoginDataBaseModel();
        StartResetButton.setText("Start");
        welcomeLabel.setText("Hello "+CandycrushApplication.loggedInPlayer+", enjoy the game!");
        model = new CandycrushModel();
        StartResetButton.setOnAction(action -> startOrReset());
        updateScoreLbls(false);
    }

    private void startOrReset() {
        if(speelbord.getChildren().isEmpty()){
            view = new CandycrushView(model);
            view.setOnMouseClicked(this::onCandyClicked);
            speelbord.getChildren().add(view);
            StartResetButton.setText("Reset");
            updateScoreLbls(true);
        } else {
            model.reset();
            updateScoreLbls(false);
        }
        update();
    }

    public void update(){
        view.update();
        if(model.highscoreIsUpdated()){
            loginDataBaseModel.setHighscore(CandycrushApplication.loggedInPlayer, model.getHighScore());
        }
        updateScoreLbls(true);
    }

    public void onCandyClicked(MouseEvent me){
        int candyIndex = view.getIndexOfClicked(me);
        model.candyWithIndexSelected(candyIndex);
        update();
    }

    private void updateScoreLbls(boolean active){
        if(!active){
            scoreLbl.setText(" --- ");
            hiScoreLbl.setText(" --- ");
            return;
        }
        hiScoreLbl.setText(""+model.getHighScore());
        scoreLbl.setText(""+model.getScore());
    }

}
