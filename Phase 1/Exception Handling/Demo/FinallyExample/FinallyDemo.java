import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class FinallyDemo {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);
        try {
            System.out.println("Enter an integer: ");
            int num = sc.nextInt();
            list.add(num);

            System.out.println("Enter index to access integer: ");
            int idx = sc.nextInt();
            System.out.println("Element at index " + idx + " is " + list.get(idx));
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input format!");
            sc.nextLine(); // clear invalid input
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Index out of Bound!");
        } finally {
            System.out.println("Program Terminated Successfully");
        }

    }

}
