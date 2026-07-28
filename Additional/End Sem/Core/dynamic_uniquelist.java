import java.util.*;

public class dynamic_uniquelist {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Set<String> uniqueSet = new LinkedHashSet<>();
        System.out.println("Enter items (type 'exit' to stop: ");
        while (true) {
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            if (!uniqueSet.add(input)) {
                System.out.println("Duplicate ignored: " + input);
            } else {
                System.out.println("Added to set: " + input);
            }
        }

        List<String> uniqueList = new ArrayList<>(uniqueSet);
        System.out.println("\nFinal Unique list:");
        uniqueList.forEach(System.out::println);
    }
}
