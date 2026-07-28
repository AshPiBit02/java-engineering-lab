import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Computer extends Remote {
    int getResult(int x, int y) throws RemoteException;

}
