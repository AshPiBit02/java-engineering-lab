import java.rmi.Remote;
import java.rmi.RemoteException;
public interface facto extends Remote{
    int getFactorail(int x) throws RemoteException;
}