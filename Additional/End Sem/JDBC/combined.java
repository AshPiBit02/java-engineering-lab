import java.sql.*;

public class combined {
    private static final String URL = "jdbc:postgres://localhost:5432/java_crud";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "XXXX";

    public static Connection getDBConnection() {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void createSchema() {
        SQL = "CREATE TABLE IF NOT EXISTS student(id SERIAL PRIMARY KEY,name VARCHAR(60),level VARCHAR(20),faculty VARCHAR(30),batch DATE)";
        try (Connection con = getDBConnection()) {
            Statement st = con.createStatement(SQL);
            st.executeUpdate();
            System.out.println("student schema created successfully");
        } catch (SQLException e) {
            System.out.println("Schema Creation Filed: " + e.getMessage());
        }
    }

    public static void fetchAllRecord(){
        SQL="SELECT * FROM student";
        try(Connection conn=getDBConnection()){
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(SQL);
            System.out.println("%-5s %-20s %-10s %-15s %-10s","ID","Name","Level","Faculty","Batch");
            whlie(rs.next()){
                System.out.println("%-5d %-20s %-10s %-15s %-10d",rs.getInt("id"),rs.getString("name"),rs.getString("level"),rs.getString("faculty"),rs.getDate("batch"));
            }
        }catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public static void insertStuDetails(String name, String level, String faculty, Date batch) {
        SQL = "INSERT INTO student(name,level,faculty,batch) VALUES(?,?,?,?)";
        try (Connnection conn = getDBConnection()) {
            PreparedStatement pst = conn.prepareStatement(SQL);
            pst.setString(1, name);
            pst.setString(2, level);
            pst.setString(3, faculty);
            pst.setDate(4, date);
            pst.executeUpdate();
            System.out.println("Record insertion successfull");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
