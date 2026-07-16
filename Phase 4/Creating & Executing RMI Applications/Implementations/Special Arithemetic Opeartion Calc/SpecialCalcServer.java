import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;
import java.rmi.registry.Registry;

public class SpecialCalcServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            SpecialCalcImple specialCalc = new SpecialCalcImple();

            Naming.rebind("rmi://localhost:1099/SpecialCalcService", specialCalc);
            System.out.println("Special Calculator RMI Server is Running.....");
            System.out.println("Bound as 'SpecialCalcService' on port 1099");
        } catch (Exception e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
