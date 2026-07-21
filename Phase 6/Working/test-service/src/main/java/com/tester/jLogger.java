import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/jlogger")
public class jLogger extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        System.out.println("test");
        String user = String.valueOf(req.getParameter("username"));
        String pass = String.valueOf(req.getParameter("password"));
        if (!user.isEmpty() && !pass.isEmpty()) {
            try {
                insertData(user, pass);
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Fields Can't be Empty");
        }
        req.getRequestDispatcher("userUtil.jsp").forward(req, res);

    }

    void insertData(String username, String password) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement pst = con.prepareStatement("INSERT INTO servlet(username,password) VALUES(?,?)");
                pst.setString(1, username);
                pst.setString(2, password);
                int rowAffected = pst.executeUpdate();
                if (rowAffected > 0) {
                    System.out.println(username + "'s credentials added to DB successfully!");
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
