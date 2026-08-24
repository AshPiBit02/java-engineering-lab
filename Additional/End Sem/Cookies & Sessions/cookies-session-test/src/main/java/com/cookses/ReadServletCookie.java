package com.cookses;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/readServletCookie")
public class ReadServletCookie extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("username")) {
                    res.getWriter().println("Username: " + c.getValue());
                } else if (c.getName().equals("password")) {
                    res.getWriter().println("Password: " + c.getValue());
                } else if (c.getName().equals("mode")) {
                    res.getWriter().println("Mode: " + c.getValue());
                }
            }
        }
    }

}
