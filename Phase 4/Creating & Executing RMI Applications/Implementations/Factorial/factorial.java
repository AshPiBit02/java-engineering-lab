import java.rmi.Remote;
import java.rmi.RemoteException;

public interface factorial extends Remote{
    int fact(int x) throws RemoteException;
}
