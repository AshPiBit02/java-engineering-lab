import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Login extends Application {
    @Override
    public void start(Stage stage) {

        stage.setTitle("JavaFX Welcome");
        Label title = new Label("Welcome");
        title.setFont(new javafx.scene.text.Font(20));

        Label userLabel = new Label("User Name: ");
        Label passwordLabel = new Label("Password: ");

        TextField userField = new TextField();
        PasswordField passField = new PasswordField();

        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText(null);
        Stage IalertStage = (Stage) infoAlert.getDialogPane().getScene().getWindow();
        IalertStage.setX(475);
        IalertStage.setY(60);

        Button signBtn = new Button("sign in");
        signBtn.setOnAction(e -> {
            if (userField.getText().isEmpty() || passField.getText().isEmpty()) {
                infoAlert.setContentText("Fields can't be Empty!");
                infoAlert.showAndWait();
            } else {
                if (userField.getText().equals("PU") && passField.getText().equals("PU@2025Spring")) {
                    infoAlert.setContentText("Login Successful!");
                    infoAlert.showAndWait();
                } else {
                    infoAlert.setContentText("Invalid Username or Incorrect Password!");
                    infoAlert.showAndWait();
                }
            }
        });

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(12);
        gp.setVgap(10);

        gp.add(title, 0, 0, 2, 2);
        gp.add(userLabel, 0, 2);
        gp.add(userField, 2, 2);
        gp.add(passwordLabel, 0, 3);
        gp.add(passField, 2, 3);

        gp.add(signBtn, 2, 4);

        stage.setScene(new Scene(gp, 300, 200));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}