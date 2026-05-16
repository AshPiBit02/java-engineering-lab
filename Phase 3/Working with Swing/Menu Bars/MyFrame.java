import javax.swing.JFrame;
import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.event.*;

public class MyFrame extends JFrame implements ActionListener {
    JMenuBar menuBar;

    JMenu fileMenu;
    JMenu editMenu;
    JMenu helpMenu;
    JMenu aboutMenu;

    JMenuItem loadItem;
    JMenuItem saveItem;
    JMenuItem exitItem;

    ImageIcon loadIcon;
    ImageIcon saveIcon;
    ImageIcon exitIcon;

    MyFrame() {
        this.setTitle("File Explorer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(new FlowLayout());

        loadIcon = new ImageIcon("load.jpg");
        saveIcon = new ImageIcon("save.jpg");
        exitIcon = new ImageIcon("exit.jpg");

        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        helpMenu = new JMenu("Help");
        aboutMenu = new JMenu("About");

        loadItem = new JMenuItem("Load");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        loadItem.setIcon(loadIcon);
        saveItem.setIcon(saveIcon);
        exitItem.setIcon(exitIcon);

        fileMenu.setMnemonic(KeyEvent.VK_F); // Alt + f for file menu
        editMenu.setMnemonic(KeyEvent.VK_E); // Alt + e for edit menu
        helpMenu.setMnemonic(KeyEvent.VK_H); // Alt + h for edit menu
        aboutMenu.setMnemonic(KeyEvent.VK_A); // Alt + a for edit menu

        loadItem.setMnemonic(KeyEvent.VK_L); // l for load
        saveItem.setMnemonic(KeyEvent.VK_S); // s for save
        exitItem.setMnemonic(KeyEvent.VK_E); // e for exit

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        menuBar.add(aboutMenu);

        this.setJMenuBar(menuBar);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadItem) {
            System.out.println("Loading Item...");
        } else if (e.getSource() == saveItem) {
            System.out.println("Item Saved!");
        } else {
            System.out.println("Exit!!!");
            System.exit(0);
        }

    }

}
