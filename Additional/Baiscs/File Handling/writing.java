import java.io.FileWriter;
import java.io.IOException;

public class writing {
    public static void main(String[] args) {
        txtFile();
        csvFile();
    }

    private static void txtFile() {
        try {
            FileWriter writer = new FileWriter("DemoFiles/data.txt");
            writer.write("Hello user!\n");
            writer.write("Welcome to java File Handling");
            writer.close();
            System.out.println("File saved Successfully!");
        } catch (IOException e) {
            System.out.println("Error occured");
        }
    }

    private static void csvFile() {
        try {
            FileWriter writer = new FileWriter("DemoFiles/users.csv");
            writer.write("Username,password\n");
            writer.write("antan2rH,89Hdafl\n");
            writer.write("k8385a,hakal\n");
            writer.write("undirul,tandanton\n");
            writer.close();
            System.out.println("File saved Successfully!");
        } catch (IOException e) {
            System.out.println("Error occured");
        }

    }

}
