import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Demo{
    public static void main(String[] args) throws Exception{
        System.out.print("Enter product id to retrieve data: ");
        Scanner sc=new Scanner(System.in);
        int value=sc.nextInt();
        String url="jdbc:postgresql://localhost:5432/explore_psql";
        String username="postgres";
        String password="2426";
        String sql="SELECT * FROM inventory WHERE id=14";

        Connection conn=DriverManager.getConnection(url,username,password);
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(sql);

        while(rs.next()){
            // Retrieving single record value(s)
            int id=rs.getInt(1);
            String product=rs.getString(2);
            String type=rs.getString(3);
            String location=rs.getString(4);
            int warranty=rs.getInt(5);

            System.out.printf("Id: %d   Product: %s   Type: %s    Location: %s      Warranty: %d years",id,product,type,location,warranty);
        }
        conn.close();


    }
}