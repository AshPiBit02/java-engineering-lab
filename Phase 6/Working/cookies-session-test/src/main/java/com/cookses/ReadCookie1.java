import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/checkCookie")
public class ReadCookie1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        Cookie[] cookies = req.getCookies();
        String found = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("Theme")) {
                    found = c.getValue();
                }
            }
        }
        out.println(found != null ? "Theme= " + found : "Default: No cookie found!");
    }

}
