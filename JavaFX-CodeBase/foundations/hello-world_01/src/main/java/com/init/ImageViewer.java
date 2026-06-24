package com.init;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ImageViewer {
    Stage stage;

    ImageViewer() throws IOException {
        stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/image_view.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
    }

    public void show() {
        stage.show();

    }

}
