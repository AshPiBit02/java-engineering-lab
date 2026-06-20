import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    static ResultSet rs;
    static PreparedStatement ps;
    static Connection con;

    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/java_crud";
        String username = "postgres";
        String password = "2426";

        // Initail SELECT
        con = DriverManager.getConnection(url, username, password);
        System.out.println("Data before Update: ");
        reload_resultset();
        display(rs);

        // UPDATE

        // Empty faculty -> Engineering
        ps = con.prepareStatement("UPDATE students SET faculty = 'Engineering' WHERE faculty='' RETURNING student_id");
        rs = ps.executeQuery(); // returns student_id of affected records
        affected_std(rs);

        System.out.println("Data after update: ");
        reload_resultset();
        display(rs);

        // Marks Update
        ps = con.prepareStatement("UPDATE students\n" + //
                "SET marks = CASE\n" + //
                "               WHEN marks < 0 OR marks IS NULL THEN 0\n" + //
                "               WHEN marks > 100 THEN 100\n" + //
                "               ELSE marks\n" + //
                "            END RETURNING student_id");
        rs = ps.executeQuery();
        affected_std(rs);

        // Grade Update
        ps = con.prepareStatement(
                "UPDATE students " +
                        "SET grade = CASE " +
                        "   WHEN marks > 80 THEN 'A' " +
                        "   WHEN marks BETWEEN 70 AND 80 THEN 'B' " +
                        "   WHEN marks BETWEEN 50 AND 69 THEN 'C' " +
                        "   WHEN marks BETWEEN 40 AND 49 THEN 'D' " +
                        "   ELSE 'F' END " +
                        "RETURNING student_id");
        rs = ps.executeQuery();
        affected_std(rs);

        // Result
        System.out.println("\nFinal result: ");
        System.out.println("-".repeat(100));
        reload_resultset();
        display(rs);

        con.close();

    }

    static void display(ResultSet rs) throws Exception {
        System.out.println("-".repeat(100));
        System.out.printf("%-5s %-10s %-20s %-23s %-10s %-10s%n", "ID", "Name", "Faculty", "Course", "Marks", "Grade");
        while (rs.next()) {
            System.out.printf("%-5d %-10s %-20s %-23s %-10d %-10s%n", rs.getInt("student_id"), rs.getString("name"),
                    rs.getString("faculty"), rs.getString("course"), rs.getInt("marks"), rs.getString("grade"));
        }
    }

    static void reload_resultset() throws Exception {
        ps = con.prepareStatement("SELECT * FROM students ORDER BY student_id");
        rs = ps.executeQuery();

    }

    static void affected_std(ResultSet rs) throws Exception {
        System.out.print("Affected Student(s) ID: ");
        while (rs.next()) {
            System.out.print(" " + rs.getInt("student_id"));
        }
        reload_resultset();
    }

}
