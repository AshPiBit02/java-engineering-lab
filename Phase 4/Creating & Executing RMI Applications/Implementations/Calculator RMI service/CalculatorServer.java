import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            CalculatorImple calculator = new CalculatorImple();

            Naming.rebind("rmi://localhost:1099/CalcService", calculator);
            System.out.println("Calculator RMI Server is Running....");
            System.out.println("Bound as 'CalcService' on port 1099");

        } catch (Exception e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
