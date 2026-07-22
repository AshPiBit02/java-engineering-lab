package com.cookses;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/simple")
public class SimpleCookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        Cookie cookie = new Cookie("hello", "world");
        cookie.setMaxAge(60);
        cookie.setPath("/");
        res.addCookie(cookie);
        out.println("Cookie sent!");

        Cookie[] cookies = req.getCookies();
        String value = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("hello")) {
                    value = c.getValue();
                }
            }
        }
        out.println(value != null ? "Found hello, " + value : "Not Found!");
    }

}
