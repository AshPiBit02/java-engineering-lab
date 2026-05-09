import java.util.*;

public class ListType {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("Name A");
        names.add("Name B");
        names.add("Name C");

        List<String> moreNames = new LinkedList<>();
        moreNames.add("Name D");
        moreNames.add("Name E");

        printList(names);
        printList(moreNames);
    }

    static void printList(List<String> list) {
        for (String item : list) {
            System.out.println(item);
        }
    }

}
