package org.parkinglot.parkinglot.servelts.Cars;

import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.parkinglot.parkinglot.common.CarDto;
import org.parkinglot.parkinglot.ejb.CarsBean;


import java.io.IOException;
import java.util.List;


@DeclareRoles({"READ_CARS", "WRITE_CARS"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"READ_CARS"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed = {"WRITE_CARS"})})

@WebServlet(name = "Cars", value = "/Cars")
public class Cars extends HttpServlet {

    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        // 1. Preluăm lista actuală de mașini din baza de date
        List<CarDto> cars = carsBean.findAllCars();
        request.setAttribute("cars", cars);

        // 2. Stabilim capacitatea totală a parcării (ex: 20 de locuri)
        int totalCapacity = 20;

        // 3. Calculăm locurile libere: Total - Mașini ocupate
        // Folosim Math.max(0, ...) pentru a nu afișa numere negative în caz de erori de date
        int numberOfFreeParkingSpots = totalCapacity - cars.size();

        // 4. Trimitem valoarea calculată către JSP
        request.setAttribute("numberOfFreeParkingSpots", Math.max(0, numberOfFreeParkingSpots));

        request.getRequestDispatcher("WEB-INF/pages/cars/cars.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        // Logica pentru POST (de obicei Delete sau alte acțiuni)
    }
}