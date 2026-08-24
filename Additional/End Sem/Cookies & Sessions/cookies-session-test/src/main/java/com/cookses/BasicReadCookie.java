package com.cookses;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/basicReadCookie")

public class BasicReadCookie extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("username")) {
                    res.getWriter().println("Username: " + c.getValue());
                }
            }
        }
    }

}
