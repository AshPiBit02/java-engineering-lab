import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PosNeg extends Remote {
    String checknum(int num) throws RemoteException;
}
