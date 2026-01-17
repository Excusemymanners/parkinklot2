package org.parkinglot.parkinglot.servelts;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.parkinglot.parkinglot.common.UserDto;
import org.parkinglot.parkinglot.ejb.CarsBean; // Added import
import org.parkinglot.parkinglot.ejb.UserBean;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger; // Added import

@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_CARS"}))

@WebServlet(name = "AddCar", value = "/AddCar")
public class AddCar extends HttpServlet {

    // Setup the logger so the catch block works
    private static final Logger LOG = Logger.getLogger(AddCar.class.getName());

    @Inject
    private UserBean userBean;

    @Inject
    private CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        List<UserDto> users = userBean.findAllUsers();
        request.setAttribute("users", users);

        request.getRequestDispatcher("/WEB-INF/pages/addCar.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Parameters come back as Strings from the browser
        String licensePlate = request.getParameter("licensePlate");
        String parkingSpot = request.getParameter("parkingSpot");
        String ownerIdStr = request.getParameter("ownerId"); // Must be String initially

        try {
            // 2. Convert and Save
            if (ownerIdStr != null && !ownerIdStr.isEmpty()) {
                Long ownerId = Long.parseLong(ownerIdStr);

                // Call the bean method (careateCar or createCar depending on your bean)
                carsBean.createCar(licensePlate, parkingSpot, ownerId);
            }
        } catch (NumberFormatException e) {
            LOG.severe("Invalid Owner ID format: " + ownerIdStr);
        }

        // 3. Redirect back to the list
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}