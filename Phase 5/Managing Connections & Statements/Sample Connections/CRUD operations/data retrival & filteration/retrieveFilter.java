import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class retrieveFilter {
    static PreparedStatement pst;

    public static void main(String[] args) throws Exception {

        String url = "jdbc:postgresql://localhost:5432/java_crud";
        String username = "postgres";
        String password = "2426";

        int rate = 500;

        Connection con = DriverManager.getConnection(url, username, password);
        pst = con.prepareStatement("SELECT name FROM inventory WHERE rate>?");
        pst.setInt(1, rate);

        ResultSet rs = pst.executeQuery();

        System.out.println("Products with rate greater than $" + rate);
        System.out.println("-".repeat(50));
        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }

        con.close();
    }

}
