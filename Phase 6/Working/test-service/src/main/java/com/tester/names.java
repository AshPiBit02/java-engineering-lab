import java.io.IOException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/pnames")
public class names extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String fname = (String) req.getParameter("fname");
        String lname = (String) req.getParameter("lname");

        req.setAttribute("Fname", fname);
        req.setAttribute("Lname", lname);
        RequestDispatcher rd = req.getRequestDispatcher("fullname.jsp");
        rd.forward(req, res);

    }

}
