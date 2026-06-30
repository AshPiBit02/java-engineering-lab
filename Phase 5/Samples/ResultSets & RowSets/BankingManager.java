import java.sql.PreparedStatement;
import java.sql.Statement;
import java.net.CacheRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import java.sql.DriverManager;

public class BankingManager {
    private static final String url = "jdbc:postgresql://localhost:5432/java_conn_state";
    private static final String username = "postgres";
    private static final String password = "2426";

    public static void main(String[] args) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            printTopandBottomAccounts(con);
            flagAndFreezeAccounts(con);
            generateOfflineReport(con);
            processTransfer(con);
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

    private static void generateOfflineReport(Connection con) throws SQLException {
        CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
        PreparedStatement pst = con.prepareStatement(
                "SELECT a.holder_name,a.account_type,a.balance,COALESCE(SUM(t.amount),0) AS total_transacted FROM accounts a LEFT JOIN transactions t ON a.id=t.account_id GROUP BY a.id,a.holder_name,a.account_type,a.balance");
        ResultSet rs = pst.executeQuery();
        crs.populate(rs); // holds all data independent of rs/con
        rs.close();
        pst.close();

        System.out.println();
        System.out.println("Offline Account Report");
        System.out.printf("%-12s %-15s %-10s %-15s%n", "Holder Name", "Account Type", "Balance", "Total Transacted");
        System.out.println("-".repeat(60));

        while (crs.next()) {
            System.out.printf("%-12s %-15s $%-10.2f $%-15.2f%n", crs.getString("holder_name"),
                    crs.getString("account_type"), crs.getDouble("balance"), crs.getDouble("total_transacted"));
        }

        crs.moveToInsertRow();
        crs.updateString("holder_name", "SYSTEM SUMMARY");
        crs.updateString("account_type", "N/A");
        crs.updateDouble("balance", 0);
        crs.updateDouble("total_transacted", 0);
        crs.insertRow();
        crs.moveToCurrentRow();

        crs.last();
        System.out.println("Total rows: " + crs.getRow());

    }

    private static void processTransfer(Connection con) throws SQLException {
        PreparedStatement debitPst = null;
        PreparedStatement creditPst = null;
        PreparedStatement debitBalance = null;
        PreparedStatement creditBalance = null;

        try {
            con.setAutoCommit(false);
            debitPst = con.prepareStatement(
                    "INSERT INTO transactions(account_id,type,amount,note) VALUES(?,'DEBIT',?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            debitPst.setInt(1, 10);
            debitPst.setDouble(2, 5000);
            debitPst.setString(3, "Transfer to account 1");
            debitPst.executeUpdate();

            ResultSet debitKeys = debitPst.getGeneratedKeys();
            {
                if (debitKeys.next()) {
                    System.out.println("Debit transaction ID: " + debitKeys.getInt(1));
                }
            }
            debitKeys.close();

            creditPst = con.prepareStatement(
                    "INSERT INTO transactions(account_id,type,amount,note)VALUES(?,'CREDIT',?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            creditPst.setInt(1, 1);
            creditPst.setDouble(2, 5000);
            creditPst.setString(3, "Transfer from account 10");
            creditPst.executeUpdate();

            ResultSet creditKeys = creditPst.getGeneratedKeys();
            if (creditKeys.next()) {
                System.out.println("Credit transaction ID: " + creditKeys.getInt(1));
            }
            creditKeys.close();

            debitBalance = con.prepareStatement("UPDATE accounts SET balance=balance-? WHERE id=?");
            debitBalance.setDouble(1, 5000);
            debitBalance.setInt(2, 10);
            debitBalance.executeUpdate();

            creditBalance = con.prepareStatement("UPDATE accounts SET balance=balance+? WHERE id=?");
            creditBalance.setDouble(1, 5000);
            creditBalance.setInt(2, 1);
            creditBalance.executeUpdate();

            con.commit();
            System.out.println("Amount transfer successfully.");
        } catch (SQLException e) {
            con.rollback();
            System.out.println("Transfer failed, rolled back: " + e.getMessage());
        } finally {
            con.setAutoCommit(true);
            if (debitPst != null)
                debitPst.close();
            if (creditPst != null)
                creditPst.close();
            if (debitBalance != null)
                debitBalance.close();
            if (creditBalance != null)
                creditBalance.close();
        }

    }

}
