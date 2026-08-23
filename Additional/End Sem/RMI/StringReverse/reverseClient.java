import java.rmi.Naming;
import java.util.Scanner;

public class reverseClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            reverse rev = (reverse) Naming.lookup("rmi://localhost:1005/reverseServer");
            System.out.println("Connected to RMI server");
            System.out.println("-".repeat(50));
            while (true) {
                System.out.print("Client: ");
                String str = sc.nextLine();
                if (str.equalsIgnoreCase("end")) {
                    System.out.println("Disconnecting....");
                    return;
                }
                System.out.println("Server: " + rev.getReverse(str));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            // e.printStackTrace();
        }
    }

}
