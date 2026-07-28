import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;

public class StringServiceServer {
    public static void main(String[] args){
        try{
            int port=1011;
            LocateRegistry.createRegistry(port);
            StringServiceImple ss=new StringServiceImple();
            Naming.rebind("rmi://localhost:1011/StringService",ss);
            System.out.println("RMI service available at port "+port);
        }catch(Exception e){
            System.out.println("Server Error: "+e.getMessage());
        }
    }
}



