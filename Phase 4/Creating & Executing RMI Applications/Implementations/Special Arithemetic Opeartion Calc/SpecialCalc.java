import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SpecialCalc extends Remote {
    int square(int x) throws RemoteException;

    int cube(int x) throws RemoteException;

    int mod(int x, int y) throws RemoteException;

    double sqroot(int x) throws RemoteException;
}
