package com.cookses;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        if (name != null && age != null && !name.isEmpty() && !age.isEmpty()) {
            Cookie nameCookie = new Cookie("pName", name);
            Cookie ageCookie = new Cookie("pAge", age);
            nameCookie.setMaxAge(60);
            ageCookie.setMaxAge(60);
            nameCookie.setPath("/");
            ageCookie.setPath("/");
            res.addCookie(nameCookie);
            res.addCookie(ageCookie);
            out.println("Saved Profile: " + name + ", " + age + "years old");
        } else {
            Cookie[] cookies = req.getCookies();
            String storedName = null;
            String storedAge = null;
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("pName")) {
                        storedName = c.getValue();
                    }
                    if (c.getName().equals("pAge")) {
                        storedAge = c.getValue();
                    }
                }
            }
            if (storedAge != null && storedName != null) {
                out.println("Stored profile: " + storedName + ", " + storedAge + "  years old.");
            } else {
                out.println("No profile stored yet. Use /profile?name=X&age=Y");
            }
        }
    }

}
