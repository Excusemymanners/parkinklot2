package org.parkinglot.parkinglot.common;

public class CarPhotoDto {
    private Long id;
    private String filename;
    private String filetype;
    private byte[] filecontent; // Corrected from int to byte[]

    public CarPhotoDto(Long id, String filename, String fileType, byte[] fileContent) {
        this.id = id;
        this.filename = filename;
        this.filetype = fileType;
        this.filecontent = fileContent; // Now types match
    }

    public Long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getFileType() {
        return filetype;
    }

    // Returns the actual image data for the Servlet to stream
    public byte[] getFileContent() {
        return filecontent;
    }
}