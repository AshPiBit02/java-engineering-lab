import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PosNegImple extends UnicastRemoteObject implements PosNeg {
    protected PosNegImple() throws RemoteException {
        super();
    }

    public String checknum(int num) {
        if (num > 0) {
            return "Positive";
        } else if (num < 0) {
            return "Negative";
        } else {
            return "Zero";
        }
    }

}
