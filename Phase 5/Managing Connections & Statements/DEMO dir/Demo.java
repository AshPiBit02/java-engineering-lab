import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class Demo{
    public static void main(String[] args) throws Exception{
        String url="jdbc:postgresql://localhost:5432/explore_psql";
        String username="postgres";
        String password="2426";
        String sql="SELECT name FROM inventory WHERE id=14";

        Connection conn=DriverManager.getConnection(url,username,password);
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(sql);
        while(rs.next()){
            System.out.println(rs.getString("name"));
        }


    }
}