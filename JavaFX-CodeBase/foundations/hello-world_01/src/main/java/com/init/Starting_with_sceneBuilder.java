package com.init;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starting_with_sceneBuilder {
    Stage stage;

    Starting_with_sceneBuilder() throws IOException {
        stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/new.fxml"));
        Scene scene = new Scene(root, 400, 400);

        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }
}
