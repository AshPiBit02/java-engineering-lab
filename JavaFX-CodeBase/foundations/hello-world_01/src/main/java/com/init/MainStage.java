package com.init;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainStage {
    private Stage stage;

    public MainStage() {
        stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, Color.BROWN);

        Image icon = new Image(getClass().getResourceAsStream("/Images/showw.jpg"));

        stage.getIcons().add(icon);
        stage.setTitle("First stage");
        stage.setWidth(420);
        stage.setHeight(300);
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("You can't escape unless you press q");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }
}