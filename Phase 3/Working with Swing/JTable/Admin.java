import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Admin extends JFrame {
    JButton logout;

    Admin() {
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        logout = new JButton("Logout");
        logout.setBounds(50, 150, 200, 50);
        logout.setFocusable(false);
        logout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }

        });

        this.add(logout);
        this.setVisible(true);

    }

}
