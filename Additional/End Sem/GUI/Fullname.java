import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

public class Fullname {
    public static void main(String[] args) {
        JFrame frame = new JFrame("DEMO DEMO DEMO");
        frame.setLayout(new GridLayout(4, 2, 10, 10));
        JLabel fNameLabel = new JLabel("First name: ");
        JTextField fNameField = new JTextField();
        JLabel lNameLabel = new JLabel("Last name: ");
        JTextField lNameField = new JTextField();

        JLabel FullName = new JLabel("Full name: ");
        JLabel fullNameLabel = new JLabel("");

        JButton submit = new JButton("Submit");
        fNameLabel.setBounds(20, 150, 150, 30);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fullname = fNameField.getText() + " " + lNameField.getText();
                fullNameLabel.setText(fullname);
            }
        });

        frame.add(fNameLabel);
        frame.add(fNameField);
        frame.add(lNameLabel);
        frame.add(lNameField);
        frame.add(FullName);
        frame.add(fullNameLabel);
        frame.add(submit);
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
