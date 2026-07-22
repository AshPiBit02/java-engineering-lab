import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/greet")
public class GreetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        String name = req.getParameter("name");

        if (name != null && !name.isEmpty()) {
            Cookie cookie = new Cookie("userName", name);
            cookie.setMaxAge(300);
            cookie.setPath("/");
            res.addCookie(cookie);

            out.println("Welcome, " + name + " ! I'll remember you.");
        } else {
            Cookie[] cookies = req.getCookies();
            String found = null;
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("userName")) {
                        found = c.getValue();
                    }
                }
            }
            if (found != null) {
                out.println("Welcome back, " + found + "!");
            } else {
                out.println("Hello, stranger! Visit /greet?name=YourName to introduce yourself.");
            }
        }
    }
}
