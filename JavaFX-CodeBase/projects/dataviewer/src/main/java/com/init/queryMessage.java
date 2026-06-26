package com.init;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class queryMessage {
    public static void sqlMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> alert.close());
        delay.play();
    }

}
