import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class LoginSecurity {
    private static final String url = "jdbc:postgresql://localhost:5432/java_conn_state";
    private static final String username = "postgres";
    private static final String password = "2426";

    public static void main(String[] args) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, username, password)) {

            System.out.println("-------- Normal Login----------");
            vunerableLogin(con, "jdoe", "pass456");

            System.out.println();
            System.out.println("-------- Injection attempt --------");
            vunerableLogin(con, "admin' --", "dummy_password");

            System.out.println();
            System.out.println();
            System.out.println("-------- SafePlay via PreparedStatement-----------");

            System.out.println("(Normal credentails)");
            secureLogin(con,"jdoe","pass456");
            System.out.println("(Injection Payload)");
            secureLogin(con,"admin' --","anything");


        }

    }

    private static void vunerableLogin(Connection con, String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        System.out.println("Executing query: " + sql);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        if (rs.next()) {
            System.out
                    .println("Login SUCCESS for : " + rs.getString("username") + " |   Role: " + rs.getString("role"));
        } else {
            System.out.println("Login FAILED!");
        }
        rs.close();
        st.close();
    }

    private static void secureLogin(Connection con, String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, username);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            System.out
                    .println("Login SUCCESS for : " + rs.getString("username") + " |   Role: " + rs.getString("role"));
        } else {
            System.out.println("Login Failed!");
        }
        rs.close();
        pst.close();
    }

}
