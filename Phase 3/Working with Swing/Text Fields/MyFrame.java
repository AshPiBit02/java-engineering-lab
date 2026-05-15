import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;

public class MyFrame extends JFrame implements ActionListener {
    JButton button;
    JTextField textfield;

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        button = new JButton("Submit");
        button.addActionListener(this);
        button.setFocusable(false);

        textfield = new JTextField();
        textfield.setPreferredSize(new Dimension(250, 40));
        textfield.setFont(new Font("Concolas", Font.PLAIN, 14));
        textfield.setForeground(Color.DARK_GRAY);
        textfield.setBackground(Color.LIGHT_GRAY);
        textfield.setCaretColor(Color.WHITE);
        textfield.setText("Username");
        this.add(button);
        this.add(textfield);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String empty = "";
        if (e.getSource() == button) {
            if (empty.equals(textfield.getText())) {
                JOptionPane.showMessageDialog(null, "Can't Input Empty String", "Try Again",
                        JOptionPane.INFORMATION_MESSAGE);

            } else {
                System.out.println(textfield.getText());
                textfield.setText(empty);

            }
        }
    }

}
