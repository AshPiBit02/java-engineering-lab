import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;

public class ComputerServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1011);
            ComputerImple compute = new ComputerImple();
            Naming.rebind("rmi://localhost:1011/ComputerService", compute);
            System.out.println("Computer RMI service started....");
            System.out.println("Bound as ComputerSerivce");
        } catch (Exception e) {
            System.out.println("Server Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
