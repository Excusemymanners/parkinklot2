package org.parkinglot.parkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.parkinglot.parkinglot.common.CarDto;
import org.parkinglot.parkinglot.common.CarPhotoDto;
import org.parkinglot.parkinglot.entities.Car;
import org.parkinglot.parkinglot.entities.CarPhoto;
import org.parkinglot.parkinglot.entities.User;

import java.util.Arrays;
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
        LOG.info("Updating carId: " + carId + " with ownerId: " + ownerId);
        try {
            // 1. Încărcăm mașina
            Car car = entityManager.find(Car.class, carId);
            if (car == null) return;

            // 2. Actualizăm câmpurile simple
            car.setLicensePlate(licensePlate);
            car.setParkingSpot(parkingSpot);

            // 3. Gestionăm schimbarea proprietarului
            // Verificăm dacă owner-ul s-a schimbat efectiv
            if (car.getOwner() == null || !car.getOwner().getId().equals(ownerId)) {
                User newOwner = entityManager.find(User.class, ownerId);
                if (newOwner != null) {
                    car.setOwner(newOwner);
                    // Nu mai este nevoie de user.getCars().add(car) aici,
                    // deoarece Car este "owning side" al relației.
                }
            }

            // 4. Forțăm sincronizarea cu baza de date pentru a vedea eroarea imediat
            entityManager.flush();

        } catch (Exception e) {
            LOG.severe("Transaction failed: " + e.getMessage());
            throw new EJBException(e);
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

    public void addPhotoToCar(Long carId, String filename, String fileType, byte[] fileContent) {
        LOG.info("addPhotoToCar called for carId: " + carId);
        Car car = entityManager.find(Car.class, carId);

        if (car != null) {

            if (car.getPhoto() != null) {
                CarPhoto oldPhoto = car.getPhoto();
                car.setPhoto(null);
                entityManager.remove(oldPhoto);
                entityManager.flush(); // Crucial: clear the database slot before new record
            }


            CarPhoto photo = new CarPhoto();
            photo.setFilename(filename);
            photo.setFileType(fileType);
            photo.setFileContent(fileContent);
            photo.setCar(car);
            car.setPhoto(photo);

            entityManager.persist(photo);
        }
    }

    public CarPhotoDto findPhotoByCarId(Long carId) {

        List<CarPhoto> photos = entityManager
                .createQuery("SELECT p FROM CarPhoto p where p.car.id = :id", CarPhoto.class)
                .setParameter("id", carId)
                .getResultList();

        if (photos.isEmpty()) {
            return null;
        }

        CarPhoto photo = photos.get(0);
        return new CarPhotoDto(photo.getId(), photo.getFilename(), photo.getFileType(), photo.getFileContent());
    }
}