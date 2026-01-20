package org.parkinglot.parkinglot.servelts.Users;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.parkinglot.parkinglot.common.UserDto;
import org.parkinglot.parkinglot.ejb.UserBean;
import org.parkinglot.parkinglot.servelts.Cars.AddCar;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "AddUser", value = "/AddUser")
public class AddUser extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(AddCar.class.getName());


    @Inject
    private UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        List<UserDto> users = userBean.findAllUsers();
        request.setAttribute("users", users);

        request.getRequestDispatcher("/WEB-INF/pages/users/addUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String[] userGroups = request.getParameterValues("userGroups"); // Added to handle roles

        // Call the bean to persist the user and their groups
        userBean.createUser(username, password, email, List.of(userGroups));

        // Redirect to the Users list page after success
        response.sendRedirect(request.getContextPath() + "/Users");
    }
}