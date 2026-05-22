import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class Converter extends JFrame {
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
        attachValidation(leftText, rightText);
        attachValidation(rightText, leftText);

        left.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String text = leftText.getText();
                if (!text.isEmpty()) {
                    try {
                        double value = Double.parseDouble(text);
                        isUpdating = true;
                        rightText.setText(String.format("%.2f", convert(value)));
                        isUpdating = false;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        });

        right.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String text = leftText.getText();
                if (!text.isEmpty()) {
                    try {
                        double value = Double.parseDouble(text);
                        isUpdating = true;
                        rightText.setText(String.format("%.2f", convert(value)));
                        isUpdating = false;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        });

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

    boolean isUpdating = false; // Denotes if any input is updating to prevent infinite loop

    private void attachValidation(JTextField source, JTextField target) {
        source.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validateInput(source, target);
            }

            public void removeUpdate(DocumentEvent e) {
                validateInput(source, target);
            }

            public void changedUpdate(DocumentEvent e) {
                validateInput(source, target);
            }

            private void validateInput(JTextField src, JTextField trgt) {
                if (isUpdating)
                    return; // Prevent infinite loop if other inputField is in use
                String text = src.getText();
                if (text.isEmpty()) {
                    closeDialog();
                    return;
                }
                boolean isValid = true;
                try {
                    double value = Double.parseDouble(text);
                    closeDialog();
                    isUpdating = true;
                    trgt.setText(String.format("%.2f", convert(value)));
                    isUpdating = false;
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

    private double convert(double value) {
        int fromIndex = left.getSelectedIndex();
        int toIndex = right.getSelectedIndex();
        double celsius;
        if (fromIndex == toIndex) {
            return value;
        }

        // Convert from source to celsius
        switch (fromIndex) {
            case 0:
                celsius = value;
                break;
            case 1:
                celsius = (value - 32) * (5.0 / 9.0); // Correct
                break;
            case 2:
                celsius = value - 273.15;
                break;
            case 3:
                celsius = (value - 491.67) * (5.0 / 9.0);
                break;
            case 4:
                celsius = value * (4.0 / 5.0);
                break;
            default:
                celsius = value;
        }

        // Convert from Celsius to target
        switch (toIndex) {
            case 0:
                return celsius;
            case 1:
                return (celsius * 9.0 / 5.0) + 32;
            case 2:
                return celsius + 273.15;
            case 3:
                return (celsius + 273.15) * 9.0 / 5.0;
            case 4:
                return celsius * 4.0 / 5.0;
            default:
                return 0;
        }

    }
}
