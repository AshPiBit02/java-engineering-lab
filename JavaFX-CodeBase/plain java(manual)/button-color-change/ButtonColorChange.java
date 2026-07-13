import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ButtonColorChange extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Button Color Changer");

        Button greenBtn = new Button("Green");
        Button redBtn = new Button("RED");

        greenBtn.setOnAction(e -> {
            redBtn.setStyle("-fx-background-color: green;");
            System.out.println("Green button clicked!");
        });

        redBtn.setOnAction(e -> {
            greenBtn.setStyle("-fx-background-color: red;");
            System.out.println("Red button clicked!");
        });

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(100);

        gp.add(greenBtn, 0, 3);
        gp.add(redBtn, 1, 3);

        stage.setScene(new Scene(gp, 300, 200));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
