import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class factoServer {
    public static void main(String[] args) {
        try {
            int port = 1003;
            LocateRegistry.createRegistry(port);
            factoImple fact = new factoImple();
            Naming.rebind("rmi://localhost:1003/factoServer", fact);
            System.out.println("RMI factorial service started at port " + port);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
