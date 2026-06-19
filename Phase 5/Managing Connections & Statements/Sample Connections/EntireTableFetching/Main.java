import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.print("Enter table name to retrieve contents: ");
        Scanner sc = new Scanner(System.in);
        String tableName = sc.nextLine();  // read table name
        sc.close();

        String url = "jdbc:postgresql://localhost:5432/explore_psql";
        String username = "postgres";
        String password = "2426";
        String sql = "SELECT * FROM " + tableName;

        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getString("type") + " | " +
                            rs.getString("location") + " | " +
                            rs.getInt("warranty")
            );
        }

        rs.close();
        st.close();
        con.close();
    }
}
