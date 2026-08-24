import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Palindrome extends Remote {
    Boolean checkString(String str) throws RemoteException;
}
