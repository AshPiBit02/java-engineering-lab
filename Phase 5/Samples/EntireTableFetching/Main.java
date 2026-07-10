import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private static final String url = "jdbc:postgresql://localhost:5432/explore_psql";
    private static final String username = "postgres";
    private static final String password = "2426";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            Statement st = con.createStatement();
            String sql = "SELECT * FROM inventory WHERE warranty<2024 ORDER BY type";
            ResultSet rs = st.executeQuery(sql);

            System.out.printf("%-10s %-20s %-15s %-12s %10s%n", "ID", "Product Name", "Type", "Location", "Warranty");
            System.out.println("-".repeat(75));
            while (rs.next()) {
                System.out.printf("%-10d %-20s %-15s %-15s %-10d%n", rs.getInt("id"), rs.getString("name"),
                        rs.getString("type"), rs.getString("place"), rs.getInt("warranty"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
