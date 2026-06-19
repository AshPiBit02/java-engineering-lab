import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/explore_psql";
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
            if (conn != null) {
                System.out.println("DBConnection: Connected successfully!");
            }
        } catch (SQLException e) {
            System.out.println("DBConnection: Connection failed!");
            e.printStackTrace();
        }
    }
}