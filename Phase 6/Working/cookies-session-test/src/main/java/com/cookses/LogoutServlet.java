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

        Cookie[] cookie = req.getCookies();
        Cookie[] cookies = req.getCookies();
        String found = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("name")) {
                    found = c.getValue();
                }
            }
        }
        out.println(found != null ? "Have a nice day, " + found : "Who you?");
    }

}
