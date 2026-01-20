package org.parkinglot.parkinglot.servelts;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.parkinglot.parkinglot.common.UserDto;
import org.parkinglot.parkinglot.ejb.InvoiceBean;
import org.parkinglot.parkinglot.ejb.UserBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "Users", value = "/Users") // Use plural "Users" to match your JSP and menu links
public class Users extends HttpServlet {

    @Inject
    private UserBean userBean;

    @Inject
    private InvoiceBean invoiceBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Fetch the list of users
        List<UserDto> users = userBean.findAllUsers();
        request.setAttribute("users", users);

        // 2. IMPORTANT: Check for invoices BEFORE forwarding
        if (!invoiceBean.getUserIds().isEmpty()) {
            Collection<String> usernames = userBean.findUsernamesByUserIds(invoiceBean.getUserIds());
            request.setAttribute("invoices", usernames);
        }

        // 3. Forward ONLY AFTER all attributes are set
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] userIdsAsString = request.getParameterValues("user_ids");

        if (userIdsAsString != null) {
            List<Long> userIds = new ArrayList<>();
            for (String userIdAsString : userIdsAsString) {
                userIds.add(Long.parseLong(userIdAsString));
            }
            // Use the injected instance (lowercase 'i')
            invoiceBean.getUserIds().addAll(userIds);
        }

        response.sendRedirect(request.getContextPath() + "/Users");
    }
}