import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class insertion {
    static PreparedStatement pst;

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgres://localhost:5432/java_auth";
        String username = "postgres";
        String password = "2426";
        String sql = "INSERT INTO user_data VALUES(?,?,?,?)";

        Connection conn = DriverManager.getConnection(url, username, password);
        pst = conn.prepareStatement(sql);
        int more_data=1;
        do{
            System.out.print("UserId: ");
            System.out.print("Username: ");
            System.out.print("Password: ");
            System.out.print("Email: ");
            
        }

    }

    static void insertData(int userId, String username, String password, String email) throws SQLException {
        pst.setInt(1, userId);
        pst.setString(2, username);
        pst.setString(3, password);
        pst.setString(4, email);
        pst.executeUpdate();
        System.out.println(username + "'s Data inserted.");

    }

}
