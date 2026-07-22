import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/setData")
public class SetDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Cookie cookie = new Cookie("visitorName", "Aashish");
        cookie.setMaxAge(60);
        cookie.setPath("/");
        res.addCookie(cookie);

        HttpSession session = req.getSession();
        session.setAttribute("LoginTime", System.currentTimeMillis());

        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println("Cookie set: visitorName = Aashish (expires in 60s)");
        out.println("Session create. ID: " + session.getId());
        out.println("Now visit /readData to see them read back.");
    }

}
