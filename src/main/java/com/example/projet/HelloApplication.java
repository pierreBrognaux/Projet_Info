package com.example.projet;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: red;");

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Fenêtre Rouge");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
