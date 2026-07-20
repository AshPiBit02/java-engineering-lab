import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/hello")
public class InitApp extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServerException, IOException {
        PrintWriter out = res.getWriter();
        out.println("<h1>Hello Servlet</h1>");

    }

}
