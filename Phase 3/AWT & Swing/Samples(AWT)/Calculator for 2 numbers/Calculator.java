import java.awt.*;
import java.awt.event.*;

public class Calculator {
    public static void main(String[] args) {
        Frame f = new Frame("Basic Calculator");
        Label header = new Label("Minimilistic Calculator");
        Label first = new Label("Number X");
        TextField inputFirst = new TextField();
        Label second = new Label("Number Y");
        TextField inputSecond = new TextField();

        header.setAlignment(Label.CENTER);
        header.setBounds(150, 30, 200, 30);

        first.setBounds(100, 100, 80, 30);
        inputFirst.setBounds(200, 100, 80, 30);

        second.setBounds(100, 150, 80, 30);
        inputSecond.setBounds(200, 150, 80, 30);

        // Result box
        Label resultLabel = new Label("Result");
        resultLabel.setBounds(100, 200, 80, 30);
        TextField resultField = new TextField();
        resultField.setBounds(200, 200, 80, 30);
        resultField.setEditable(false);
        resultField.setFocusable(false);
        // resultField.setForeground(Color.BLUE);

        // Buttons
        Button add = new Button("Add");
        Button mul = new Button("Multiply");
        Button sub = new Button("Subtract");
        Button div = new Button("Divide");
        Button clear = new Button("Clear");
        add.setBounds(75, 275, 50, 30);
        sub.setBounds(150, 275, 60, 30);
        mul.setBounds(225, 275, 50, 30);
        div.setBounds(300, 275, 50, 30);
        clear.setBounds(375, 275, 50, 30);

        // TextArea resultBox=new TextArea()

        f.add(header);
        f.add(first);
        f.add(inputFirst);
        f.add(inputSecond);
        f.add(second);
        f.add(resultField);
        f.add(resultLabel);
        f.add(add);
        f.add(mul);
        f.add(sub);
        f.add(div);
        f.add(clear);

        f.setSize(500, 400);
        f.setBackground(Color.LIGHT_GRAY);
        f.setLayout(null);
        f.setVisible(true);

        // Actions
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String empty = "";
                    if (empty.equals(inputFirst.getText()) || empty.equals(inputSecond.getText())) {
                        emptyInput(f, "Error! Fields can't be empty!!!");
                        return;

                    }
                    double num1 = Double.parseDouble(inputFirst.getText());
                    double num2 = Double.parseDouble(inputSecond.getText());
                    double sum = num1 + num2;
                    resultField.setText(String.format("%.2f", sum));

                } catch (NumberFormatException ex) {
                    showExceptionDialog(f, "Invalid Input(s)!");
                }
            }
        });

        sub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String empty = "";
                    if (empty.equals(inputFirst.getText()) || empty.equals(inputSecond.getText())) {
                        emptyInput(f, "Error! Fields can't be empty!!!");
                        return;

                    }
                    double num1 = Double.parseDouble(inputFirst.getText());
                    double num2 = Double.parseDouble(inputSecond.getText());
                    double sub = num1 - num2;
                    resultField.setText(String.format("%.2f", sub));
                } catch (NumberFormatException ex) {
                    showExceptionDialog(f, "Invalid Input(s)!");

                }
            }
        });

        mul.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String empty = "";
                    if (empty.equals(inputFirst.getText()) || empty.equals(inputSecond.getText())) {
                        emptyInput(f, "Error! Fields can't be empty!!!");
                        return;

                    }
                    double num1 = Double.parseDouble(inputFirst.getText());
                    double num2 = Double.parseDouble(inputSecond.getText());
                    double mul = num1 * num2;
                    resultField.setText(String.format("%.2f", mul));
                } catch (NumberFormatException ex) {
                    showExceptionDialog(f, "Invalid Input(s)!");

                }
            }
        });

        div.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double num1 = Double.parseDouble(inputFirst.getText());
                    double num2 = Double.parseDouble(inputSecond.getText());
                    String empty = "";
                    if (num2 == 0) {
                        showExceptionDialog(f, "Cannot divide by zero!");
                    } else if (empty.equals(inputFirst.getText()) || empty.equals(inputSecond.getText())) {
                        emptyInput(f, "Error! Fields can't be empty!!!");
                        return;

                    } else {
                        double div = num1 / num2;
                        resultField.setText(String.format("%.2f", div));
                    }

                } catch (NumberFormatException ex) {
                    showExceptionDialog(f, "Invalid Input(s)!");

                } catch (Exception ex) {
                    showExceptionDialog(f, "Unexpected error occurred!");
                }
            }
        });

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputFirst.setText("");
                inputSecond.setText("");
                resultField.setText("");

            }
        });

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                f.dispose();
            }
        });

    }

    // General exception popup method
    private static void showExceptionDialog(Frame f, String message) {
        Dialog d = new Dialog(f, "Error", true);
        d.setLayout(new FlowLayout());

        Label msg = new Label(message);
        Button ok = new Button("OK");
        ok.addActionListener(e -> d.setVisible(false));

        d.add(msg);
        d.add(ok);
        d.setSize(200, 100);
        d.setLocationRelativeTo(null);
        d.setVisible(true);
    }

    // General empty input handler
    private static void emptyInput(Frame f, String message) {
        Dialog d = new Dialog(f, "Error", true);
        d.setLayout(new FlowLayout());

        Label msg = new Label(message);
        Button ok = new Button("OK");
        ok.addActionListener(e -> d.setVisible(false));

        d.add(msg);
        d.add(ok);
        d.setSize(200, 100);
        d.setLocationRelativeTo(null);
        d.setVisible(true);

    }

}
