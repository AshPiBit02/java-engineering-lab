import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class factorialImple extends UnicastRemoteObject implements factorial {
    protected factorialImple() throws RemoteException {
        super();
    }

    public int fact(int x) throws RemoteException {
        System.out.println("Method fact() called with x: " + x);
        int res = 1;
        while (x != 1) {
            res *= x;
            x--;
        }
        return res;
    }

}
