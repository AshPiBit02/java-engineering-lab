import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class analyst {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc/postgresql://localhost:5432/java_ecommerce";
        String username = "analyst";
        String password = "analyst";

        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();

        Scanner sc = new Scanner(System.in);
        System.out.print("Role: ");
        String role = sc.nextLine();

        System.out.print("User: ");
        String user = sc.nextLine();

        try {
            st.execute("GRANT " + role + " TO " + user);
            System.out.println(role + " role assigned to user " + user);
        } catch (SQLException e) {
            System.out.println("Error! Use valid user/role");
        }
        con.close();

    }
}