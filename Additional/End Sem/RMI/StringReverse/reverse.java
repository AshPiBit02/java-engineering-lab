import java.rmi.Remote;
import java.rmi.RemoteException;

public interface reverse extends Remote {
    String getReverse(String str) throws RemoteException;
}
