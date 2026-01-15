package org.parkinglot.parkinglot.common;

public class CarDto {
    Long id;
    String licensePlate;
    String parkingSpot;
    String ownerName;

    public CarDto(Long id, String licensePlate, String parkinSpot, String ownerName) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.parkingSpot = parkinSpot;
        this.ownerName = ownerName;
    }

    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getParkingSpot() {
        return parkingSpot;
    }

}
