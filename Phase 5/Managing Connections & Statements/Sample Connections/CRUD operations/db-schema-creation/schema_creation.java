import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;

public class schema_creation {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/explore_psql";
        String username = "postgres";
        String password = "2426";

        String schema_sql = "CREATE TABLE employees(id SERIAL PRIMARY KEY,name VARCHAR(30),age INT, department VARCHAR(40))";

        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();
        st.executeUpdate(schema_sql);

        con.close();

    }

}
