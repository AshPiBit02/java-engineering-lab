import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class basicFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Basic DEMO");

        Label userLabel = new Label("Username: ");
        TextField userField = new TextField();

        Label passLabel = new Label("Password: ");
        TextField passField = new TextField();

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            if (!userField.getText().isEmpty() && !passField.isEmpty()) {
                System.out.println("Submit successful");
            } else {
                System.out.println("Field can't be empty!");
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passField, 1, 1);
        grid.add(submit, 1, 2);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
