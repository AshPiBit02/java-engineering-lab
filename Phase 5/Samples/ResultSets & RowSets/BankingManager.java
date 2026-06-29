import java.sql.PreparedStatement;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

public class BankingManager {
    private static final String url = "jdbc:postgresql://localhost:5432/java_conn_state";
    private static final String username = "postgres";
    private static final String password = "2426";

    public static void main(String[] args) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            printTopandBottomAccounts(con);
        }
    }

    private static void printTopandBottomAccounts(Connection con) throws SQLException {
        PreparedStatement pst = con.prepareStatement("SELECT * FROM accounts WHERE is_active ORDER BY balance ASC",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = pst.executeQuery();

        rs.afterLast();
        int count = 0;
        System.out.printf("%-12s %-15s %-10s%n", "Holder Name", "Account Type", "Balance");
        System.out.println("-".repeat(40));
        while (rs.previous() && count < 3) {
            System.out.printf("%-12s %-15s $%-10.2f%n", rs.getString("holder_name"), rs.getString("account_type"),
                    rs.getFloat("balance"));
            count++;
        }

    }

}
