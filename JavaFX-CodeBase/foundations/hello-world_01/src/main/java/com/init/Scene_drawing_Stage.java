// src/main/java/com/init/SecondStage.java
package com.init;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Scene_drawing_Stage {
    public void show() {
        Group root = new Group();
        Text text = new Text();

        text.setText("Hello from Second stage");
        text.setX(50);
        text.setY(200);
        text.setFont(Font.font("Verdana", 50));
        text.setFill(Color.GREEN);

        Scene scene = new Scene(root, 600, 600, Color.LIGHTSKYBLUE);
        Stage stage = new Stage();

        Line line = new Line();
        line.setStartX(100);
        line.setStartY(50);

        line.setEndX(500);
        line.setEndY(100);
        line.setStrokeWidth(10);
        line.setStroke(Color.RED);
        line.setOpacity(0.5);
        line.setRotate(45);

        root.getChildren().add(line);
        root.getChildren().add(text);
        stage.setScene(scene);
        stage.show();
    }
}