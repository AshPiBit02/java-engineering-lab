import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Converter extends JFrame {
    JFrame frame;
    JLabel title;
    String[] temps = { "Celsius(°C)", "Fahrenheit(°F)", "Kelvin(K)", "Rankine(°R)", "Réaumur(R°é)" };
    JComboBox<String> left;
    JComboBox<String> right;
    JTextField leftText;
    JTextField rightText;

    Converter() {
        this.setTitle("Temperature Converter");
        this.setSize(500, 300);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#d5e27e"));

        title = new JLabel();
        title.setBounds(50, 0, 400, 80);
        title.setText("Dynamic Temperature Converter");
        title.setForeground(Color.decode("#020226"));
        title.setFont(new Font("Arabic Typesetting", Font.BOLD, 25));

        left = new JComboBox<>(temps);
        left.setBounds(75, 110, 120, 30);
        left.setBackground(Color.decode("#d9e7bd"));
        left.setBorder(null);
        left.setFocusable(false);

        right = new JComboBox<>(temps);
        right.setBounds(300, 110, 120, 30);
        right.setSelectedIndex(1);
        right.setBackground(Color.decode("#d9e7bd"));
        right.setBorder(null);
        right.setFocusable(false);

        leftText = new JTextField();
        leftText.setFont(new Font("Monospaced", Font.BOLD, 14));
        leftText.setBounds(100, 75, 60, 30);
        leftText.setBorder(null);

        rightText = new JTextField();
        rightText.setFont(new Font("Monospaced", Font.BOLD, 14));
        rightText.setBounds(325, 75, 60, 30);
        rightText.setBorder(null);
        // rightText.setOpaque(true);

        this.add(rightText);
        this.add(leftText);
        this.add(right);
        this.add(left);
        this.add(title);
        this.setVisible(true);
    }

}
