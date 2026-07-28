import java.rmi.Naming;
import java.util.Scanner;

public class StringServiceClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Accessing remote method via RMI service");
            StringService ss = (StringService) Naming.lookup("rmi://localhost:1011/StringService");
            System.out.print("Input 1: ");
            String word1 = sc.nextLine();
            System.out.print("Input 2: ");
            String word2 = sc.nextLine();
            System.out.println("Result: " + ss.getResult(word1, word2));
        } catch (Exception e) {
            System.out.println("Client Error: " + e.getMessage());
        }
    }
}
