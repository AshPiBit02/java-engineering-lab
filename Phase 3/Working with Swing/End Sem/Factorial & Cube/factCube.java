import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class factCube {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Factorial & Cube Finder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel inputLabel = new JLabel("Enter Number: ");
        JTextField inputField = new JTextField();
        JLabel resultLabel = new JLabel("Result: ");
        JLabel outputLabel = new JLabel("");

        JButton resultBtn = new JButton("Result");

        resultBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    int num = Integer.parseInt(inputField.getText());
                    if (num < 0) {
                        outputLabel.setText("Invalid Input!");
                    } else {
                        outputLabel.setText(String.valueOf(factorial(num)));
                    }
                } catch (NumberFormatException ex) {
                    outputLabel.setText("Invalid Input!");
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    int num = Integer.parseInt(inputField.getText());
                    outputLabel.setText(String.valueOf(num * num * num));
                } catch (NumberFormatException ex) {
                    outputLabel.setText("Invalid Input!");
                }
            }
        });

        frame.add(inputLabel);
        frame.add(inputField);
        frame.add(resultLabel);
        frame.add(outputLabel);
        frame.add(resultBtn);
        frame.setVisible(true);
    }

    private static long factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        long fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

}
