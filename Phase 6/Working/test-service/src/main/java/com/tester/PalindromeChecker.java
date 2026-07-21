import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/palindromeChecker")
public class PalindromeChecker extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String input = String.valueOf(req.getParameter("input"));
        String reverse = new StringBuilder(input).reverse().toString();
        String output = "False";
        if (input.equals(reverse)) {
            output = "True";
        }

        req.setAttribute("result", output);
        RequestDispatcher rd = req.getRequestDispatcher("palindrome.jsp");
        rd.forward(req, res);
    }

}
