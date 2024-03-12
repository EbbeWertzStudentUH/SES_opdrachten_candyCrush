module be.kuleuven.candycrush {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;

    opens be.kuleuven.candycrush to javafx.fxml;
    opens be.kuleuven.candycrush.model to com.google.gson;
    exports be.kuleuven.candycrush;
    exports be.kuleuven.candycrush.controller;
    exports be.kuleuven.candycrush.model;
    exports be.kuleuven.candycrush.view;
    opens be.kuleuven.candycrush.controller to javafx.fxml;
}