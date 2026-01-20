package org.parkinglot.parkinglot.servelts;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.parkinglot.parkinglot.common.UserDto;
import org.parkinglot.parkinglot.ejb.UserBean;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "EditUser", value = "/EditUser")
public class EditUser extends HttpServlet {

    @Inject
    private UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        UserDto user = userBean.findById(userId);
        request.setAttribute("user", user);

        // Add the list of all possible groups/rights
        List<String> allGroups = List.of("WRITE_CARS", "READ_CARS", "WRITE_USERS", "READ_USERS", "INVOICING");
        request.setAttribute("allGroups", allGroups);

        request.getRequestDispatcher("/WEB-INF/pages/editUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Collect updated data from the form
        Long userId = Long.parseLong(request.getParameter("user_id"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String[] userGroups = request.getParameterValues("userGroups");

        // 2. Call the bean to update both the user and their roles
        userBean.updateUser(userId, username, email, password, List.of(userGroups != null ? userGroups : new String[0]));

        // 3. Redirect back to the users list
        response.sendRedirect(request.getContextPath() + "/Users");
    }
}