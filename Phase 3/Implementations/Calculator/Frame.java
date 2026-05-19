
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;

public class Frame extends JFrame {

    JButton one, two, three, four, five, six, seven, eight, nine, zero, result, dot, clear, mul, div, sub, add, del,
            mod, zerozero, expo, squr;
    JPanel buttonPanel;

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

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBounds(20, 150, 290, 300);
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setOpaque(true);
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        for (JButton b : buttons) {
            buttonPanel.add(b);
            b.setFocusable(false);
        }

        this.setLayout(null);
        this.add(buttonPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(350, 500);
        // this.add(button);
        this.setVisible(true);
    }

}
