import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/login")
public class Logger extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String user = req.getParameter("username");
        String pass = req.getParameter("password");

        req.setAttribute("username", user);
        req.setAttribute("password", pass);
        RequestDispatcher rd;
        if (user.equals("github") && pass.equals("hubgit2")) {
            rd = req.getRequestDispatcher("welcome.jsp");
        } else {
            rd = req.getRequestDispatcher("notwelcome.jsp");
        }
        rd.forward(req, res);
    }

}
