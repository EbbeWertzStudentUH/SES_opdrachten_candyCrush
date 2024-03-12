package be.kuleuven.candycrush;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CandycrushApplication extends Application {

    private static Stage stage;

    public static String loggedInPlayer;
    //ik gebruik deze class als een makkelijk accessible plaats.
    //Het dient voor deze string puur enkel als data containter,
    //vandaar dat deze public mag zijn.

    @Override
    public void start(Stage stage) {
        CandycrushApplication.stage = stage;
        setScene("login_screen.fxml");
        stage.setTitle("CandyCrush");
        stage.setResizable(false);
        stage.show();
    }

    public static void setScene(String fxml){
        final FXMLLoader fxmlLoader = new FXMLLoader(CandycrushApplication.class.getResource("fxml/"+fxml));
        try {
            final Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        launch();
    }
}