package com.service;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calculate")
public class AddServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String num1Str = request.getParameter("num1");
        String num2Str = request.getParameter("num2");
        String resultValue = "";

        if (num1Str != null && num2Str != null && !num1Str.isEmpty() && !num2Str.isEmpty()) {
            try {
                double num1 = Double.parseDouble(num1Str);
                double num2 = Double.parseDouble(num2Str);
                double sum = num1 + num2;
                resultValue = String.valueOf(sum);
            } catch (NumberFormatException e) {
                resultValue = "Invalid Input";
            }
        }
        request.setAttribute("result", resultValue);

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
}