import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/sessionDemo")
public class SessionDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            username = "Ace";
            session.setAttribute("username", username);
            out.println("New Session created. Welcome, " + username);
        } else {
            out.println("Welcome back, " + username + " !");
        }
        out.println("Session ID: " + session.getId());
    }

}
