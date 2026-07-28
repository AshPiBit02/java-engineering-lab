import java.sql.*;
import java.util.Scanner;

public class CountFetch {
    static Scanner sc = new Scanner(System.in);
    private static final String url = "jdbc:postgresql://localhost:5432/java_auth";
    private static final String username = "postgres";
    private static final String password = "xxxx";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) AS count FROM students WHERE program=?");
            System.out.print("Enter program to get student count: ");
            String program = sc.nextLine();
            pst.setString(1, program);
            ResultSet rs = pst.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("count");
            }
            System.out.println("No of students in " + program + ": " + count);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
