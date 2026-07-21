import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import jakarta.servlet.ServletException;

import jakarta.servlet.RequestDispatcher;

@WebServlet("/greet")
public class Greeter extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String username = (String) req.getParameter("username");
        req.setAttribute("user", username);
        RequestDispatcher rd = req.getRequestDispatcher("greeting.jsp");
        rd.forward(req, res);
    }
}
