import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class retrieveFilter {
    static PreparedStatement pst, pst2;

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

        pst2 = con.prepareStatement("Select * FROM inventory");
        ResultSet rs2 = pst2.executeQuery();

        System.out.println("Inventory: \n");
        System.out.printf("%-10s %-30s %-20s %-10s %-10s %-10s%n",
                "ID", "Name", "Category", "Rate", "Quantity", "Total");
        System.out.println("-".repeat(100));

        while (rs2.next()) {
            System.out.printf("%-10d %-30s %-20s $%-10.2f %-10d $%-10.2f%n",
                    rs2.getInt("product_id"),
                    rs2.getString("name"),
                    rs2.getString("category"),
                    rs2.getDouble("rate"),
                    rs2.getInt("quantity"),
                    rs2.getDouble("rate") * rs2.getInt("quantity"));

        }

        con.close();
    }

}
