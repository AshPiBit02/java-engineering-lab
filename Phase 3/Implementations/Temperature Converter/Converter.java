import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

public class Converter extends JFrame implements ActionListener {
    JFrame frame;
    JLabel title;
    String[] temps = { "Celsius(°C)", "Fahrenheit(°F)", "Kelvin(K)", "Rankine(°R)", "Réaumur(R°é)" };
    JComboBox<String> left;
    JComboBox<String> right;
    JTextField leftText;
    JTextField rightText;
    JDialog infoDialog;
    JLabel dialogText;

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
        left.setBounds(75, 115, 120, 30);
        left.setBackground(Color.decode("#d9e7bd"));
        left.setBorder(null);
        left.setFocusable(false);

        right = new JComboBox<>(temps);
        right.setBounds(300, 115, 120, 30);
        right.setSelectedIndex(1);
        right.setBackground(Color.decode("#d9e7bd"));
        right.setBorder(null);
        right.setFocusable(false);

        leftText = new JTextField();
        leftText.setFont(new Font("Monospaced", Font.BOLD, 18));
        leftText.setBounds(90, 75, 80, 30);
        leftText.setHorizontalAlignment(JTextField.CENTER);
        leftText.setBorder(BorderFactory.createLineBorder(Color.decode("#6d8242"), 4));

        rightText = new JTextField();
        rightText.setFont(new Font("Dialog", Font.BOLD, 18));
        rightText.setBounds(315, 75, 80, 30);
        rightText.setHorizontalAlignment(JTextField.CENTER);
        rightText.setBorder(BorderFactory.createLineBorder(Color.decode("#6d8242"), 4));

        // Input Validation
        attachValidation(leftText);
        attachValidation(rightText);

        // Dynamic Info Dialog
        infoDialog = new JDialog();
        infoDialog.setTitle("Warning!");
        infoDialog.setLayout(new FlowLayout());
        dialogText = new JLabel();
        dialogText.setText("⚠︎ Warning Invalid Input!");
        dialogText.setHorizontalAlignment(JLabel.CENTER);
        dialogText.setFont(new Font("Monospaced", Font.BOLD, 16));
        infoDialog.add(dialogText);
        infoDialog.setSize(300, 100);
        infoDialog.setLocation(500, 200);
        infoDialog.setFocusableWindowState(false);
        infoDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        infoDialog.setVisible(false);

        this.add(rightText);
        this.add(leftText);
        this.add(right);
        this.add(left);
        this.add(title);
        this.setVisible(true);
    }

    private void attachValidation(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validateInput(field);
            }

            public void removeUpdate(DocumentEvent e) {
                validateInput(field);
            }

            public void changedUpdate(DocumentEvent e) {
                validateInput(field);
            }

            private void validateInput(JTextField f) {
                String text = f.getText();
                if ("".equals(text)) {
                    closeDialog();
                    return;
                }
                boolean isValid = true;
                try {
                    Double.parseDouble(text);
                    closeDialog();
                } catch (NumberFormatException ex) {
                    isValid = false;
                }
                if (!isValid) {
                    showDialog();
                }
            }
        });

    }

    private void showDialog() {
        infoDialog.setVisible(true);
    }

    private void closeDialog() {
        infoDialog.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
