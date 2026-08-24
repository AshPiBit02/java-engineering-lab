import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PalindromeImple extends UnicastRemoteObject implements Palindrome {
    protected PalindromeImple() throws RemoteException {
        super();
    }

    public Boolean checkString(String str) {
        String rev = new StringBuilder(str).reverse().toString();
        if (str.equals(rev)) {
            return true;
        }
        return false;
    }
}
