package com.init;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.BROWN);

        Image icon = new Image(getClass().getResourceAsStream("/showw.jpg"));

        stage.getIcons().add(icon);
        stage.setTitle("First stage");
        stage.setWidth(420);
        stage.setHeight(300);
        stage.setResizable(false);
        // stage.setX(50);
        // stage.setY(50);
        stage.setFullScreen(true); // by default exit with esc.
        stage.setFullScreenExitHint("You can't escape unless you press q");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));

        stage.setScene(scene);
        stage.show();
        new SecondStage().show();
    }
}