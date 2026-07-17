import java.rmi.Naming;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/CalcService");
            System.out.println("5 + 3 = " + calculator.add(5, 3));
            System.out.println("5 - 3 = " + calculator.sub(5, 3));
            System.out.println("5 * 3 = " + calculator.mul(5, 3));
            System.out.println("5 / 3 = " + calculator.div(5, 3));
        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}