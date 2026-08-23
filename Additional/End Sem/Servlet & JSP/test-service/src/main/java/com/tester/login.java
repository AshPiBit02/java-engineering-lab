import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/login")
public class login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email.equals("aashishchaudhari249@gmail.com") && password.equals("xxxxxxxx")) {
            req.getRequestDispatcher("successful.jsp").forward(req, res);
        } else {
            req.getRequestDispatcher("unsuccessful.jsp").forward(req, res);
        }
    }

}
