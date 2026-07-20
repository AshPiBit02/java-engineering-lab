import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/calculate")
public class Computer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        double num1 = Double.parseDouble(req.getParameter("num1"));
        double num2 = Double.parseDouble(req.getParameter("num2"));
        String op = req.getParameter("operation");
        double result = 0;
        switch (op) {
            case "Add":
                result = num1 + num2;
                break;
            case "Subtract":
                result = num1 - num2;
                break;
            case "Multiply":
                result = num1 * num2;
                break;
            case "Divide":
                if (num2 == 0) {
                    req.setAttribute("result", "Error: Divide by zero");
                } else {
                    result = num1 / num2;
                    req.setAttribute("result", String.valueOf(result));
                }
                break;
        }
        if (!"Divide".equals(op) || num2 != 0) {
            req.setAttribute("result", String.valueOf(result));
        }
        RequestDispatcher rd = req.getRequestDispatcher("calci.jsp");
        rd.forward(req, res);

    }

}
