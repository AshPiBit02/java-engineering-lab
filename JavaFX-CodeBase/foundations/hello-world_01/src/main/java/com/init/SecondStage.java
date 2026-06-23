// src/main/java/com/init/SecondStage.java
package com.init;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SecondStage {
    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Second Stage");
        stage.setScene(new Scene(new StackPane(new Label("Second!")), 300, 200));
        stage.show();
    }
}