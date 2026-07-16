import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImple extends UnicastRemoteObject implements Calculator {
    protected CalculatorImple() throws RemoteException {
        super();
    }

    public int add(int a, int b) throws RemoteException {
        System.out.println("Method add() called with " + a + "," + b);
        return a + b;
    }

    public int sub(int a, int b) throws RemoteException {
        System.out.println("Method sub() called with " + a + ", " + b);
        return a - b;
    }

    public int mul(int a, int b) throws RemoteException {
        System.out.println("Method mul() called with " + a + "," + b);
        return a * b;
    }

    public double div(int a, int b) throws RemoteException {
        System.out.println("Method div() called with " + a + "," + b);
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero!");
        }
        return (double) (a / b);
    }

}
