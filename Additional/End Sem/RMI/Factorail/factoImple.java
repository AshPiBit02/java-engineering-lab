import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class factoImple extends UnicastRemoteObject implements facto {
    protected factoImple() throws RemoteException {
        super();
    }

    public int getFactorail(int x) {
        if (x == 0 || x == 1) {
            return 1;
        } else {
            return x * getFactorail(x - 1);
        }
    }

}
