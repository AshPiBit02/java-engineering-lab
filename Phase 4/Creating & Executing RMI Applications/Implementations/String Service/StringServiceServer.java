import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class StringServiceServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1100);
            StringServiceImple stringImple = new StringServiceImple();
            Naming.bind("rmi://localhost:1100/StringServicer", stringImple);
            System.out.println("String Service RMI is Running...");
            System.out.println("Bound as StringServicer");

        } catch (Exception e) {
            System.out.println("Server Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
