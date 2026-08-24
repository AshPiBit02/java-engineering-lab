package com.cookses;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/setServletCookie")
public class SetServletCookie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String mode = req.getParameter("mode");

        Cookie c1 = new Cookie("username", username);
        Cookie c2 = new Cookie("password", password);
        Cookie c3 = new Cookie("mode", mode);

        c1.setMaxAge(60);
        c2.setMaxAge(60);
        c3.setMaxAge(60);

        res.addCookie(c1);
        res.addCookie(c2);
        res.addCookie(c3);

        res.getWriter().println("Cookie has been stored successfully!");

    }

}
