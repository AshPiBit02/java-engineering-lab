import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAndTooltipDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu and Tooltip Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");

        // Menu items
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Adding menu items to the file menu
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Adding the file menu to the menu bar
        menuBar.add(fileMenu);

        // Setting the menu bar for the frame
        frame.setJMenuBar(menuBar);

        // Tooltips
        JButton button = new JButton("Click Me");
        button.setToolTipText("This is a button");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Button clicked!");
            }
        });

        // Adding components to the frame
        frame.getContentPane().add(button);

        // Display the frame
        frame.setVisible(true);
    }
}
