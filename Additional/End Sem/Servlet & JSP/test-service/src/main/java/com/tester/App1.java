import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/intro")
public class App1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String name = req.getParameter("name");
        String address = req.getParameter("city");
        String profession = req.getParameter("profession");

        req.setAttribute("name", name);
        req.setAttribute("city", address);
        req.setAttribute("profession", profession);
        req.getRequestDispatcher("intro2.jsp").forward(req, res);

    }

}
