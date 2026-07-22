import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/visitCount")
public class VisitCounterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        Cookie[] cookies = req.getCookies();
        int count = 0;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("visitCount")) {
                    count = Integer.parseInt(c.getValue());
                }
            }
        }
        count++;

        Cookie visitCookie = new Cookie("visitCount", String.valueOf(count));
        visitCookie.setMaxAge(30);
        visitCookie.setPath("/");
        res.addCookie(visitCookie);

        out.println("You have visited this page " + count + " time(s).");
    }

}
