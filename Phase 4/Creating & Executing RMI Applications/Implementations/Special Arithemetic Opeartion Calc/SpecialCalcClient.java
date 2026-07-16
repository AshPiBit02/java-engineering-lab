import java.rmi.Naming;

public class SpecialCalcClient {
    public static void main(String[] args) {
        try {
            SpecialCalc speCal = (SpecialCalc) Naming.lookup("rmi://localhost:1099/SpecialCalcService");
            int x = 5;
            int y = 4;
            System.out.println("Square of " + x + " = " + speCal.square(x));
            System.out.println("Cube of " + x + " = " + speCal.cube(x));
            System.out.println("Mod of " + x + " with " + y + " = " + speCal.mod(x, y));
            System.out.println("Square Root of " + x + " = " + speCal.sqroot(x));
        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
