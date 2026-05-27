import java.awt.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Signup extends Login {
    JLabel fullName;
    JTextField fname;
    JLabel signUser;
    JTextField signUsername;
    JLabel emailAdd;
    JTextField email;
    JLabel signPassLbl;
    JPasswordField signpass;
    JLabel signCpassLbl;
    JPasswordField signCPass;

    JDialog signDialog;
    JLabel errorDialog = new JLabel(
            "<html><div style='text-align: center;'>Error!</div></html>");

    JDialog signedDialog;
    JLabel signedMessage = new JLabel(
            "<html><div style='text-align: center;'>Signed in Successsfully<br>Welcome to the System</div></html>");

    JButton showpass;
    JButton showCpass;
    JButton signup;

    Signup() {

        fullName = new JLabel("Full Name");
        fullName.setBounds(50, 100, 100, 30);
        fullName.setFont(new Font("Arial", Font.PLAIN, 20));
        fullName.setLayout(null);
        fullName.setForeground(Color.WHITE);
        fullName.setVisible(true);

        fname = new JTextField();
        fname.setBounds(70, 135, 260, 25);
        fname.setLayout(new FlowLayout());
        fname.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        fname.setForeground(Color.BLACK);

        signUser = new JLabel("Username");
        signUser.setBounds(50, 185, 100, 30);
        signUser.setFont(new Font("Arial", Font.PLAIN, 20));
        signUser.setLayout(null);
        signUser.setForeground(Color.WHITE);
        signUser.setVisible(true);

        signUsername = new JTextField();
        signUsername.setBounds(70, 220, 260, 25);
        signUsername.setLayout(new FlowLayout());
        signUsername.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        signUsername.setForeground(Color.BLACK);

        emailAdd = new JLabel("Email Address");
        emailAdd.setBounds(50, 270, 150, 30);
        emailAdd.setFont(new Font("Arial", Font.PLAIN, 20));
        emailAdd.setLayout(null);
        emailAdd.setForeground(Color.WHITE);
        emailAdd.setVisible(true);

        email = new JTextField();
        email.setBounds(70, 305, 260, 25);
        email.setLayout(new FlowLayout());
        email.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        email.setForeground(Color.BLACK);

        signPassLbl = new JLabel("Password");
        signPassLbl.setBounds(50, 360, 150, 30);
        signPassLbl.setFont(new Font("Arial", Font.PLAIN, 20));
        signPassLbl.setLayout(null);
        signPassLbl.setForeground(Color.WHITE);
        signPassLbl.setVisible(true);

        signpass = new JPasswordField();
        signpass.setBounds(70, 395, 260, 25);
        signpass.setForeground(Color.BLACK);

        showpass = createShowPassBtn(signpass, false, "signPassword");
        signpass.add(showpass);

        signCpassLbl = new JLabel("Confirm Password");
        signCpassLbl.setBounds(50, 445, 200, 30);
        signCpassLbl.setFont(new Font("Arial", Font.PLAIN, 20));
        signCpassLbl.setLayout(null);
        signCpassLbl.setForeground(Color.WHITE);
        signCpassLbl.setVisible(true);

        signCPass = new JPasswordField();
        signCPass.setBounds(70, 480, 260, 25);
        signCPass.setForeground(Color.BLACK);

        showCpass = createShowPassBtn(signCPass, false, "signConfirmPassword");
        signCPass.add(showCpass);

        // Dialogs for signup panel
        signDialog = new JDialog();
        signDialog.setBounds(550, 20, 250, 100);
        signDialog.getContentPane().setBackground(Color.decode("#ffffff"));
        signDialog.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        errorDialog.setFont(new Font("Arial", Font.PLAIN, 18));
        errorDialog.setForeground(Color.BLACK);
        signDialog.add(errorDialog);

        signup = new JButton("Sign up") {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        signup.setBounds(65, 580, 270, 40);
        signup.setFont(new Font("Arial", Font.PLAIN, 18));
        signup.setBackground(Color.decode("#09b422"));
        signup.setForeground(Color.WHITE);
        signup.setOpaque(false);
        signup.setContentAreaFilled(false);
        signup.setFocusable(false);
        signup.setFocusPainted(false);
        signup.setBorderPainted(false);

        signup.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                signup.setBackground(Color.decode("#00510c"));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                signup.setBackground(Color.decode("#09b422"));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                signup.setBackground(Color.decode("#00ff26"));
                if (fname.getText().isEmpty() || signUsername.getText().isEmpty() || email.getText().isEmpty()
                        || signPassword.isEmpty() || signConfirmPassword.isEmpty()) {
                    signDialog.setTitle("Empty Field Error");
                    signDialogfunc(signDialog);
                } else if (!signPassword.equals(signConfirmPassword)) {
                    signDialog.setTitle("Password Match Error!!!");
                    signDialogfunc(signDialog);
                } else { // Register
                    new RegisterFile(fname.getText(), signUser.getText(), email.getText(), signPassword, // Registers
                                                                                                         // valid User
                            signConfirmPassword);
                    signDialogfunc(signedDialog);
                    System.out.println("Registered");
                }

            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                signup.setBackground(Color.decode("#09b422"));
            }
        });

        signedDialog = new JDialog();
        signedDialog.setTitle("Signed In ✅");
        signedDialog.setBounds(550, 20, 250, 100);
        signedDialog.getContentPane().setBackground(Color.decode("#ffffff"));
        signedMessage.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        signedMessage.setFont(new Font("Arial", Font.PLAIN, 18));
        signedMessage.setForeground(Color.BLACK);
        signDialog.add(signedMessage);

        bearLabel.setVisible(false);
        logPanel.setVisible(false);
        signPanel.setVisible(true);

        signPanel.add(fname);
        signPanel.add(fullName);
        signPanel.add(signUser);
        signPanel.add(signUsername);
        signPanel.add(emailAdd);
        signPanel.add(email);
        signPanel.add(signPassLbl);
        signPanel.add(signpass);
        signPanel.add(signCpassLbl);
        signPanel.add(signCPass);
        signPanel.add(signup);

        signPanel.revalidate();
        signPanel.repaint();

    }

    private void signDialogfunc(JDialog dial) {
        dial.setVisible(true);
        Timer time = new Timer(1000, e -> {
            dial.setVisible(false);
            if (dial == signedDialog) { // Registration success open login panel
                dispose();
                new Login();
            }
        });
        time.setRepeats(false);
        time.start();
    }

}
