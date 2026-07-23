package com.cookses;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        res.setContentType("text/plain");

        Cookie[] cookies = req.getCookies();
        String curBalance = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("balance")) {
                    curBalance = c.getValue();
                }
            }
        }
        if (curBalance == null) {
            curBalance = "0";
        }
        String amountParm = req.getParameter("amount");
        int depAmount = 0;
        try {
            depAmount = Integer.parseInt(amountParm);
        } catch (NumberFormatException e) {
            out.println("Invalid amount.");
            return;
        }
        if (depAmount <= 0) {
            out.print("Amount must be positive.");
            return;
        }
        int newBalance = Integer.parseInt(curBalance) + depAmount;
        Cookie cookie = new Cookie("balance", String.valueOf(newBalance));
        cookie.setMaxAge(120);
        cookie.setPath("/");
        res.addCookie(cookie);

        out.println("Deposited $" + depAmount + ". New balance: $" + newBalance);

    }

}
