import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Calci extends Application {
    @Override
    public void start(Stage stage) {

        Label titleLabel = new Label("Basic JavaFX Calculator");
        titleLabel.setFont(new javafx.scene.text.Font(20));
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(javafx.geometry.Pos.CENTER);

        Label firstLabel = new Label("First Number: ");
        Label secondLabel = new Label("Second Number: ");
        Label resultLabel = new Label("Result: ");

        TextField firstNum = new TextField();
        TextField secondNum = new TextField();
        TextField resultField = new TextField();
        resultField.setEditable(false);
        resultField.setFocusTraversable(false);

        Button addBtn = new Button("Add");
        addBtn.setMaxWidth(Double.MAX_VALUE);
        Button subBtn = new Button("Subtract");
        subBtn.setMaxWidth(Double.MAX_VALUE);
        Button mulBtn = new Button("Multiply");
        mulBtn.setMaxWidth(Double.MAX_VALUE);
        Button divBtn = new Button("Divide");
        divBtn.setMaxWidth(Double.MAX_VALUE);

        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText("Fields can't be Empty!");

        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Invalid Input!!");

        addBtn.setOnAction(e -> {
            if (isEmpty(firstNum.getText(), secondNum.getText())) {
                infoAlert.showAndWait();
            } else {
                try {
                    resultField.setText(
                            String.valueOf(Math.round(Float.parseFloat(firstNum.getText())
                                    + Float.parseFloat(secondNum.getText()) * 100f)));
                } catch (NumberFormatException ex) {
                    errorAlert.showAndWait();
                    firstNum.setText("");
                    secondNum.setText("");
                }
            }
        });

        subBtn.setOnAction(e -> {
            if (isEmpty(firstNum.getText(), secondNum.getText())) {
                infoAlert.showAndWait();
            } else {
                try {
                    resultField.setText(
                            String.valueOf(Math.round(Float.parseFloat(firstNum.getText())
                                    - Float.parseFloat(secondNum.getText()) * 100f)));
                } catch (NumberFormatException ex) {
                    errorAlert.showAndWait();
                    firstNum.setText("");
                    secondNum.setText("");
                }

            }
        });

        mulBtn.setOnAction(e -> {
            if (isEmpty(firstNum.getText(), secondNum.getText())) {
                infoAlert.showAndWait();
            } else {
                try {

                    resultField.setText(
                            String.valueOf(Math.round(Float.parseFloat(firstNum.getText())
                                    * Float.parseFloat(secondNum.getText()) * 100f)));
                } catch (NumberFormatException ex) {
                    errorAlert.showAndWait();
                    firstNum.setText("");
                    secondNum.setText("");
                }

            }
        });
        divBtn.setOnAction(e -> {
            if (isEmpty(firstNum.getText(), secondNum.getText())) {
                infoAlert.showAndWait();
            } else {
                try {
                    resultField.setText(
                            String.valueOf(Math.round(Float.parseFloat(firstNum.getText())
                                    / Float.parseFloat(secondNum.getText()) * 100f)));
                } catch (NumberFormatException ex) {
                    errorAlert.showAndWait();
                    firstNum.setText("");
                    secondNum.setText("");
                } catch (ArithmeticException exx) {
                    errorAlert.setContentText("Divide by Zero!!!");
                    errorAlert.showAndWait();
                    firstNum.setText("");
                    secondNum.setText("");
                }

            }
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

        stage.setScene(new Scene(gp, 300, 250));
        stage.show();

    }

    public boolean isEmpty(String num1, String num2) {
        if (num1.equals("") || num2.equals("")) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
