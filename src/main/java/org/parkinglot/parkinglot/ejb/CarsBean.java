package org.parkinglot.parkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.parkinglot.parkinglot.common.CarDto;
import org.parkinglot.parkinglot.entities.Car;
import org.parkinglot.parkinglot.entities.User;

import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars() {
        LOG.info("Inside findAllCars");
        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex) {
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
                    car.getOwner().getUsername()
            );
            carDtoList.add(carDto);
        }
        return carDtoList;
    }

    public void createCar(String licensePlate, String parkingSpot, Long ownerId) {
        LOG.info("createCar called for license plate: " + licensePlate);
        try {
            Car car = new Car();
            car.setLicensePlate(licensePlate);
            car.setParkingSpot(parkingSpot);

            User user = entityManager.find(User.class, ownerId);
            if (user != null) {
                car.setOwner(user);
                user.getCars().add(car);
                entityManager.persist(car);
            }
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    public CarDto findById(Long carId) {
        LOG.info("Inside findById carId: " + carId);
        try {
            Car car = entityManager.find(Car.class, carId);
            if (car != null) {
                return new CarDto(car.getId(), car.getLicensePlate(), car.getParkingSpot(), car.getOwner().getUsername());
            }
            return null;
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long ownerId) {
        LOG.info("Inside updateCar carId: " + carId);

        // 1. Find the car to update
        Car car = entityManager.find(Car.class, carId);
        if (car == null) return;

        // 2. Update basic fields
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        // 3. Handle Owner Change
        User oldOwner = car.getOwner();
        if (oldOwner.getId().equals(ownerId)) {
            // Owner hasn't changed, nothing to do with the relationship
            return;
        }

        // Remove from old owner's list
        oldOwner.getCars().remove(car);

        // Find new owner and link
        User newOwner = entityManager.find(User.class, ownerId);
        if (newOwner != null) {
            car.setOwner(newOwner);
            newOwner.getCars().add(car);
        }
    }

    public void deleteCarsByIds(List<Long> carIds) {
        LOG.info("Inside deleteCarsByIds carIds: " + carIds);
        try {
            for (Long carId : carIds) {
                Car car = entityManager.find(Car.class, carId);
                if (car != null) {
                    // Important: remove relationship from User side before deleting car
                    car.getOwner().getCars().remove(car);
                    entityManager.remove(car);
                }
            }
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }
}