import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/java_conn_state";
        String username = "postgres";
        String password = "2426";

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter SELECT query: ");
        String query1 = sc.nextLine();

        try (Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement pst = con.prepareStatement(query1);
                ResultSet rs = pst.executeQuery()) {

            ResultSetMetaData meta = rs.getMetaData();

            int colCount = meta.getColumnCount();
            showHeader(meta, colCount);

            showRows(rs, colCount);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void showHeader(ResultSetMetaData meta, int count) throws SQLException {
        for (int i = 1; i <= count; i++) {
            System.out.printf("%-25s", meta.getColumnName(i));
        }
        System.out.println();
        System.out.println("-".repeat(25 * count));
    }

    public static void showRows(ResultSet rs, int count) throws SQLException {
        while (rs.next()) {
            for (int i = 1; i <= count; i++) {
                System.out.printf("%-25s", rs.getString(i));
            }
            System.out.println();
        }

    }

}
