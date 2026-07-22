import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/greet")
public class GreetServlete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Cookie cookie = new Cookie("name", "Aashish");
        cookie.setMaxAge(10);
        cookie.setPath("/");
        res.addCookie(cookie);
        res.setContentType("text/plain");
        res.getWriter().println("Hello sir, " + cookie.getValue() + " Welcome!");
        res.getWriter().println("Cookie name:Aashish has been sent.");
    }
}
