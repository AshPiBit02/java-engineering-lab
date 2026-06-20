import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    static ResultSet rs1, rs2;
    static PreparedStatement ps1, ps2;

    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/java_crud";
        String username = "postgres";
        String password = "2426";

        // Initail SELECT
        Connection con = DriverManager.getConnection(url, username, password);
        ps1 = con.prepareStatement("SELECT * FROM students");
        rs1 = ps1.executeQuery();
        System.out.println("Data before Update: ");
        display(rs1);

        // UPDATE
        ps2 = con.prepareStatement("UPDATE students SET faculty = 'Engineering' WHERE faculty=''");
        int rows = ps2.executeUpdate(); // returns number of rows affected
        System.out.println(rows + " rows updated.");

        ps1 = con.prepareStatement("SELECT * FROM students");
        rs1 = ps1.executeQuery();
        System.out.println("Data after update: ");
        display(rs1);

        con.close();

    }

    static void display(ResultSet rs) throws Exception {
        System.out.println("-".repeat(100));
        System.out.printf("%-5s %-10s %-20s %-23s %-10s %-10s%n", "ID", "Name", "Faculty", "Course", "Marks", "Grade");
        while (rs.next()) {
            System.out.printf("%-5d %-10s %-20s %-23s %-10d %-10s%n", rs.getInt("student_id"), rs.getString("name"),
                    rs.getString("faculty"), rs.getString("course"), rs.getInt("marks"), rs.getString("grade"));
        }
    }

}
