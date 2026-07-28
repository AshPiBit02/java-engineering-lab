import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.IOError;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserAuthFromDB {
    static Scanner sc = new Scanner(System.in);

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/java_auth";
        String username = "postgres";
        String password = "xxxx";
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) {
        try (Connection con = getConnection()) {
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM userinfo WHERE username=? AND password=?");
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("Authentication successful! Welcome " + username);
            } else {
                System.out.println("Authentication failed! Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
