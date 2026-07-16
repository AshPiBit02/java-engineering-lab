import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StringService extends Remote {
    String ToUpperCase(String s) throws RemoteException;
    String Reverse(String s) throws RemoteException;
}
