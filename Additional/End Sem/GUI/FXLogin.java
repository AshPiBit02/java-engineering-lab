import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class FXLogin extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("JavaFX Welcome");

        Label welcome = new Label("Welcome");
        welcome.setFont(new Font(24));

        Label userLabel = new Label("User Name: ");
        TextField userField = new TextField();

        Label passLabel = new Label("Password: ");
        PasswordField passField = new PasswordField();

        Label statusLabel = new Label("Status: ");
        Label statusField = new Label();

        Button btn = new Button("Sign in");
        btn.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();
            if (username.equals("PU") && password.equals("PU@2025Spring")) {
                statusField.setText("Login Successful");
            } else {
                statusField.setText("Unknown user or incorrect password!");
            }
        });

        GridPane gp = new GridPane();
        gp.setHgap(15);
        gp.setVgap(10);

        gp.add(welcome, 0, 0, 2, 1);
        gp.add(userLabel, 0, 1);
        gp.add(userField, 1, 1);
        gp.add(passLabel, 0, 2);
        gp.add(passField, 1, 2);
        gp.add(statusLabel, 0, 3);
        gp.add(statusField, 1, 3);
        gp.add(btn, 1, 4);

        Scene scene = new Scene(gp, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
