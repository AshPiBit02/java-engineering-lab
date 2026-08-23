import java.sql.*;
import java.util.Scanner;

public class CountFetch {
    static Scanner sc = new Scanner(System.in);
    private static final String url = "jdbc:postgresql://localhost:5432/java_crud";
    private static final String username = "postgres";
    private static final String password = "2426";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) AS count FROM students WHERE faculty=?");
            System.out.print("Enter faculty name to get student count: ");
            String faculty = sc.nextLine();
            pst.setString(1, faculty);
            ResultSet rs = pst.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("count");
            }
            System.out.println("No of students in " + faculty + ": " + count);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
