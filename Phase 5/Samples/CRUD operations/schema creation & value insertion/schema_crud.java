import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;

public class schema_crud {
    static PreparedStatement pst;

    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/explore_psql";
        String username = "postgres";
        String password = "2426";

        String schema_sql = "CREATE TABLE IF NOT EXISTS employees(" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(30)," +
                "age INT," +
                "department VARCHAR(40))";

        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();

        // Schema creation
        st.executeUpdate(schema_sql);

        // values insertion
        String insert_sql = "INSERT INTO employees(name,age,department) VALUES(?,?,?)";
        pst = con.prepareStatement(insert_sql);

        insertEmployee("Juj", 23, "HR");
        insertEmployee("Jon Snow", 22, "North");
        insertEmployee("Rob Startk", 19, "West");

        con.close();
    }

    static void insertEmployee(String name, int age, String department) throws Exception {
        pst.setString(1, name);
        pst.setInt(2, age);
        pst.setString(3, department);
        pst.executeUpdate();
        System.out.printf("%s's data inserted successfully.%n", name);
    }
}
