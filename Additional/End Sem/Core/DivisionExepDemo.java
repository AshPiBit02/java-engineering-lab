import java.util.InputMismatchException;
import java.util.Scanner;

public class DivisionExepDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Enter Number 1: ");
            int num1 = Integer.parseInt(sc.nextLine());
            System.out.print("Enter Number 2: ");
            int num2 = Integer.parseInt(sc.nextLine());
            double result = (double) num1 / num2;
            System.out.println("Result: " + result);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input!");
            System.out.println(e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero!");
            System.out.println(e.getMessage());
        }
    }
}