import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        Cookie cookie = new Cookie("userName", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        res.addCookie(cookie);

        out.println("You have been logged out.");
    }

}
