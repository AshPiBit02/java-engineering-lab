import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.PrintWriter;
import java.io.IOException;

@WebServlet("/readData")
public class ReadDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        Cookie[] cookies = req.getCookies();
        String cookieVal = "not found";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("visitorName")) {
                    cookieVal = c.getValue();
                }
            }
        }
        out.println("Cookie visitorName = " + cookieVal);

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("loginTime") != null) {
            out.println("Session ID: " + session.getId());
            out.println("Login time stored: " + session.getAttribute("loginTime"));
        } else {
            out.println("No Active session found!");
        }
    }

}
