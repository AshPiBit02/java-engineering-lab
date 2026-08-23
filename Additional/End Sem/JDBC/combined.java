import java.sql.*;

public class combined {
    private static final String URL = "jdbc:postgresql://localhost:5432/java_crud";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "2426";

    public static Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void createSchema() {
        String SQL = "CREATE TABLE IF NOT EXISTS student(id SERIAL PRIMARY KEY,name VARCHAR(60),level VARCHAR(20),faculty VARCHAR(30),batch INT)";
        try (Connection con = getDBConnection()) {
            Statement st = con.createStatement();
            st.executeUpdate(SQL);
            System.out.println("student schema created successfully");
        } catch (SQLException e) {
            System.out.println("Schema Creation Filed: " + e.getMessage());
        }
    }

    public static void fetchAllRecord() {
        String SQL = "SELECT * FROM student";
        try (Connection conn = getDBConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            System.out.printf("%-5s %-20s %-10s %-30s %-10s", "ID", "Name", "Level", "Faculty", "Batch");
            System.out.println();
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-10s %-30s %-10d", rs.getInt("id"), rs.getString("name"),
                        rs.getString("level"), rs.getString("faculty"), rs.getInt("batch"));
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void insertStuDetails(String name, String level, String faculty, int batch) {
        String SQL = "INSERT INTO student(name,level,faculty,batch) VALUES(?,?,?,?)";
        try (Connection conn = getDBConnection()) {
            PreparedStatement pst = conn.prepareStatement(SQL);
            pst.setString(1, name);
            pst.setString(2, level);
            pst.setString(3, faculty);
            pst.setInt(4, batch);
            pst.executeUpdate();
            System.out.println("Record insertion successfull");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        combined.createSchema();
        combined.insertStuDetails("Maria", "Bachelor", "Science & Technology", 2025);
        combined.fetchAllRecord();
    }

}
