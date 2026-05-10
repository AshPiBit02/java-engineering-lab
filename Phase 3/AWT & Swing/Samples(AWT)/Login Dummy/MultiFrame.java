import java.awt.*;
import java.awt.event.*;

public class MultiFrame {
    public static void main(String[] args) {

        // Frame 1
        Frame loginFrame = new Frame("Login Frame");

        Label l1 = new Label("Username:");
        Label l2 = new Label("Password:");
        TextField tf1 = new TextField(20);
        TextField tf2 = new TextField(20);
        tf2.setEchoChar('*');
        Button loginBtn = new Button("Login");

        l1.setBounds(50, 50, 80, 30);
        tf1.setBounds(150, 50, 150, 30);
        l2.setBounds(50, 100, 80, 30);
        tf2.setBounds(150, 100, 150, 30);
        loginBtn.setBounds(150, 150, 80, 30);

        loginFrame.add(l1);
        loginFrame.add(tf1);
        loginFrame.add(l2);
        loginFrame.add(tf2);
        loginFrame.add(loginBtn);

        loginFrame.setSize(400, 250);
        loginFrame.setLayout(null);
        loginFrame.setVisible(true);

        // Frame 2
        Frame dashboardFrame = new Frame("Dashboard Frame");
        Label loggedInLabel = new Label("Logged In Successfully!");
        loggedInLabel.setBounds(100, 100, 200, 30);
        Button logoutBtn = new Button("Logout");
        logoutBtn.setBounds(150, 150, 80, 30);

        dashboardFrame.add(loggedInLabel);
        dashboardFrame.add(logoutBtn);
        dashboardFrame.setSize(400, 250);
        dashboardFrame.setLayout(null);

        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide login frame, show dashboard
                loginFrame.setVisible(false);
                dashboardFrame.setVisible(true);
            }
        });

        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide dashboard, show login frame
                dashboardFrame.setVisible(false);
                loginFrame.setVisible(true);
            }
        });

        // Close both frames properly
        loginFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                loginFrame.dispose();
            }
        });
        dashboardFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dashboardFrame.dispose();
            }
        });

    }

}
