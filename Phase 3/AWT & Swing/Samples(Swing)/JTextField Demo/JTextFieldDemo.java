import javax.swing.*;
import java.awt.*;

public class JTextFieldDemo extends JFrame {
    JTextFieldDemo() {
        setLayout(new GridLayout(3, 2, 10, 10));
        ((JPanel) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(new JLabel("Username:"));
        JTextField userTF = new JTextField(15);
        add(userTF);

        add(new JLabel("Email:"));
        JTextField emailTF = new JTextField("user@example.com");
        emailTF.setForeground(Color.GRAY);
        add(emailTF);

        add(new JLabel("Age:"));
        JTextField ageTF = new JTextField(5);
        ageTF.setHorizontalAlignment(JTextField.CENTER);
        add(ageTF);

        setSize(350, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new JTextFieldDemo();
    }
}