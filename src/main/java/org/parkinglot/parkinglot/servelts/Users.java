package org.parkinglot.parkinglot.servelts;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.parkinglot.parkinglot.common.UserDto;
import org.parkinglot.parkinglot.ejb.UserBean;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Users", value = "/Users") // Use plural "Users" to match your JSP and menu links
public class Users extends HttpServlet {

    @Inject
    private UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        // 1. Fetch the list of users from the EJB
        List<UserDto> users = userBean.findAllUsers();

        // 2. Set the list as a request attribute for the JSP
        request.setAttribute("users", users);

        // 3. Forward to the users.jsp page
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        // Typically, listing pages don't need doPost unless handling inline actions
    }
}