import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class factorialServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            factorialImple factImple = new factorialImple();
            Naming.rebind("rmi://localhost:1099/factorialService", factImple);
            System.out.println("Factorial Calculator RMI Server is Runing.....");
            System.out.println("Bound as factorialService");
        } catch (Exception e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
