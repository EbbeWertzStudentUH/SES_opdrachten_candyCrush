package be.kuleuven.candycrush.controller;

import be.kuleuven.candycrush.CandycrushApplication;
import be.kuleuven.candycrush.model.LoginDataBaseModel;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;

public class LoginScreenController {
    public Button loginButton;
    public ToggleGroup loginOptions;
    public RadioButton loginExistingRadio;
    public RadioButton loginNewRadio;
    public PasswordField passwordField;
    public TextField nameField;
    public Label message;


    private LoginDataBaseModel loginDataBaseModel;

    public void initialize() {

        loginDataBaseModel = new LoginDataBaseModel();


        nameField.textProperty().addListener(buttonsUpdater());
        passwordField.textProperty().addListener(buttonsUpdater());

        loginExistingRadio.setOnAction(action -> update());
        loginNewRadio.setOnAction(action -> update());

        loginButton.setOnAction(action -> login());


    }

    private ChangeListener<String> buttonsUpdater(){
        return (observable, oldValue, newValue) -> update();
    }

    private void update(){
        setMessage("Enter your login");
        boolean nameGood = checkNameFieldIsGood();
        boolean passwordGood = false;
        if(nameGood){
            passwordGood = checkPasswordFieldIsGood(nameField.getText());
        }
        loginButton.setDisable(!(nameGood && passwordGood));
    }

    private void login(){
        if (!optionIsToLoginToExisting()) {
            loginDataBaseModel.addNewUser(nameField.getText(), passwordField.getText());
        }
        CandycrushApplication.setScene("game_screen.fxml");
    }

    private boolean checkNameFieldIsGood(){
        final String name = nameField.getText();
        if(name.isEmpty()) return false;
        if(optionIsToLoginToExisting()){
            if(!loginDataBaseModel.nameExists(name)) {
                setErrorMessage("There is no user with this name.");
                return false;
            }
        } else {
            if(loginDataBaseModel.nameExists(name)) {
                setErrorMessage("A user with this name already exists.");
                return false;
            }
        }
        return true;
    }
    private boolean checkPasswordFieldIsGood(String name){
        final String password = passwordField.getText();
        if(password.isEmpty()) return false;
        if(!optionIsToLoginToExisting()) return true;
        if(loginDataBaseModel.passWordIsCorrect(name, password)){
            setMessage("Correct login");
            return true;
        } else {
            setErrorMessage("Password is incorrect.");
            return false;
        }
    }

    private boolean optionIsToLoginToExisting(){
        return loginExistingRadio.isSelected();
        //als andere geselecteerd word gaat deze automatisch gedeselecteerd worden
    }

    private void setMessage(String message){
        this.message.setText(message);
        this.message.getStyleClass().clear();
    }

    private void setErrorMessage(String message){
        this.message.setText(message);
        this.message.getStyleClass().add("errormessage");
    }

}
