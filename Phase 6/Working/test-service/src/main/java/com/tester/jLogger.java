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
            throws IOException, ServletException, SQLException {
        String user = String.valueOf(req.getParameter("username"));
        String pass = String.valueOf(req.getParameter("password"));
        if (!user.isEmpty() || !pass.isEmpty()) {
            insertData(user, pass);
        } else {
            System.out.println("Fields Can't be Empty");
        }

    }

    void insertData(String username, String password) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement pst = con.prepareStatement("INSERT INTO servlet(username,password) VALUES(?,?)");
            pst.setString(1, username);
            pst.setString(2, password);
            int rowAffected = pst.executeUpdate();
            System.out.println(username + "'s credentails added to DB successfully!");
        }
    }
}
