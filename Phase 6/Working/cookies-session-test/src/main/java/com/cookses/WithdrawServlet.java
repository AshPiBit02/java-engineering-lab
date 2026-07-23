package com.cookses;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/withdraw")
public class WithdrawServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        String withdrawAmt = req.getParameter("amount");
        int withdrawAmount = 0;
        try {
            withdrawAmount = Integer.parseInt(withdrawAmt);
        } catch (NumberFormatException e) {
            out.println("Invalid Amount!");
            return;
        }
        if (withdrawAmount < 1) {
            out.println("Invalid Amount!");
            return;
        }

        Cookie[] cookies = req.getCookies();
        String balance = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("balance")) {
                    balance = c.getValue();
                }
            }
        }
        int realBalance = Integer.parseInt(balance);
        if (balance == null) {
            realBalance = 0;
        }
        if (balance == null || realBalance < withdrawAmount) {
            out.println("Insufficient balance. Current balance: $" + realBalance);
            return;
        }
        int newBalance = realBalance - withdrawAmount;
        Cookie cookie = new Cookie("balance", String.valueOf(newBalance));
        cookie.setMaxAge(300);
        cookie.setPath("/");
        res.addCookie(cookie);
        out.println("Withdrew $" + withdrawAmount + ". New balance: $" + newBalance);

    }

}
