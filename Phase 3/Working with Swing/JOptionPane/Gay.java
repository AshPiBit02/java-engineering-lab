import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

public class Gay {
    public static void main(String[] args) throws InterruptedException {
        // String[] responses = { "No, You are", "No no , you are", "STFU" };
        ImageIcon icon = new ImageIcon("dumb.jpg");
        String[] answer = { "Sorry", "No Comment" };
        String[] answer2 = { "Yes, I am.", "May be, I don't know." };
        int response = JOptionPane.showOptionDialog(null,
                "Are you Gay?",
                "LGBTQ+",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon,
                null,
                0);
        if (response == 0) {
            int response1 = JOptionPane.showOptionDialog(null,
                    "Respond....", // message
                    "WHY", // title
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    answer,
                    0);
            if (response1 == 0) {
                System.out.println("Don't Be.");
            } else {
                System.out.println("Gay!!!!");
            }
        } else if (response == 1) {
            int response2 = JOptionPane.showOptionDialog(null,
                    "You sure?", // message
                    "Really?", // title
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    answer2,
                    0);
            if (response2 == 1) {
                System.out.println("Nice. Gay!");
            } else {
                System.out.println("Nice!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "You are 101% Gay.", "Gay", JOptionPane.PLAIN_MESSAGE);
            int g = 10;
            int y = 1;
            while (g > 0) {
                System.out.print("\rGa" + "y".repeat(y) + "!"); // '\r' overwrites the sop statement
                Thread.sleep(1000);
                g--;
                y++;
            }
            System.out.print("\nGayyyyyyyyyyyyyyyyyy................");
        }
    }
}
