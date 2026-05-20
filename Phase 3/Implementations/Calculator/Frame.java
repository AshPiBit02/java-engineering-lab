import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
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

    Frame() {

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
        mod = new JButton("%");
        expo = new JButton("x10ˣ");
        squr = new JButton("x²");
        zerozero = new JButton("00");
        left = new JButton("(");
        right = new JButton(")");

        JButton[] buttons = { seven, eight, nine, add, four, five, six, sub, one, two, three, mul, zero, zerozero, dot,
                div,
                mod, expo, squr, result };

        // resultPanel setup
        resultPanel = new JPanel();
        resultPanel.setBounds(10, 20, 300, 80);
        resultPanel.setLayout(new GridLayout(2, 1));
        resultPanel.setBackground(Color.WHITE);

        // inputText
        inputText = new JTextField();
        inputText.setFont(new Font("Monospaced", Font.BOLD, 18));
        inputText.setBorder(null);

        // outputText
        outputText = new JTextField("0");
        outputText.setFont(new Font("Monospaced", Font.BOLD, 18));
        outputText.setHorizontalAlignment(JTextField.RIGHT);
        outputText.setBorder(null);
        outputText.setEditable(false);
        outputText.setBackground(Color.WHITE);

        // add in order
        resultPanel.add(inputText);
        resultPanel.add(outputText);
        this.add(resultPanel);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBounds(20, 150, 290, 300);
        buttonPanel.setBackground(Color.GRAY);
        buttonPanel.setOpaque(true);
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));

        // parentheses
        left.setBounds(22, 105, 68, 40);
        left.setFocusable(false);
        left.setBorder(new LineBorder(Color.GRAY, 4));
        left.setBackground(new Color(233, 223, 204));
        left.addActionListener(this);

        right.setBounds(95, 105, 68, 40);
        right.setFocusable(false);
        right.setBorder(new LineBorder(Color.GRAY, 4));
        right.setBackground(new Color(233, 223, 204));
        right.addActionListener(this);
        this.add(left);
        this.add(right);

        // del button added to panel
        del.setBounds(167, 105, 68, 40);
        del.setFocusable(false);
        del.setBorder(new LineBorder(Color.GRAY, 4));
        del.setBackground(new Color(233, 223, 204));
        this.add(del);
        del.addActionListener(this);

        // Clear button added to panel
        clear.setBounds(240, 105, 70, 40);
        clear.setFocusable(false);
        clear.setBorder(new LineBorder(Color.GRAY, 4));
        clear.setBackground(new Color(233, 223, 204));
        this.add(clear);
        clear.addActionListener(this);

        for (JButton b : buttons) {
            buttonPanel.add(b);
            b.setFocusable(false);
            b.setBackground(new Color(233, 223, 204));
            b.addActionListener(this);
        }

        getContentPane().setBackground(new Color(102, 102, 102));
        this.setLayout(null);
        this.add(buttonPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(350, 500);
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
                try {
                    String expr = current.trim();
                    double evalResult = evaluate(expr);

                    // Display as integer if result is a whole number (e.g. 6.0 → 6)
                    if (evalResult == (long) evalResult)
                        outputText.setText(String.valueOf((long) evalResult));
                    else
                        outputText.setText(String.valueOf(evalResult));

                } catch (Exception ex) {
                    outputText.setText("Syntax Error");
                    ex.printStackTrace();
                }
                break;

            case "1":
                inputText.setText(current + "1");
                break;
            case "2":
                inputText.setText(current + "2");
                break;
            case "3":
                inputText.setText(current + "3");
                break;
            case "4":
                inputText.setText(current + "4");
                break;
            case "5":
                inputText.setText(current + "5");
                break;
            case "6":
                inputText.setText(current + "6");
                break;
            case "7":
                inputText.setText(current + "7");
                break;
            case "8":
                inputText.setText(current + "8");
                break;
            case "9":
                inputText.setText(current + "9");
                break;
            case "0":
                inputText.setText(current + "0");
                break;
            case "00":
                inputText.setText(current + "00");
                break;
            case ".":
                inputText.setText(current + ".");
                break;
            case "%":
                inputText.setText(current + "%");
                break;
            case "+":
                inputText.setText(current + "+");
                break;
            case "-":
                inputText.setText(current + "-");
                break;
            case "×":
                inputText.setText(current + "*");
                break;
            case "÷":
                inputText.setText(current + "/");
                break;
            case "x²":
                inputText.setText("Math.pow(" + current + ",2)");
                break;
            case "x10ˣ":
                inputText.setText(current + "*Math.pow(10,");
                break;
            case "(":
                inputText.setText(current + "(");
                break;
            case ")":
                inputText.setText(current + ")");
                break;
            default:
                break;
        }
    }

    /** Entry point: strips whitespace and starts parsing from position 0. */
    private double evaluate(String expr) {
        return parseAddSub(expr.replaceAll("\\s+", ""), new int[] { 0 });
    }

    /** Handles addition and subtraction (lowest precedence). */
    private double parseAddSub(String expr, int[] pos) {
        double leftVal = parseMulDiv(expr, pos);
        while (pos[0] < expr.length()) {
            char op = expr.charAt(pos[0]);
            if (op == '+' || op == '-') {
                pos[0]++;
                double rightVal = parseMulDiv(expr, pos);
                leftVal = (op == '+') ? leftVal + rightVal : leftVal - rightVal;
            } else {
                break;
            }
        }
        return leftVal;
    }

    /** Handles multiplication and division (medium precedence). */
    private double parseMulDiv(String expr, int[] pos) {
        double leftVal = parsePrimary(expr, pos);
        while (pos[0] < expr.length()) {
            char op = expr.charAt(pos[0]);
            if (op == '*' || op == '/') {
                pos[0]++;
                double rightVal = parsePrimary(expr, pos);
                leftVal = (op == '*') ? leftVal * rightVal : leftVal / rightVal;
            } else {
                break;
            }
        }
        return leftVal;
    }

    private double parsePrimary(String expr, int[] pos) {
        // Math.pow(base, exponent)
        if (expr.startsWith("Math.pow(", pos[0])) {
            pos[0] += 9; // skip "Math.pow("
            double base = parseAddSub(expr, pos);
            pos[0]++; // skip ','
            double exp = parseAddSub(expr, pos);
            pos[0]++; // skip ')'
            return Math.pow(base, exp);
        }

        if (expr.charAt(pos[0]) == '-') {
            pos[0]++;
            return -parsePrimary(expr, pos);
        }

        // Parenthesised expression
        if (expr.charAt(pos[0]) == '(') {
            pos[0]++; // skip '('
            double val = parseAddSub(expr, pos);
            pos[0]++; // skip ')'
            return val;
        }

        // Numeric literal (integer or decimal)
        int start = pos[0];
        while (pos[0] < expr.length() &&
                (Character.isDigit(expr.charAt(pos[0])) || expr.charAt(pos[0]) == '.')) {
            pos[0]++;
        }
        return Double.parseDouble(expr.substring(start, pos[0]));
    }
}