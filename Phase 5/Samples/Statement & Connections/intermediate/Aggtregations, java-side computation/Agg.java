import java.sql.*;

public class Agg {
    static final String url = "jdbc:postgresql://localhost:5432/java_conn_state";
    static final String username = "postgres";
    static final String password = "2426";

    public static void main(String[] args) throws SQLException {
        String query = "SELECT category, COUNT(*) as total_products,AVG(price) as avg_price,MAX(price) as max_price,MIN(price) as min_price,SUM(stock) as total_stock,SUM(units_sold) as total_sold,AVG(rating) as avg_rating FROM product GROUP BY category";
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            try (PreparedStatement pst = con.prepareStatement(query)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData meta = rs.getMetaData();
                int colCount = meta.getColumnCount();

                showHeader(meta, colCount);
                showRows(rs);
            }
            try (PreparedStatement pst = con.prepareStatement(
                    "SELECT COUNT(*) AS total_product, AVG(price) AS avg_price, MAX(price) AS max_price,MIN(price) AS min_price,SUM(stock) AS total_stock,SUM(total_units_sold) AS total_unit_sold,AVG(rating) as avg_rating) FROM product")) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData meta = rs.getMetaData();
                showHeader(meta, meta.getColumnCount());
                rs.next();
                System.out.printf("%-15d %-15.2f %-15.2f %-15.2f %-15d %-15d %-15.2f", rs.getInt("total_product"),
                        rs.getFloat("avg_price"), rs.getFloat("max_price"), rs.getFloat("min_price"),
                        rs.getInt("total_stock"), rs.getInt("total_units_sold"), rs.getFloat("avg_rating"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showHeader(ResultSetMetaData meta,
            int colCount) throws SQLException {
        for (int i = 1; i <= colCount; i++) {
            System.out.printf("%-17s", meta.getColumnName(i));
        }
        System.out.println();
        System.out.println("-".repeat(17 * colCount));
    }

    public static void showRows(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.printf("%-17s %-15d $%-15.2f $%-15.2f $%-16.2f %-15d %-16d %-15.2f", rs.getString("category"),
                    rs.getInt("total_products"), rs.getFloat("avg_price"), rs.getFloat("max_price"),
                    rs.getFloat("min_price"), rs.getInt("total_stock"), rs.getInt("total_sold"),
                    rs.getFloat("avg_rating"));

            System.out.println();
        }

    }
}
