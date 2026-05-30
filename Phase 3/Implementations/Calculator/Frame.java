import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.awt.*;

public class Frame extends JFrame implements ActionListener {

    JButton one, two, three, four, five, six, seven, eight, nine, zero, result, dot, clear, mul, div, sub, add, del,
            mod, zerozero, expo, squr, left, right;
    JPanel buttonPanel;
    JPanel resultPanel;
    JTextField inputText;
    JTextField outputText;
    JLabel name;

    Frame() {

        // FIX #1: setLayout(null) moved here, before any add() calls
        this.setLayout(null);

        one = new JButton("1");
        two = new JButton("2");
        three = new JButton("3");
        four = new JButton("4");
        five = new JButton("5");
        six = new JButton("6");
        seven = new JButton("7");
        eight = new JButton("8");
        nine = new JButton("9");
        zero = new JButton("0");
        result = new JButton("=");
        dot = new JButton(".");
        clear = new JButton("CLR");
        mul = new JButton("×");
        div = new JButton("÷");
        sub = new JButton("-");
        add = new JButton("+");
        del = new JButton("DEL");

        JButton[] buttons = { seven, eight, nine, add, four, five, six, sub, one, two, three, mul, zero, dot,
                div, result };

        // resultPanel setup
        resultPanel = new JPanel();
        resultPanel.setBounds(10, 20, 320, 80);
        resultPanel.setLayout(new GridLayout(2, 1));

        // inputText
        inputText = new JTextField();
        inputText.setFont(new Font("Monospaced", Font.BOLD, 18));
        inputText.setBorder(null);
        inputText.setBackground(Color.decode("#EEE5D9"));

        // outputText
        outputText = new JTextField("0");
        outputText.setFont(new Font("Monospaced", Font.BOLD, 18));
        outputText.setHorizontalAlignment(JTextField.RIGHT);
        outputText.setBorder(null);
        outputText.setEditable(false);
        outputText.setBackground(Color.decode("#EEE5D9"));

        // add in order
        resultPanel.add(inputText);
        resultPanel.add(outputText);
        this.add(resultPanel);

        name = new JLabel();
        name.setText("Juj Calci");
        name.setBounds(20, 103, 130, 40);
        name.setHorizontalAlignment(JLabel.CENTER);
        name.setVerticalAlignment(JLabel.CENTER);
        name.setBorder(null);
        name.setFont(new Font("Edwardian Script ITC", Font.BOLD, 35));
        name.setForeground(Color.WHITE);
        this.add(name);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBounds(20, 150, 290, 250);
        buttonPanel.setBackground(Color.decode("#000000"));
        buttonPanel.setOpaque(true);
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 4));

        JPanel topButtonPanel = new JPanel(null);
        topButtonPanel.setBounds(155, 103, 155, 40);
        topButtonPanel.setBackground(Color.decode("#290907"));
        topButtonPanel.setOpaque(true);

        del.setBounds(10, 0, 70, 40);
        del.setFocusable(false);
        del.setBorder(new LineBorder(Color.decode("#000000"), 4));
        del.setBackground(Color.decode("#D2B68A"));
        topButtonPanel.add(del);
        del.addActionListener(this);

        clear.setBounds(83, 0, 70, 40);
        clear.setFocusable(false);
        clear.setBorder(new LineBorder(Color.decode("#000000"), 4));
        clear.setBackground(Color.decode("#D2B68A"));
        topButtonPanel.add(clear);
        clear.addActionListener(this);

        this.add(topButtonPanel);

        for (JButton b : buttons) {
            buttonPanel.add(b);
            b.setFocusable(false);
            b.setBackground(Color.decode("#D2B68A"));
            b.addActionListener(this);
        }

        getContentPane().setBackground(Color.decode("#290907"));
        this.add(buttonPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(350, 450);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        String label = src.getText();
        String current = inputText.getText();

        switch (label) {
            case "CLR":
                inputText.setText("");
                outputText.setText("0");
                break;
            case "DEL":
                if (!current.isEmpty()) {
                    inputText.setText(current.substring(0, current.length() - 1));
                }
                break;
            case "=":
                outputText.setText(evaluate(current));
                break;
            case "×":
                inputText.setText(current + "*");
                break;
            case "÷":
                inputText.setText(current + "/");
                break;
            default:
                inputText.setText(current + label);
                break;
        }
    }

    public static String evaluate(String expr) {
        try {
            // Clean up display symbols just in case
            expr = expr.replace("×", "*").replace("÷", "/");
            double result = new ExprParser(expr).parse();
            // Return without unnecessary trailing ".0"
            if (result == Math.floor(result) && !Double.isInfinite(result))
                return String.valueOf((long) result);
            return String.valueOf(result);
        } catch (ArithmeticException e) {
            return "Error";
        } catch (Exception e) {
            return "Syntax Error";
        }
    }

    static class ExprParser {
        private final String input;
        private int pos = 0;

        ExprParser(String input) {
            this.input = input.trim();
        }

        double parse() {
            double result = parseExpr();
            if (pos < input.length())
                throw new RuntimeException("Unexpected character: " + input.charAt(pos));
            return result;
        }

        // Handles + and -
        private double parseExpr() {
            double left = parseTerm();
            while (pos < input.length()) {
                char op = input.charAt(pos);
                if (op == '+' || op == '-') {
                    pos++;
                    double right = parseTerm();
                    left = (op == '+') ? left + right : left - right;
                } else
                    break;
            }
            return left;
        }

        // Handles * and /
        private double parseTerm() {
            double left = parseFactor();
            while (pos < input.length()) {
                char op = input.charAt(pos);
                if (op == '*' || op == '/') {
                    pos++;
                    double right = parseFactor();
                    if (op == '/' && right == 0)
                        throw new ArithmeticException("Division by zero");
                    left = (op == '*') ? left * right : left / right;
                } else
                    break;
            }
            return left;
        }

        // Handles unary minus, parentheses, and numbers
        private double parseFactor() {
            if (pos >= input.length())
                throw new RuntimeException("Unexpected end of expression");

            char ch = input.charAt(pos);

            // Unary minus
            if (ch == '-') {
                pos++;
                return -parseFactor();
            }

            // Parentheses
            if (ch == '(') {
                pos++;
                double val = parseExpr();
                if (pos >= input.length() || input.charAt(pos) != ')')
                    throw new RuntimeException("Missing closing parenthesis");
                pos++;
                return val;
            }

            // Number
            int start = pos;
            while (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.'))
                pos++;
            if (start == pos)
                throw new RuntimeException("Expected number at pos " + pos);
            return Double.parseDouble(input.substring(start, pos));
        }
    }
}