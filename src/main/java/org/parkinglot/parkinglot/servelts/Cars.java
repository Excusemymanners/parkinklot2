package org.parkinglot.parkinglot.servelts;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.parkinglot.parkinglot.common.CarDto;
import org.parkinglot.parkinglot.ejb.CarsBean;


import java.io.IOException;
import java.util.List;

@WebServlet(name = "Cars", value = "/Cars")
public class Cars extends HttpServlet {

    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        List<CarDto> cars = carsBean.findAllCars();

        // THIS LINE IS MISSING IN YOUR CODE:
        request.setAttribute("cars", cars);

        request.setAttribute("numberOfFreeParkingSpots", 10);
        request.getRequestDispatcher("WEB-INF/pages/cars.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
    }
}