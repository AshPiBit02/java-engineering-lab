import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Calci extends Application {
    @Override
    public void start(Stage stage) {

        Label titleLabel = new Label("Basic JavaFX Calculator");

        Label firstLabel = new Label("First Number: ");
        Label secondLabel = new Label("Second Number: ");
        Label resultLabel = new Label("Result: ");

        TextField firstNum = new TextField();
        TextField secondNum = new TextField();
        TextField resultField = new TextField();
        resultField.setEditable(false);

        Button addBtn = new Button("Add");
        Button subBtn = new Button("Subtract");
        Button mulBtn = new Button("Multiply");
        Button divBtn = new Button("Divide");

        addBtn.setOnAction(e -> {
            resultField.setText(
                    String.valueOf(Integer.parseInt(firstNum.getText()) + Integer.parseInt(secondNum.getText())));
        });

        subBtn.setOnAction(e -> {
            resultField.setText(
                    String.valueOf(Integer.parseInt(firstNum.getText()) - Integer.parseInt(secondNum.getText())));
        });
        mulBtn.setOnAction(e -> {
            resultField.setText(
                    String.valueOf(Integer.parseInt(firstNum.getText()) * Integer.parseInt(secondNum.getText())));
        });
        divBtn.setOnAction(e -> {
            resultField.setText(
                    String.valueOf(Integer.parseInt(firstNum.getText()) / Integer.parseInt(secondNum.getText())));
        });

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(12);
        gp.setVgap(10);

        gp.add(titleLabel, 0, 0, 2, 1);

        gp.add(firstLabel, 0, 1);
        gp.add(firstNum, 1, 1);

        gp.add(secondLabel, 0, 2);
        gp.add(secondNum, 1, 2);

        gp.add(resultLabel, 0, 3);
        gp.add(resultField, 1, 3);

        gp.add(addBtn, 0, 4);
        gp.add(subBtn, 1, 4);
        gp.add(mulBtn, 0, 5);
        gp.add(divBtn, 1, 5);

        stage.setScene(new Scene(gp, 500, 400));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
