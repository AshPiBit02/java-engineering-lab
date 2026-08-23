import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/intro")
public class App1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String name = req.getParameter("name");
        String address = req.getParameter("city");
        String profession = req.getParameter("profession");

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<html><body>");
        out.println("Name of Applicant: " + name + "<br>");
        out.println("City: " + address + "<br>");
        out.println("Profession: " + profession + "<br>");
        out.println("</body></html>");

    }

}
