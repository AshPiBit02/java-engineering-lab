import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/java_conn_state";
        String username = "postgres";
        String password = "2426";
        try (Connection con = DriverManager.getConnection(url, username, password);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM employees")) {

            if (con.isValid(2)) {
                System.out.println("Connection successful");
                DatabaseMetaData meta = con.getMetaData();
                System.out.println("DB: " + meta.getDatabaseProductName());
                System.out.println("Version: " + meta.getDatabaseProductVersion());
                System.out.println("Driver: " + meta.getDriverName());
            }
            System.out.printf("%-5s %-20s %-15s %-10s %-10s%n", "ID", "Name", "Department", "Salary", "Joined Date");
            System.out.println("-".repeat(70));
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-15s $%-10.2f %-10s%n", rs.getInt("id"), rs.getString("name"),
                        rs.getString("department"), rs.getFloat("salary"), rs.getDate("joined_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}