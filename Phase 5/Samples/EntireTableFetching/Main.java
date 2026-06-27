import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) throws Exception {

        String url = "jdbc:postgresql://localhost:5432/explore_psql";
        String username = "postgres";
        String password = "2426";
        String sql = "SELECT * FROM inventory";

        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getString("type") + " | " +
                            rs.getString("place") + " | " +
                            rs.getInt("warranty"));
        }

        rs.close();
        st.close();
        con.close();
    }
}
