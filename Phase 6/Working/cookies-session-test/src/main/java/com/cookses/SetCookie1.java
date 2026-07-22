import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/writeCookie")
public class SetCookie1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Cookie cookie = new Cookie("Theme", "Dark");
        cookie.setMaxAge(20);
        cookie.setPath("/");

        res.addCookie(cookie);

        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println("Cookie: 'Theme = Dark' gas been sent.");
    }

}
