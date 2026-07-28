import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OddEven extends Remote {
    String checkNum(int num) throws RemoteException;

}
