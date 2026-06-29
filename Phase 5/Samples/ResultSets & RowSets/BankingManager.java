import java.sql.PreparedStatement;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BooleanSupplier;
import java.sql.DriverManager;

public class BankingManager {
    private static final String url = "jdbc:postgresql://localhost:5432/java_conn_state";
    private static final String username = "postgres";
    private static final String password = "2426";

    public static void main(String[] args) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            printTopandBottomAccounts(con);
            flagAndFreezeAccounts(con);
        }
    }

    private static void printTopandBottomAccounts(Connection con) throws SQLException {
        PreparedStatement pst = con.prepareStatement("SELECT * FROM accounts WHERE is_active ORDER BY balance ASC",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = pst.executeQuery();

        System.out.println();
        System.out.println("(Top 3 Richest Balance Accounts)");
        displayTopAccounts(rs, true);

        System.out.println();
        System.out.println("(Bottom 3 Lowest Balance Accounts)");
        displayTopAccounts(rs, false);

    }

    private static void displayTopAccounts(ResultSet rs, Boolean top) throws SQLException {
        int count = 0;
        System.out.printf("%-12s %-15s %-10s%n", "Holder Name", "Account Type", "Balance");
        System.out.println("-".repeat(40));
        if (top) {
            rs.afterLast();
            while (rs.previous() && count < 3) {
                System.out.printf("%-12s %-15s $%-10.2f%n", rs.getString("holder_name"), rs.getString("account_type"),
                        rs.getFloat("balance"));
                count++;
            }
        } else {
            rs.beforeFirst();
            while (rs.next() && count < 3) {
                System.out.printf("%-12s %-15s $%-10.2f%n", rs.getString("holder_name"), rs.getString("account_type"),
                        rs.getFloat("balance"));
                count++;
            }
        }
    }

    private static void flagAndFreezeAccounts(Connection con) throws SQLException {
        PreparedStatement pst = con.prepareStatement(
                "SELECT * FROM accounts WHERE balance < 1000.00 AND is_active=TRUE", ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = pst.executeQuery();
        System.out.println();
        System.out.println("Disable Frozen Accounts");
        System.out.println("-".repeat(40));
        while (rs.next()) {
            rs.updateBoolean("is_active", false);
            rs.updateRow();
            System.out.printf("Frozen: [%s] | Balance: [$%.2f]%n", rs.getString("holder_name"), rs.getFloat("balance"));
        }

        rs.beforeFirst();
        System.out.println();
        System.out.println("Frozen Accounts After Update");
        System.out.println("-".repeat(40));
        System.out.printf("%-12s %-15s %-10s %-10s%n", "Account Holder", "Account Type", "Balance", "is_active");
        while (rs.next()) {
            System.out.printf("%-12s %-15s $%-10.2f %-10s%n", rs.getString("holder_name"), rs.getString("account_type"),
                    rs.getFloat("balance"), rs.getBoolean("is_active"));
        }

    }

}
