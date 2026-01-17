package org.parkinglot.parkinglot.servelts;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Check if the 'error' parameter we added in web.xml is present
        if (request.getParameter("error") != null) {
            request.setAttribute("message", "Username or password incorrect.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // This is only called if the user manually POSTs to /Login instead of j_security_check
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}