import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

class Mediator {
    private static final String url = "jdbc:postgresql://localhost:5432/java_crud";
    private static final String username = "postgres";
    private static final String password = "2426";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS employee(id SERIAL PRIMARY KEY,name VARCHAR(40),department VARCHAR(40),salary NUMERIC(12,2))";
        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            System.out.println("Employees schema created successfully!");
        } catch (SQLException e) {
            System.out.println("Error: Schema creation failed!");
            e.printStackTrace();
        }
    }

    public static void insertEmpDetails(String name, String department, Double salary) {
        String sql = "INSERT INTO employee(name,department,salary) VALUES(?,?,?)";
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, department);
            pst.setDouble(3, salary);
            pst.executeUpdate();
            System.out.println(name + "'s details inserted successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + name + "'s data insertion failed!!!");
            e.printStackTrace();
        }
    }

    public static void fetchAllEmpDetails() {
        String sql = "SELECT * FROM employee ORDER BY id";
        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.printf("%-15s %-23s %-35s %s%n", "Employee ID", "Name", "Department", "Salary");
            System.out.println("-".repeat(100));
            while (rs.next()) {
                System.out.printf("%-15d %-23s %-35s $%.2f %n", rs.getInt("id"), rs.getString("name"),
                        rs.getString("department"), rs.getDouble("salary"));

            }
        } catch (SQLException e) {
            System.out.println("Error: unable to fetch employees details!!!");
            e.printStackTrace();
        }
    }

    public static void updateSalaryByDept(String dept,float percentage){
        String sql="UPDATE employee SET salary=salary*(1+?) WHERE department=?";
        try{
            Connection con=getConnection();
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setFloat(1,percentage/100);
            pst.setString(2,dept);
            int affectedRow=pst.executeUpdate();
            System.out.println(affectedRow+" employees's salary updated");
        }catch(SQLException e){
            System.out.println("Error: unable to update "+dept+ "'s empoyees salary!!");
            e.printStackTrace();
        }
    }

    public static void deleteEmpById(int id){
        String sql="DELETE FROM employee WHERE id=?";
        try{
            Connection con=getConnection();
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1,id);
            int rowAffected=pst.executeUpdate();
            System.out.println(rowAffected+" Row Affected: Employee's record having id "+id+" deleted successfully!");
        }catch(SQLException e){
            System.out.println("Error: unable to delete employee with id "+id+ " !!!");
            e.printStackTrace();
        }
    }

}

    public class Main {
        public static void main(String[] args) {
            // Mediator.createTable();

            // Mediator.insertEmpDetails("Jon Snow", "Computer Science", 59900.26);
            // Mediator.insertEmpDetails("Aegon Targerian", "Information & Technology",
            // 395000.56);
            // Mediator.insertEmpDetails("Daemon Targerian", "HR", 65200.00);
            // Mediator.insertEmpDetails("Alicent Hightower", "Sales", 65800.00);
            // Mediator.insertEmpDetails("Rhaenera Targerian", "Information & Technology",
            // 98555.02);

            // Mediator.updateSalaryByDept("HR",10);
            // Mediator.fetchAllEmpDetails();

            // Mediator.deleteEmpById(5);
            Mediator.fetchAllEmpDetails();

        }
}
