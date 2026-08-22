import java.util.*;

public class exceptionTwo {
    public static void main(String[] args) {
        List<Integer> number = new ArrayList<>();
        number.add(10);
        number.add(30);
        number.add(50);
        number.add(60);
        try {
            System.out.println(number.get(4));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of Bound!");
        } finally {
            System.out.println("Program closed!");
        }
    }
}
