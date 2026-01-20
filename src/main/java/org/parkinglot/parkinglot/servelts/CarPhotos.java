package org.parkinglot.parkinglot.servelts;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.parkinglot.parkinglot.common.CarPhotoDto;
import org.parkinglot.parkinglot.ejb.CarsBean;

import java.io.IOException;

@WebServlet(name = "CarPhotos", value = "/CarPhotos") // Rename to plural
public class CarPhotos extends HttpServlet { // Rename class to CarPhotos

    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Integer carId = Integer.parseInt(idParam);
            CarPhotoDto photo = carsBean.findPhotoByCarId(Long.valueOf(carId));

            if (photo != null && photo.getFileContent() != null) {
                response.setContentType(photo.getFileType());
                response.setContentLength(photo.getFileContent().length);
                response.getOutputStream().write(photo.getFileContent());
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}