import java.sql.*;

public class Agg {
    static final String url = "jdbc:postgresql://localhost:5432/java_conn_state";
    static final String username = "postgres";
    static final String password = "2426";

    public static void main(String[] args) throws SQLException {
        String query = "SELECT category, COUNT(*) as total_products,AVG(price) as avg_price,MAX(price) as max_price,MIN(price) as min_price,SUM(stock) as total_stock,SUM(units_sold) as total_sold,AVG(rating) as avg_rating FROM product GROUP BY category";
        try (Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement pst = con.prepareStatement(query)) {
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            showHeader(meta, colCount);
            showRows(rs);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showHeader(ResultSetMetaData meta,
            int colCount) throws SQLException {
        for (int i = 1; i <= colCount; i++) {
            System.out.printf("%-12s", meta.getColumnName(i));
        }
        System.out.println();
        System.out.println("-".repeat(12 * colCount));
    }

    public static void showRows(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.printf("%-12s %-12d %-12f %-12f %-12f %-12d %-12d %-12f", rs.getString("category"),
                    rs.getInt("total_products"), rs.getFloat("avg_price"), rs.getFloat("max_price"),
                    rs.getFloat("min_price"), rs.getInt("total_stock"), rs.getInt("total_sold"),
                    rs.getFloat("avg_rating"));

            System.out.println();
        }

    }
}
