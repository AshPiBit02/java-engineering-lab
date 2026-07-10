import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/java_lulli";
    private static final String USER = "postgres";
    private static final String PASSWORD = "2426";

    // Prevent instantiation - this is a utility class
    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Quick standalone test - run this file directly to verify connectivity
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM lulli");

            if (conn != null) {
                System.out.println("DBConnection: Connected successfully!");
            }
            while (rs.next()) {
                System.out.printf("%-10d %-10s", rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("DBConnection: Connection failed!");
            e.printStackTrace();
        }
    }
}