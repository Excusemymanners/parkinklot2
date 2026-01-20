package org.parkinglot.parkinglot.servelts.Cars;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.parkinglot.parkinglot.common.CarDto;
import org.parkinglot.parkinglot.ejb.CarsBean;

import java.io.IOException;

@MultipartConfig
@WebServlet(name = "AddCarPhoto", value = "/AddCarPhoto")
public class AddCarPhoto extends HttpServlet {

    @Inject
    private CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Get the 'id' parameter from the URL (e.g., /AddCarPhoto?id=123)
        String idParam = request.getParameter("id");

        if (idParam != null) {
            Long carId = Long.parseLong(idParam);
            // 2. Fetch the car details from the DB
            CarDto car = carsBean.findById(carId);
            // 3. Set the attribute so the JSP can see ${car}
            request.setAttribute("car", car);
        }

        // 4. Forward to the JSP
        request.getRequestDispatcher("/WEB-INF/pages/cars/addCarPhoto.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Your existing doPost logic for handling the file upload
        Long carId = Long.parseLong(request.getParameter("car_id"));
        Part filePart = request.getPart("file");

        String fileName = filePart.getSubmittedFileName();
        String fileType = filePart.getContentType();
        byte[] fileContent = filePart.getInputStream().readAllBytes();

        carsBean.addPhotoToCar(carId, fileName, fileType, fileContent);
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}