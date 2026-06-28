import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
                    case 6 -> viewAuditLog(con);
                    case 7 -> {
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
        System.out.printf(" %-10s%n %-10s%n %-10s%n %-10s%n %-10s%n %-10s%n %-10s%n", "1. List all records",
                "2. Search by department", "3. Add new teacher", "4. Update Salary", "5. Delete by id",
                "6. View Audit Log", "7. Exit");
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
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM teacher ORDER BY teacher_id")) {
            showRecords(pst.executeQuery());
        } catch (SQLException e) {
            System.out.println("Record fetching failed: " + e.getMessage());
        }
    }

    static void searchByDepartment(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter department:  ");
        String depart = sc.nextLine();
        try (PreparedStatement pst = con
                .prepareStatement("SELECT * FROM teacher WHERE department=? ORDER BY teacher_id")) {
            pst.setString(1, depart);
            showRecords(pst.executeQuery());
        } catch (SQLException e) {
            System.out.println("Record fetching failed: " + e.getMessage());
        }

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

        int id = -1;
        try (PreparedStatement fetch = con.prepareStatement("SELECT teacher_id FROM teacher WHERE name=?")) {
            fetch.setString(1, name);
            ResultSet rs = fetch.executeQuery();
            if (!rs.next()) {
                System.out.println("Teacher not found!");
                return;
            }
            id = rs.getInt("teacher_id");
        }

        con.setAutoCommit(false);
        try (PreparedStatement pst = con.prepareStatement("UPDATE teacher SET salary = ? WHERE name= ?")) {
            pst.setFloat(1, sal);
            pst.setString(2, name);
            pst.executeUpdate();

            logAudit(con, "UPDATE", id);
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            System.out.println("Update failed: " + e.getMessage());
        } finally {
            con.setAutoCommit(true);
        }
    }

    static void deleteById(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter teacher id whose records are to be deleted: ");
        int id = Integer.parseInt(sc.nextLine());

        try (PreparedStatement fetch = con.prepareStatement("SELECT teacher_id FROM teacher WHERE teacher_id =?")) {
            fetch.setInt(1, id);
            ResultSet rs = fetch.executeQuery();
            if (!rs.next()) {
                System.out.println("ID not found!");
                return;
            }
        }
        con.setAutoCommit(false);
        try (PreparedStatement pst = con.prepareStatement("DELETE FROM teacher WHERE teacher_id=?")) {
            pst.setInt(1, id);
            pst.executeUpdate();

            logAudit(con, "DELETE", id);
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            System.out.println("Delete failed: " + e.getMessage());
        } finally {
            con.setAutoCommit(true);
        }
    }

    static void logAudit(Connection con, String action, int affectedId) throws SQLException {
        try (PreparedStatement pst = con
                .prepareStatement("INSERT INTO audit_log(action,affected_table,affected_id) VALUES(?,'teacher',?)")) {
            pst.setString(1, action);
            pst.setInt(2, affectedId);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Audit log updation failed: " + e.getMessage());
        }
    }

    static void viewAuditLog(Connection con) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM audit_log")) {
            ResultSet rs = pst.executeQuery();
            System.out.printf("%-10s %-10s %-17s %-15s %-10s %n", "ID", "Action", "Affected Table", "Affected ID",
                    "Performed At");
            System.out.println("-".repeat(85));
            while (rs.next()) {
                System.out.printf("%-10d %-10s %-17s %-15d %-10s %n", rs.getInt("id"), rs.getString("action"),
                        rs.getString("affected_table"), rs.getInt("affected_id"), rs.getTimestamp("performed_at"));
            }

        } catch (SQLException e) {
            System.out.println("Audit log failed to display: " + e.getMessage());
        }
    }

}
