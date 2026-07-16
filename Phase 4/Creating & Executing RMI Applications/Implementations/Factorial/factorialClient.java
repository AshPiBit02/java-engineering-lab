import java.rmi.Naming;

public class factorialClient {
    public static void main(String[] args) {
        try {
            factorial facto = (factorial) Naming.lookup("rmi://localhost:1099/factorialService");
            int x = 5;
            System.out.println("Factorial of " + x + ": " + facto.fact(x));
        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}