import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;

public class PosNegServer {
    public static void main(String[] args) {
        try {
            int port = 1001;
            LocateRegistry.createRegistry(port);
            PosNegImple pn = new PosNegImple();
            Naming.rebind("rmi://localhost:1001/PosNegServer", pn);
            System.out.println("RMI Service porvided at port " + port);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
