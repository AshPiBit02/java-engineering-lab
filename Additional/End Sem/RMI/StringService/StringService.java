import java.rmi.RemoteException;
import java.rmi.Remote;

public interface StringService extends Remote {
    String getResult(String word1, String word2) throws RemoteException;
}
