import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SpecialCalcImple extends UnicastRemoteObject implements SpecialCalc {
    protected SpecialCalcImple() throws RemoteException {
        super();
    }

    public int square(int x) throws RemoteException {
        System.out.println("Method square() called with " + x);
        return x * x;
    }

    public int cube(int x) throws RemoteException {
        System.out.println("Method cube() called with " + x);
        return x * x * x;
    }

    public int mod(int x, int y) throws RemoteException {
        System.out.println("Method mod() called with " + x + ", " + y);
        return x * x;
    }

    public double sqroot(int x) throws RemoteException {
        System.out.println("Method sqroot() called with " + x);
        return Math.sqrt(x);
    }

}
