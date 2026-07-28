import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class StringServiceImple extends UnicastRemoteObject implements StringService {
    protected StringServiceImple() throws RemoteException {
        super();
    }

    public String getResult(String word1, String word2) {
        StringBuilder sb1 = new StringBuilder(word1);
        StringBuilder sb2 = new StringBuilder(word2);

        // Compare first characters directly
        if (sb1.charAt(0) == 'A' && sb2.charAt(0) == 'A') {
            return word1.toUpperCase() + word2.toUpperCase();
        } else {
            return word1.toLowerCase() + word2.toLowerCase();
        }
    }
}
