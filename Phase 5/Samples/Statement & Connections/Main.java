import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/java_conn_state";
        String username = "postgres";
        String password = "2426";
        String query1 = "SELECT * FROM employees";
        String query2 = "SELECT * FROM employees WHERE department='Engineering'";
        String query3 = "SELECT * FROM employees WHERE department=?";
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            if (con.isValid(2)) {
                displayMetadata(con);
            }
            try (Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(query2);
                System.out.println("-".repeat(30) + " Statement " + "-".repeat(30) + "\n");
                displayHeader();
                displayRows(rs);
            }
            try (PreparedStatement pst = con.prepareStatement(query3)) {
                System.out.println("\n\n" + "-".repeat(27) + " Prepared Statement " + "-".repeat(27) + "\n");

                System.out.println("Engineering Department");
                pst.setString(1, "Engineering");
                displayHeader();
                displayRows(pst.executeQuery());

                System.out.println("\nHR Department");
                pst.setString(1, "HR");
                displayHeader();
                displayRows(pst.executeQuery());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayHeader() {
        System.out.printf("%-5s %-20s %-15s %-10s %-10s%n", "ID", "Name", "Department", "Salary", "Joined Date");
        System.out.println("-".repeat(70));
    }

    public static void displayRows(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.printf("%-5d %-20s %-15s $%-10.2f %-10s%n", rs.getInt("id"), rs.getString("name"),
                    rs.getString("department"), rs.getFloat("salary"), rs.getDate("joined_date"));
        }

    }

    public static void displayMetadata(Connection con) throws SQLException {
        System.out.println("Connection successful");
        DatabaseMetaData meta = con.getMetaData();
        System.out.println("DB: " + meta.getDatabaseProductName());
        System.out.println("Version: " + meta.getDatabaseProductVersion());
        System.out.println("Driver: " + meta.getDriverName());
    }

}