package com.cookses;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.io.PrintWriter;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        res.setContentType("text/plain");

        Cookie[] cookies = req.getCookies();
        String count = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("cartCount")) {
                    count = c.getValue();
                }
            }
        }
        if (count == null) {
            count = "0";
        }

        String qtyParam = req.getParameter("qty");
        if (qtyParam == null || qtyParam.isEmpty()) {
            out.println("Invalid quantity!");
            return;
        }
        int qty;
        try {
            qty = Integer.parseInt(qtyParam);
        } catch (NumberFormatException e) {
            out.println("Invalid qunatity!");
            return;
        }

        int existingCount = Integer.parseInt(count);
        int newTotal = existingCount + qty;

        Cookie cartCookie = new Cookie("cartCount", String.valueOf(newTotal));
        cartCookie.setMaxAge(300);
        cartCookie.setPath("/");
        res.addCookie(cartCookie);

        out.println("Added " + qty + " item(s). Cart total: " + newTotal);

    }

}
