import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class palinRev {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Palindrome & Reverse");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel textLabel = new JLabel("Input any integer number: ");
        textLabel.setBounds(40, 20, 150, 30);
        JTextField inputField = new JTextField();
        inputField.setBounds(200, 20, 150, 30);
        JLabel outputLabel = new JLabel("");
        outputLabel.setBounds(40, 50, 150, 30);

        JButton palindromeCheckBtn = new JButton("CheckPalindrome");
        palindromeCheckBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                String reversed = getReverse(input);
                if (input.equals(reversed)) {
                    outputLabel.setText("True");
                } else {
                    outputLabel.setText("False");
                }
            }
        });

        palindromeCheckBtn.setBounds(40, 100, 150, 20);
        JButton reverseBtn = new JButton("Reverse");
        reverseBtn.setBounds(200, 100, 150, 20);

        frame.add(textLabel);
        frame.add(inputField);
        frame.add(outputLabel);
        frame.add(palindromeCheckBtn);
        frame.add(reverseBtn);
        frame.setVisible(true);

    }

    public static String getReverse(String input) {
        StringBuilder sb = new StringBuilder(input);
        return sb.reverse().toString();
    }
}
