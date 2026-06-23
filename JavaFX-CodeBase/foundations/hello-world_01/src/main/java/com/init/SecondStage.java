// src/main/java/com/init/SecondStage.java
package com.init;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SecondStage {
    public void show() {
        Group root = new Group();
        Text text = new Text();
        text.setText("Hello from Second stage");
        text.setX(200);
        text.setY(200);
        root.getChildren().add(text);
        Scene scene = new Scene(root, 600, 350, Color.LIGHTSKYBLUE);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
    }
}