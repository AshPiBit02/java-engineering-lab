import java.rmi.Naming;

public class ComputerClient {
    public static void main(String[] args) {
        try {
            Computer compute = (Computer) Naming.lookup("rmi://localhost:1011/ComputerService");
            System.out.println("Result: " + compute.getResult(2, 2));
        } catch (Exception e) {
            System.out.println("Client Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
