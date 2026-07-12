import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Label titleLabel = new Label("JavaFX Program To Calculate Sum");
        // titleLabel.setLayoutX()
        Label firstLabel = new Label("First Number: ");
        Label secondLabel = new Label("Second Number: ");
        Label resultLabel = new Label("Result: ");

        TextField num1 = new TextField();
        TextField num2 = new TextField();
        TextField result = new TextField();
        result.setEditable(false);

        Button btn = new Button("Add");

        btn.setOnAction(e -> {
            try {
                int x = Integer.parseInt(num1.getText());
                int y = Integer.parseInt(num2.getText());
                result.setText(String.valueOf(x + y));
            } catch (NumberFormatException ex) {
                result.setText("Invalid input!");
                num1.setText("");
                num2.setText("");
            }
        });

        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);

        root.add(firstLabel, 0, 0);
        root.add(num1, 1, 0);

        root.add(secondLabel, 0, 1);
        root.add(num2, 1, 1);

        root.add(resultLabel, 0, 2);
        root.add(result, 1, 2);

        root.add(btn, 1, 3);

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("JavaFX Sum Calculator");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
