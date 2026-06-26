package com.init;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class queryMessage {
    public static void sqlMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Label contentlabel = (Label) alert.getDialogPane().lookup(".content.label");
        if (contentlabel != null) {
            contentlabel.setStyle("-fx-font-size: 18px;");
        }

        alert.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> alert.close());
        delay.play();
    }

}
