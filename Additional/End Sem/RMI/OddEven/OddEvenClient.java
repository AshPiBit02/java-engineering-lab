import java.util.Scanner;
import java.rmi.Naming;

public class OddEvenClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            OddEven oe = (OddEven) Naming.lookup("rmi://localhost:1010/OddEvenService");
            System.out.print("Enter an integer: ");
            int x = Integer.parseInt(sc.nextLine());
            System.out.println("Result: " + oe.checkNum(x));

        } catch (Exception e) {
            System.out.println("Client Error: " + e.getMessage());
        }
    }
}