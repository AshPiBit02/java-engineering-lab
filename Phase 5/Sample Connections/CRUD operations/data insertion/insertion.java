import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class insertion {
    static PreparedStatement pst;

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/java_auth";
        String username = "postgres";
        String password = "2426";
        String sql = "INSERT INTO user_data VALUES(?,?,?,?)";

        Scanner sc = new Scanner(System.in);

        Connection conn = DriverManager.getConnection(url, username, password);
        pst = conn.prepareStatement(sql);
        int more_data = 1;
        do {
            try {
                System.out.println("-".repeat(15) + "Input User Data" + "-".repeat(15));
                System.out.print("UserId: ");
                int id = sc.nextInt();
                if (String.valueOf(id).equals("")) {
                    System.out.println("UserId can't be empty!");
                    continue;
                }
                sc.nextLine();

                System.out.print("Username: ");
                String uname = sc.nextLine();
                if (empty("UserID", uname)) {
                    continue;
                }

                System.out.print("Password: ");
                String upass = sc.nextLine();
                if (empty("UserID", upass)) {
                    continue;
                }

                System.out.print("Email: ");
                String email = sc.nextLine();
                if (empty("UserID", email)) {
                    continue;
                }

                insertData(id, uname, upass, email);
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input! Try Again");
                sc.nextLine();
            }

            System.out.print("\nRegister more user(s)? [No -> 0 | Yes -> any integer]: ");

            if ((more_data = sc.nextInt()) == 0) {
                System.out.print("\n" + "*".repeat(15) + "Exit" + "*".repeat(15));
            }
            sc.nextLine();

        } while (more_data != 0);

    }

    static void insertData(int userId, String username, String password, String email) throws SQLException {
        pst.setInt(1, userId);
        pst.setString(2, username);
        pst.setString(3, password);
        pst.setString(4, email);
        pst.executeUpdate();
        System.out.println("-".repeat(100));
        System.out.println(username + "'s Data inserted.");

    }

    static boolean empty(String field, String fieldValue) throws SQLException {
        if (fieldValue.equals("")) {
            System.out.printf("%s can't be empty! try again\n", field);
            return true;
        }
        return false;
    }

}
