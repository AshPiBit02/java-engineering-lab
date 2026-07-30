import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/setCookie")
public class SetCookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        Cookie c1 = new Cookie("username", "ashpibit02");
        Cookie c2 = new Cookie("password", "xxxxxxxxx");

        c1.setMaxAge(60);
        c2.setMaxAge(60);

        res.addCookie(c1);
        res.addCookie(c2);

        out.println("<h3>Cookies have been set!</h3>");
    }

}
