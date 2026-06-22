import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Scanner;

public class super_user {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/java_ecommerce";
        String username = "postgres";
        String password = "2426";

        Scanner sc = new Scanner(System.in);

        // assign role to user
        String sql_role_to_analyst = "GRANT ? TO ?";

        Connection con = DriverManager.getConnection(url, username, password);
        PreparedStatement pst = con.prepareStatement(sql_role_to_analyst);

        System.out.print("User: ");
        String uname = sc.nextLine();

        System.out.print("Role(admin,user_role): ");
        String role = sc.nextLine();

        pst.setString(1, uname);
        pst.setString(2, role);

        try {
            pst.executeQuery();
            System.out.println(role + " assigned to " + uname + "successfully");
        } catch (SQLException e) {
            System.out.println("Error! Use valid user/role");
        }

        con.close();
    }

}
