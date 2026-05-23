import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Admin extends JFrame {
    JButton logout;
    JDialog logoutDialog;

    Admin() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        logoutDialog = new JDialog();
        logoutDialog.setTitle("Logout Successfully");
        logoutDialog.add(new JLabel("Logged from Admin"));
        logoutDialog.setBounds(500, 50, 300, 150);
        logoutDialog.setLayout(new FlowLayout());

        logout = new JButton("Logout");
        logout.setBounds(50, 150, 200, 50);
        logout.setFocusable(false);
        logout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                logoutDialog.setVisible(true);
                Timer time = new Timer(1000, ev -> {
                    logoutDialog.dispose();
                    new Login();
                    dispose();
                });
                time.setRepeats(false); // run only once
                time.start();
            }

        });

        this.add(logout);
        this.setVisible(true);

    }

}
