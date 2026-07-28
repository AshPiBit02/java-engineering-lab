import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;

public class OddEvenServer {
    public static void main(String[] args) {
        try {
            int port = 1011;
            LocateRegistry.createRegistry(port);
            OddEvenImple oddeven = new OddEvenImple();
            Naming.rebind("rmi://localhost:1010/OddEvenService", oddeven);
            System.out.println("RMI Service provided to port " + port);
        } catch (Exception e) {
            System.out.println("Server Error: " + e.getMessage());
        }
    }
}
