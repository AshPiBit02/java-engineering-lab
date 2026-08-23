import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class reverseImple extends UnicastRemoteObject implements reverse {
    protected reverseImple() throws RemoteException {
        super();
    }

    public String getReverse(String str) {
        String result = new StringBuilder(str.strip()).reverse().toString();
        return result;
    }

}
