import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class PalindromeServer {
    public static void main(String[] args) {
        try {
            int port = 1000;
            LocateRegistry.createRegistry(port);
            PalindromeImple palin = new PalindromeImple();
            Naming.rebind("rmi://localhost:1000/PalindromeServer", palin);
            System.out.println("RMI Service started at port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
