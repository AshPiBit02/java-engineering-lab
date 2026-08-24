import java.rmi.Naming;
import java.util.concurrent.ExecutionException;

public class host {
    public static void main(String[] args) {
        String input = "change this to uppercase";
        String input2 = "kaggle";
        try {
            StringService stringserv = (StringService) Naming.lookup("rmi://localhost:1100/StringServicer");
            System.out.println("Upper case of " + input + " = " + stringserv.ToUpperCase(input));
            System.out.println("Reverse of string " + input2 + " = " + stringserv.Reverse(input2));
            System.out.println("Count of vowel in " + input2 + " = " + stringserv.vowelCount(input2));
        } catch (Exception e) {
            System.out.println("Client Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
