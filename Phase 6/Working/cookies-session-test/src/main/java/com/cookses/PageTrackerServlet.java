import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/track")
public class PageTrackerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        Cookie[] cookies = req.getCookies();
        String lastPage = null;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("lastPage")) {
                    lastPage = c.getValue();
                }
            }
        }
        if (lastPage != null) {
            out.println("Last time you visited: " + lastPage);
        } else {
            out.println("No previous visit found.");
        }

        String page = req.getParameter("page");
        if (page != null && !page.isEmpty()) {
            Cookie cookie = new Cookie("lastPage", page);
            cookie.setMaxAge(30);
            cookie.setPath("/");
            res.addCookie(cookie);
            out.println("Current page: " + page);
        } else {
            out.println("No page specified this time.");
        }
    }
}