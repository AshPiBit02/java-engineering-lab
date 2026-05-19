
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.awt.*;

public class Frame extends JFrame {

    JButton one, two, three, four, five, six, seven, eight, nine, zero, result, dot, clear, mul, div, sub, add, del,
            mod, zerozero, expo, squr;
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

        JButton[] buttons = { seven, eight, nine, add, four, five, six, sub, one, two, three, mul, zero, zerozero, dot,
                div,
                mod, expo, squr, result };

        // will contain the display section
        resultPanel = new JPanel();
        resultPanel.setBounds(10, 20, 300, 80);
        resultPanel.setLayout(null);
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setOpaque(true);

        // InputText will display the input from the user
        inputText = new JTextField();
        inputText.setFont(new Font("Monospaced", Font.BOLD, 18));
        inputText.setBounds(2, 2, 300, 40);
        inputText.setBorder(null);
        inputText.setLayout(null);
        // inputText.setBackground(Color.RED);// for reference

        // OutputText will display the result
        outputText = new JTextField("0");
        // Align the output text displayer to the bottom right corner
        outputText.setBounds(resultPanel.getWidth() - 250, resultPanel.getHeight() - 40, 250, 40);
        outputText.setFont(new Font("Monospaced", Font.BOLD, 18));
        // Align text inside the field to the right
        outputText.setHorizontalAlignment(JTextField.RIGHT);
        outputText.setBorder(null);
        outputText.setBackground(Color.WHITE);
        // This field will only display the result
        outputText.setEditable(false);
        outputText.setFocusable(false);

        resultPanel.add(outputText);
        resultPanel.add(inputText);

        this.add(resultPanel);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBounds(20, 150, 290, 300);
        buttonPanel.setBackground(Color.GRAY);
        buttonPanel.setOpaque(true);
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));

        // del button added to panel
        del.setBounds(167, 105, 68, 40);
        del.setFocusable(false);
        del.setBorder(new LineBorder(Color.GRAY, 4));
        del.setBackground(new Color(233, 223, 204));
        this.add(del);

        // Clear button added to panel
        clear.setBounds(240, 105, 70, 40);
        clear.setFocusable(false);
        clear.setBorder(new LineBorder(Color.GRAY, 4));
        clear.setBackground(new Color(233, 223, 204));
        this.add(clear);

        for (JButton b : buttons) {
            buttonPanel.add(b);
            b.setFocusable(false);
            b.setBackground(new Color(233, 223, 204));
        }

        getContentPane().setBackground(new Color(102, 102, 102));
        // this.setOpacity(true);
        this.setLayout(null);
        this.add(buttonPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(350, 500);
        // this.add(button);
        this.setResizable(false);
        this.setVisible(true);
    }

}
