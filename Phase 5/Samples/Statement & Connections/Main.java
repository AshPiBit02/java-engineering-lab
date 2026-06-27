import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/java_conn_state";
        String username = "postgres";
        String password = "2426";

        Connection con = DriverManager.getConnection(url, username, password);
        if (con.isValid(2)) {
            System.out.println("Connection successful");
            DatabaseMetaData meta = con.getMetaData();
            System.out.println("DB: " + meta.getDatabaseProductName());
            System.out.println("Version: " + meta.getDatabaseProductVersion());
            System.out.println("Driver: " + meta.getDriverName());
        }
        con.close();
        System.out.println(con.isClosed());
    }

}
