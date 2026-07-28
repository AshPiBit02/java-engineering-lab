import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/log")
public class Log extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (!username.isEmpty() && !password.isEmpty()) {
            req.setAttribute("user", username);
            req.getRequestDispatcher("success.jsp").forward(req, res);
        } else {
            req.getRequestDispatcher("failed.jsp").forward(req, res);
        }
    }

}
