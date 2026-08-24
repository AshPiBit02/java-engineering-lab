import java.rmi.Naming;
import java.util.Scanner;
import java.util.*;

public class PalindromeClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter string to be for plaindrome('end' to exit)");
        try {
            Palindrome plain = (Palindrome) Naming.lookup("rmi://localhost:1000/PalindromeServer");
            while (true) {
                System.out.print("Client: ");
                String request = sc.nextLine();
                if (request.equalsIgnoreCase("end")) {
                    return;
                }
                System.out.println("Server: " + plain.checkString(request));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
