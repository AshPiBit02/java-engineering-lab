import java.awt.*;
import java.awt.event.*;

public class MultiFrame {
    public static void main(String[] args) {
        String defaultPassword = "clfbd5b2d7";
        String defaultUser = "Admin57";

        // Frame 1
        Frame loginFrame = new Frame("Login Frame");

        Label l1 = new Label("Username:");
        Label l2 = new Label("Password:");
        Label l0 = new Label("Instagram Login");
        TextField tf1 = new TextField(20);
        TextField tf2 = new TextField(20);
        tf2.setEchoChar('*');
        Button loginBtn = new Button("Login");

        l0.setAlignment(Label.CENTER);
        l0.setBounds(160, 30, 120, 30);
        l1.setBounds(50, 60, 80, 30);
        tf1.setBounds(150, 60, 150, 30);
        l2.setBounds(50, 110, 80, 30);
        tf2.setBounds(150, 110, 150, 30);
        loginBtn.setBounds(150, 160, 80, 30);

        loginFrame.add(l0);
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
        // Lable L=new Label("Hallo!\n\n" +
        // "Here you can share your moments,connect with friends, " +
        // "and explore content that inspires you. " +
        // "Stay creative and enjoy your time here!");
        TextArea ta = new TextArea(
                "Hallo!\n\n" +
                        "Here you can share your moments,connect with friends, " +
                        "and explore content that inspires you. " +
                        "Stay creative and enjoy your time here!",
                5, 40, TextArea.SCROLLBARS_NONE);
        ta.setBounds(50, 60, 300, 120);
        ta.setEditable(false);
        ta.setBackground(dashboardFrame.getBackground());
        ta.setForeground(Color.BLACK);
        Label loggedInLabel = new Label("Logged In Successfully!");
        loggedInLabel.setBounds(120, 30, 200, 30);
        Button logoutBtn = new Button("Logout");
        logoutBtn.setBounds(150, 200, 80, 30);

        dashboardFrame.add(loggedInLabel);
        dashboardFrame.add(logoutBtn);
        dashboardFrame.setSize(400, 250);
        dashboardFrame.setLayout(null);
        dashboardFrame.add(ta);

        // Frame 3(Incorrect password or Unknown User)
        Frame failedFrame = new Frame("Login Failed");
        Label FailedLabel = new Label("Login Failed! Incorrect Password or Unknown User");
        FailedLabel.setBounds(50, 100, 300, 30);
        Button retryBtn = new Button("Retry");
        retryBtn.setBounds(150, 150, 80, 30);

        failedFrame.add(FailedLabel);
        failedFrame.add(retryBtn);
        failedFrame.setSize(400, 250);
        failedFrame.setLayout(null);

        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String empty = "";
                if (empty.equals(tf1.getText()) || empty.equals(tf2.getText())) {
                    Dialog d = new Dialog(loginFrame, "Warning!!!", true);
                    d.setLayout(new FlowLayout());
                    Label msg = new Label("Fields can't be empty.");
                    Button okBtn = new Button("OK");

                    okBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            d.setVisible(false);
                            d.dispose();
                        }
                    });

                    d.add(msg);
                    d.add(okBtn);
                    d.setSize(200, 100);
                    d.setLocationRelativeTo(null); // center relative to parent
                    d.setVisible(true);

                } else if (defaultUser.equals(tf1.getText()) && defaultPassword.equals(tf2.getText())) {
                    // Hide login frame, show dashboard
                    loginFrame.setVisible(false);
                    failedFrame.setVisible(false);
                    dashboardFrame.setVisible(true);
                } else {
                    dashboardFrame.setVisible(false);
                    loginFrame.setVisible(false);
                    failedFrame.setVisible(true);
                }

                // Empty user and password field
                tf1.setText("");
                tf2.setText("");
            }
        });

        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide dashboard, show login frame
                dashboardFrame.setVisible(false);
                failedFrame.setVisible(false);
                loginFrame.setVisible(true);
            }
        });

        retryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // show failedFrame, hide rest
                dashboardFrame.setVisible(false);
                failedFrame.setVisible(false);
                loginFrame.setVisible(true);
            }
        });

        // Close both frames properly
        loginFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dashboardFrame.dispose();
                loginFrame.dispose();
                failedFrame.dispose();
            }
        });
        dashboardFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                loginFrame.dispose();
                failedFrame.dispose();
                dashboardFrame.dispose();
            }
        });
        failedFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                loginFrame.dispose();
                dashboardFrame.dispose();
                failedFrame.dispose();
            }

        });

    }

}
