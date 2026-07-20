import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/pass")
public class PassingData extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String name = req.getParameter("user");
        req.setAttribute("username", name);
        RequestDispatcher rd = req.getRequestDispatcher("passer.jsp");
        rd.forward(req, res);
    }
}