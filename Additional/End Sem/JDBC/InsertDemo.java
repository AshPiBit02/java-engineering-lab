import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class InsertDemo {
    private static final String url = "jdbc:postgresql://localhost:5432/db_college";
    private static final String username = "postgres";
    private static final String password = "xxxx";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            PreparedStatement pst = con.prepareStatement("INSERT INTO fbl_student(name,faculty,batch) VALUES(?,?,?)");
            pst.setString(1, "Dummy");
            pst.setString(2, "Science & Technology");
            pst.setInt(3, 2024);
            pst.executeUpdate();
            System.out.println("Record inserted successfully")

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
