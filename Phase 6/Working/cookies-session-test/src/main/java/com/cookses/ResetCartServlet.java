package com.cookses;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.io.PrintWriter;

@WebServlet("/resetCart")
public class ResetCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        Cookie cookie = new Cookie("cartCount", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        res.addCookie(cookie);

        out.println("Cart has been reset.");
    }

}
