import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class reverseServer {
    public static void main(String[] args) {
        try {
            int port = 1005;
            LocateRegistry.createRegistry(port);
            reverseImple rev = new reverseImple();
            Naming.rebind("rmi://localhost:1005/reverseServer", rev);
            System.out.println("String reverse RMI service avaliable at port: " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
