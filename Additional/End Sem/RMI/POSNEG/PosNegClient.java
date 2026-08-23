import java.rmi.Naming;
import java.util.Scanner;

public class PosNegClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            PosNeg ps = (PosNeg) Naming.lookup("rmi://localhost:1001/PosNegServer");
            while (true) {
                System.out.print("Enter a number to check if it is -ve or +ve: ");
                int num = Integer.parseInt(sc.nextLine());
                if (num == -1) {
                    break;
                }
                System.out.println("Result: " + ps.checknum(num));

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Disconnected from RMI server!");
    }

}
