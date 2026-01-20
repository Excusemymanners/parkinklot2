package org.parkinglot.parkinglot.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces; // FIX: Use jakarta.ws.rs.Produces, NOT enterprise.inject
import jakarta.ws.rs.core.MediaType;
import org.parkinglot.parkinglot.common.CarDto;
import org.parkinglot.parkinglot.ejb.CarsBean;

import java.util.List;

@Path("/cars")
public class CarsController {

    @Inject
    CarsBean carsBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // FIX: Method must be public for JAX-RS to access it
    public List<CarDto> findAllCars() {
        return carsBean.findAllCars();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CarDto findCar(@PathParam("id") Long id) {
        return carsBean.findById(id);
    }
}