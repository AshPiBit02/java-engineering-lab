import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        // JOptionPane -> pop up a standard dialog box that prompts users for a value
        // or informs then of something.
        // JOptionPane.showMessageDialog(null, "This is a standard Dialog of Swing",
        // "Plain Message",
        // JOptionPane.PLAIN_MESSAGE);
        // JOptionPane.showMessageDialog(null, "This is a standard Dialog of Swing",
        // "Information message",
        // JOptionPane.INFORMATION_MESSAGE);
        // JOptionPane.showMessageDialog(null, "Is this a standard Dialog of Swing?",
        // "Question message",
        // JOptionPane.QUESTION_MESSAGE);
        // JOptionPane.showMessageDialog(null, "This is a standard Dialog of Swing!!!",
        // "Warning message",
        // JOptionPane.WARNING_MESSAGE);
        // JOptionPane.showMessageDialog(null, "This is a standard Dialog of Swing XXX",
        // "Error message",
        // JOptionPane.ERROR_MESSAGE);

        // JOptionPane.showConfirmDialog(null, "Do you code in C++?", "Error message",
        // JOptionPane.YES_NO_CANCEL_OPTION);
        // System.out.println(JOptionPane.showConfirmDialog(null, "Do you code in C++?",
        // "Error message",
        // JOptionPane.YES_NO_CANCEL_OPTION));
        // this will print 1->No, 0->Yes, 2-> Cancel and -1->Cross

        // String name = JOptionPane.showInputDialog("What is your name?");
        // System.out.println("Hey! " + name);

        String[] responses = { "No, You are!", "No no , you are", "STFU" };
        ImageIcon icon = new ImageIcon("dumb.jpg");

        JOptionPane.showOptionDialog(null,
                "You are Dumb!", // message
                "secret message", // title
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                icon,
                responses,
                0);

    }
}
