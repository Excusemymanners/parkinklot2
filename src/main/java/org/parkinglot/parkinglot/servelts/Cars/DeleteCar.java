package org.parkinglot.parkinglot.servelts.Cars;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.parkinglot.parkinglot.ejb.CarsBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DeleteCar", value = "/DeleteCar")
public class DeleteCar extends HttpServlet {
    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String[] carIdsAsString = request.getParameterValues("carIds");
        if  (carIdsAsString != null){
            List<Long> carIds = new ArrayList<>();
            for (String carIdAsString : carIdsAsString) {
                carIds.add(Long.parseLong(carIdAsString));
            }
            carsBean.deleteCarsByIds(carIds);


        }
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}