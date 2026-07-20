import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.HttpServlet;
import jakarta.servlet.HttpServletRequest;
import jakarta.servlet.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/calculate")
public class Computer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        double num1 = Double.parseDouble(req.getParameter("num1"));
        double num2 = Double.parseDouble(req.getParameter("num2"));
        double result = num1 + num2;
        res.setAttribute("result", result);
        RequestDispatcher rd = res.getRequestDispatcher("calci.jsp");
        rd.forward(req, res);

    }

}
