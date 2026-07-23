package com.cookses;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/setTheme")
public class SetThemeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        res.setContentType("text/plain");
        String theme = req.getParameter("theme");
        if (theme.equals("light") || theme.equals("dark")) {
            Cookie cookie = new Cookie("siteTheme", theme);
            cookie.setMaxAge(60);
            cookie.setPath("/");
            res.addCookie(cookie);
            out.println("Theme set to :" + theme);
            out.println("Cookie sent to server!");
        } else if (theme != null) {
            out.println("Invalid Theme!!!");
        } else {
            out.println("Please specify a theme: /setTheme?theme=light or /setTheme?theme=dark");
        }
    }

}
