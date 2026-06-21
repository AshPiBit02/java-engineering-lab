import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class insertion {
    static PreparedStatement pst;

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/java_auth";
        String username = "postgres";
        String password = "2426";
        String sql = "INSERT INTO user_data VALUES(?,?,?,?)";

        Scanner sc = new Scanner(System.in);

        Connection conn = DriverManager.getConnection(url, username, password);
        pst = conn.prepareStatement(sql);
        int more_data = 1;
        do {
            System.out.print("UserId: ");
            int id = sc.nextInt();

            System.out.print("Username: ");
            String uname = sc.nextLine();

            System.out.print("Password: ");
            String upass = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            insertData(id, uname, upass, email);
            
            System.out.print("Register more user(s)[No -> 0]: ");
            more_data = sc.nextInt();

        } while (more_data != 0);

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
