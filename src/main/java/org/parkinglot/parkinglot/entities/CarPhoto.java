package org.parkinglot.parkinglot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "car_photo")
public class CarPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fileName")
    private String fileName;

    @Column(name="fileType")
    private String fileType;

    @Lob
    @Column(name = "fileContent", columnDefinition = "LONGBLOB")
    private byte[] fileContent;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public byte[] getFileContent() { return fileContent; }
    public void setFileContent(byte[] fileContent) { this.fileContent = fileContent; }
    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public void setFilename(String filename) { this.fileName = filename; }
    public String getFilename() { return fileName; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public String getFileType() { return fileType; }
}