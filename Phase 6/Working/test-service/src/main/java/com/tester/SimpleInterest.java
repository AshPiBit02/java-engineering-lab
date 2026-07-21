import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/simpleInterest")
public class SimpleInterest extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        double p = Double.parseDouble(req.getParameter("principle"));
        double t = Double.parseDouble(req.getParameter("time"));
        double r = Double.parseDouble(req.getParameter("rate"));
        double resu = (p * t * r) / 100;
        req.setAttribute("result", resu);
        RequestDispatcher rd = req.getRequestDispatcher("SI.html");
        rd.forward(req, res);

    }

}
