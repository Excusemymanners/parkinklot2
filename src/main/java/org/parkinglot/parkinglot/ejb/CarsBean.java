package org.parkinglot.parkinglot.ejb;


import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.parkinglot.parkinglot.common.CarDto;
import org.parkinglot.parkinglot.entities.Car;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars(){
        LOG.info("Inside findAllCars");
        try{
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List <Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        }catch (Exception ex) {
throw new EJBException(ex);
        }
    }

    private List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> carDtoList = new java.util.ArrayList<>();
        for (Car car : cars) {
            CarDto carDto = new CarDto(
                    car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getOwner().getUsername() // Accessing the User entity's username
            );
            carDtoList.add(carDto);
        }
        return carDtoList;
    }


}
