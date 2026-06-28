import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static final String url = "jdbc:postgresql://localhost:5432/java_conn_state";
    static final String username = "postgres";
    static final String password = "2426";

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            while (true) {
                showMenu();
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> listAll(con);
                    case 2 -> searchByDepartment(con, sc);
                    case 3 -> addTeacher(con, sc);
                    case 4 -> UpdatedSalary(con, sc);
                    case 5 -> deleteById(con, sc);
                    case 6 -> {
                        System.out.println("Exit!");
                        return;
                    }
                    default -> System.out.println("Invalid choice!!!");
                }
            }
        }
    }

    static void showMenu() {
        System.out.println();
        System.out.println("-".repeat(15) + " MENU " + "-".repeat(15));
        System.out.printf(" %-10s%n %-10s%n %-10s%n %-10s%n %-10s%n %-10s%n", "1. List all records",
                "2. Search by department", "3. Add new teacher", "4. Update Salary", "5. Delete by id", "6. Exit");
        System.out.print("Your choice: ");
    }

    static void Header() {
        System.out.printf("%-10s %-17s %-20s %-28s %-15s %n", "ID", "Name", "Department", "Course", "Salary");
        System.out.println("-".repeat(100));
    }

    static void showRecords(ResultSet rs) throws SQLException {
        Header();
        while (rs.next()) {
            System.out.printf("%-10d %-17s %-20s %-28s $%-15.2f %n", rs.getInt("teacher_id"), rs.getString("name"),
                    rs.getString("department"), rs.getString("course"), rs.getFloat("salary"));
        }
    }

    static void listAll(Connection con) throws SQLException {
        PreparedStatement pst = con.prepareStatement("SELECT * FROM teacher ORDER BY teacher_id");
        ResultSet rs = pst.executeQuery();
        showRecords(rs);
    }

    static void searchByDepartment(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter department:  ");
        String depart = sc.nextLine();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM teacher WHERE department=? ORDER BY teacher_id");
        pst.setString(1, depart);
        ResultSet rs = pst.executeQuery();
        showRecords(rs);

    }

    static void addTeacher(Connection con, Scanner sc) throws SQLException {
        System.out.println("-".repeat(10) + "Insert new teacher details" + "-".repeat(10));
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Department: ");
        String dept = sc.nextLine();
        System.out.print("Course: ");
        String course = sc.nextLine();
        System.out.print("Salary: ");
        Float salary = Float.parseFloat(sc.nextLine());

        con.setAutoCommit(false);
        try (PreparedStatement pst = con
                .prepareStatement("INSERT INTO teacher(name,department,course,salary) VALUES(?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, name);
            pst.setString(2, dept);
            pst.setString(3, course);
            pst.setFloat(4, salary);
            pst.executeUpdate();

            ResultSet keys = pst.getGeneratedKeys();
            if (keys.next()) {
                int generatedId = keys.getInt(1);
                logAudit(con, "INSERT", generatedId);
            }
            con.commit();
        } catch (SQLException e) {
            con.rollback();
        } finally {
            con.setAutoCommit(true);
        }
    }

    static void UpdatedSalary(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter name of teacher whose salary is to be updated: ");
        String name = sc.nextLine();
        System.out.print("Enter updated salary: ");
        Float sal = Float.parseFloat(sc.nextLine());

        PreparedStatement pst = con.prepareStatement("UPDATE teacher SET salary = ? WHERE name= ?");
        pst.setFloat(1, sal);
        pst.setString(2, name);
        pst.executeUpdate();
    }

    static void deleteById(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter teacher id whose records are to be deleted: ");
        int id = Integer.parseInt(sc.nextLine());

        PreparedStatement pst = con.prepareStatement("DELETE FROM teacher WHERE teacher_id=?");
        pst.setInt(1, id);
        pst.executeUpdate();
    }

    static void logAudit(Connection con, String action, int affectedId) throws SQLException {
        try (PreparedStatement pst = con
                .prepareStatement("INSERT INTO audit_log(action,affected_table,affected_id) VALUES(?,'teacher',?)")) {
            pst.setString(1, action);
            pst.setInt(2, affectedId);
            pst.executeUpdate();
        }
    }

}
