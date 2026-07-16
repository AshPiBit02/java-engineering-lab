import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StringServiceImple extends UnicastRemoteObject implements StringService {
    protected StringServiceImple() throws RemoteException {
        super();
    }

    public String ToUpperCase(String s) throws RemoteException {
        System.out.println("Method ToUpperCase() called with " + s);
        return s.toUpperCase();
    }

    public String Reverse(String s) throws RemoteException {
        System.out.println("Method reverse() called with " + s);
        String rev = new StringBuilder(s).reverse().toString();
        return rev;
    }

}
