import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    static ResultSet rs, rs1, rs2, rs3, rs4;
    static PreparedStatement ps1, ps2, ps3, ps4;
    static Connection con;

    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/java_crud";
        String username = "postgres";
        String password = "2426";

        // Initail SELECT
        con = DriverManager.getConnection(url, username, password);
        ps1 = con.prepareStatement("SELECT * FROM students");
        rs1 = ps1.executeQuery();
        System.out.println("Data before Update: ");
        display(rs1);

        // UPDATE

        // Empty faculty -> Engineering
        ps2 = con.prepareStatement("UPDATE students SET faculty = 'Engineering' WHERE faculty='' RETURNING student_id");
        rs2 = ps2.executeQuery(); // returns student_id of affected records
        affected_std(rs2);

        System.out.println("Data after update: ");
        reload_resultset();
        display(rs);

        // Marks Update
        ps3 = con.prepareStatement("UPDATE students\n" + //
                "SET marks = CASE\n" + //
                "               WHEN marks < 0 OR marks IS NULL THEN 0\n" + //
                "               WHEN marks > 100 THEN 100\n" + //
                "               ELSE marks\n" + //
                "            END RETURNING student_id");
        rs3 = ps3.executeQuery();
        affected_std(rs3);

        // Grade Update
        PreparedStatement ps4 = con.prepareStatement(
                "UPDATE students " +
                        "SET grade = CASE " +
                        "   WHEN marks > 80 THEN 'A' " +
                        "   WHEN marks BETWEEN 70 AND 80 THEN 'B' " +
                        "   WHEN marks BETWEEN 50 AND 69 THEN 'C' " +
                        "   WHEN marks BETWEEN 40 AND 49 THEN 'D' " +
                        "   ELSE 'F' END " +
                        "RETURNING student_id");
        rs4 = ps4.executeQuery();
        affected_std(rs4);

        // Result
        System.out.println("Final result: ");
        System.out.println("*".repeat(100));
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
        ps1 = con.prepareStatement("SELECT * FROM students ORDER BY student_id");
        rs1 = ps1.executeQuery();

    }

    static void affected_std(ResultSet rs) throws Exception {
        System.out.print("Affected Student(s) ID: ");
        while (rs.next()) {
            System.out.print(" " + rs.getInt("student_id"));
        }
        reload_resultset();
    }

}
