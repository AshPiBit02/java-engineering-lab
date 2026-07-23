package com.cookses;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/checkTheme")
public class CheckThemeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        res.setContentType("text/plain");
        String theme = req.getParameter("theme");
        if ((theme != null) && !theme.isEmpty()) {
            out.println("Currrent theme: " + theme);
        } else {
            out.println("No theme set. Default is 'light'.");
        }
    }

}
