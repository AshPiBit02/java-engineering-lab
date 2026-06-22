import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Scanner;

public class super_user {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/java_ecommerce";
        String username = "postgres";
        String password = "2426";

        Scanner sc = new Scanner(System.in);

        System.out.print("User: ");
        String uname = sc.nextLine();

        System.out.print("Role(admin,user_role): ");
        String role = sc.nextLine();

        // assign role to user
        String sql_role_to_user = "GRANT " + role + " TO " + uname;

        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();

        try {
            st.executeUpdate(sql_role_to_user);
            System.out.println(role + " role assigned to " + uname + " successfully");
        } catch (SQLException e) {
            System.out.println("Error! Use valid user/role");
        }

        con.close();
    }

}
