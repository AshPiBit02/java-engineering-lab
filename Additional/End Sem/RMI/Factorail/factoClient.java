import java.rmi.Naming;
import java.util.Scanner;

public class factoClient {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            facto fact = (facto) Naming.lookup("rmi://localhost:1003/factoServer");
            System.out.println("Enter integer and get the factorial");
            System.out.println("-".repeat(20));
            while (true) {
                System.out.print("Client: ");
                int x = Integer.parseInt(sc.nextLine());
                if (x < 0) {
                    System.out.println("Banned from service");
                    break;
                }
                System.out.println("Server: " + fact.getFactorail(x));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
