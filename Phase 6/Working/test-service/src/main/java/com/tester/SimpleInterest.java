import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/simpleInterest")
public class SimpleInterest extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        double p = Double.parseDouble(req.getParameter("principle"));
        double t = Double.parseDouble(req.getParameter("time"));
        double r = Double.parseDouble(req.getParameter("rate"));
        double si = (p * t * r) / 100;

        res.setContentType("text/html");

        PrintWriter out = res.getWriter();

        out.println("<html><body>");
        out.println("<h2>Simple Interest Result</h2>");
        out.println("<p>Principle: " + p + "</p>");
        out.println("<p>Time: " + t + "</p>");
        out.println("<p>Rate: " + p + "</p>");
        out.println("<h3>Result: " + si + "</h3>");
        out.println("</body></html>");

    }

}
