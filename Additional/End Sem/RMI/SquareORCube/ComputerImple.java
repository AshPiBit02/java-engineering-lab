import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ComputerImple extends UnicastRemoteObject implements Computer {
    protected ComputerImple() throws RemoteException {
        super();
    }

    public int getResult(int x, int y) throws RemoteException {
        if (x % 2 == 0 && y % 2 == 0) {
            return (x + y) * (x + y);
        } else {
            return (x + y) * (x + y) * (x + y);
        }
    }

}
