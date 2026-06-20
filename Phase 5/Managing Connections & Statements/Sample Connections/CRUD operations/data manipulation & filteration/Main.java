import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/java_crud";
        String username = "postgres";
        String password = "2426";

        Connection con = DriverManager.getConnection(url, username, password);
        PreparedStatement ps1 = con.prepareStatement("SELECT * FROM students");
        ResultSet rs1 = ps1.executeQuery();

        System.out.println("Data from java_crud/students: ");
        System.out.println("-".repeat(100));
        System.out.printf("%-5s %-10s %-20s %-23s %-10s %-10s%n", "ID", "Name", "Faculty", "Course", "Marks", "Grade");
        while (rs1.next()) {
            System.out.printf("%-5d %-10s %-20s %-23s %-10d %-10s%n", rs1.getInt("student_id"), rs1.getString("name"),
                    rs1.getString("faculty"), rs1.getString("course"), rs1.getInt("marks"), rs1.getString("grade"));

        }

        // Manipult
        con.close();

    }

}
