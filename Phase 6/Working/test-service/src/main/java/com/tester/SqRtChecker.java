import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/perfectSQ")
public class SqRtChecker extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        int num = Integer.parseInt(req.getParameter("num"));
        int sqrt = (int) Math.sqrt(num);

        String result = "False";
        if (sqrt * sqrt == num) {
            result = "True";
        }
        req.setAttribute("result", result);
        RequestDispatcher rd = req.getRequestDispatcher("PerfectSq.jsp");
        rd.forward(req, res);
    }

}
