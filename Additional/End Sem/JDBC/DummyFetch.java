import java.sql.*;

public class DummyFetch {
    private static final String url = "jdbc:postgresql://localhost:5432/java_crud";
    private static final String username = "postgres";
    private static final String password = "XXXX";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM employees WHERE salary>?");
            pst.setFloat(1, 125000);
            ResultSet rs = pst.executeQuery();
            System.out.printf("%-5s   %-10s   %-10s  %-10s", "ID", "Name", "Department", "Salary");
            System.out.println("-".repeat(50));
            while (rs.next()) {
                System.out.printf("%-5d   %-10s   %-10s  %-10.2f", rs.getInt("id"), rs.getString("name"),
                        rs.getString("department"), rs.getFloat("salary"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
