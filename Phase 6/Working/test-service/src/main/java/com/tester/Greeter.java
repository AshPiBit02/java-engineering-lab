import jakarta.servlet.annotation.WebServlet;
import jakarta.servelt.http.HttpServlet;
import jakarta.servelt.http.HttpServletRequest;
import jakarta.servelt.http.HttpServletResponse;

@WebServlet("/greet")
public class Greeter extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        String username = (String) req.getParameter("username");
        req.setAttribute("user", usernmae);
        RequestDispatcher rd = req.getRequestDispatcher("greeting.jsp");
        rd.forward(req, res);
    }
}
