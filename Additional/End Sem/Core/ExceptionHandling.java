import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionHandling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Enter dividend: ");
            float x = Float.parseFloat(sc.nextLine());
            System.out.print("Enter divisor: ");
            float y = Float.parseFloat(sc.nextLine());
            if (y == 0) {
                throw new Exception("Cannot divide by zero!");
            }
            System.out.println("Result: " + x / y);
        } catch (NumberFormatException e) {
            System.out.println("Input must be valid number!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
            System.out.println("Program Terminated!");
        }

    }
}
