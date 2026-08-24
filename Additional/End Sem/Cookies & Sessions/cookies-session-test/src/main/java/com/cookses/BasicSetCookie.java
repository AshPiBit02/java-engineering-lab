package com.cookses;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.http.Cookie;

public class BasicSetCookie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Cookie cookie = new Cookie("username", "Aegon");
        cookie.setMaxAge(60);
        res.addCookie(cookie);
        res.getWriter().println("Cookie stored successfully!");
    }

}
