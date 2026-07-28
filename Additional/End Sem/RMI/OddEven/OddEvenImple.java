import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class OddEvenImple extends UnicastRemoteObject implements OddEven {
    protected OddEvenImple() throws RemoteException {
        super();
    }

    public String checkNum(int x) {
        if (x % 2 == 0) {
            return "Even";
        } else {
            return "Odd";
        }
    }

}
