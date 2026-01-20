package org.parkinglot.parkinglot.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parkinglot.parkinglot.ejb.UserBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DeleteUser", value = "/DeleteUser")
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_USERS"}))
public class DeleteUser extends HttpServlet {

    @Inject
    UserBean usersBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the IDs from the checkboxes (name="user_ids" in your JSP)
        String[] userIdsString = request.getParameterValues("user_ids");

        if (userIdsString != null) {
            List<Long> userIds = new ArrayList<>();
            for (String id : userIdsString) {
                userIds.add(Long.parseLong(id));
            }
            // Call the EJB to delete the users
            usersBean.deleteUsersByIds(userIds);
        }

        // Redirect back to the users page to refresh the list
        response.sendRedirect(request.getContextPath() + "/Users");
    }
}