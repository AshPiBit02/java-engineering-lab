package com.init;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testCRUD {
    Stage stage;

    testCRUD() throws Exception {
        stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/testforCRUD.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

}
