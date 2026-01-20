package org.parkinglot.parkinglot.servelts;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.parkinglot.parkinglot.ejb.UserBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DeleteUser", value = "/DeleteUser")
public class DeleteUser extends HttpServlet {

    @Inject
    UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        // Redirect back to Users if accessed via GET
        response.sendRedirect(request.getContextPath() + "/Users");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        // Change "userIds" to "user_ids" to match your JSP checkboxes
        String[] userIdsAsString = request.getParameterValues("user_ids");

        if (userIdsAsString != null) {
            List<Long> userIds = new ArrayList<>();
            for (String userIdAsString : userIdsAsString) {
                userIds.add(Long.parseLong(userIdAsString));
            }
            // Execute deletion in the bean
            userBean.deleteUsersByIds(userIds);
        }

        response.sendRedirect(request.getContextPath() + "/Users");
    }
}