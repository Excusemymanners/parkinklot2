package org.parkinglot.parkinglot.common;

public class CarDto {
    Long id;
    String licensePlate;
    String parkinSpot;
    String ownerName;

    public CarDto(Long id, String licensePlate, String parkinSpot, String ownerName) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.parkinSpot = parkinSpot;
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

    public String getParkinSpot() {
        return parkinSpot;
    }

}
