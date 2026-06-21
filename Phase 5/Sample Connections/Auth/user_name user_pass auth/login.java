import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class login {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/java_auth";
        String username = "postgres";
        String password = "2426";
        Connection conn = DriverManager.getConnection(url, username, password);

        Scanner sc = new Scanner(System.in);
        System.out.print("Username: ");
        String input_user = sc.nextLine();
        System.out.print("Password: ");
        String input_password = sc.nextLine();

        PreparedStatement pst = conn.prepareStatement("SELECT user_password FROM user_data WHERE username=?");
        pst.setString(1, input_user);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String storedPass = rs.getString("user_password");
            if (storedPass.equals(input_password)) {
                System.out.println("Login successful for " + input_user);
            } else {
                System.out.println("Incorrect password!!!!");
            }
        } else {
            System.out.println("User not found!");
        }

    }
}
