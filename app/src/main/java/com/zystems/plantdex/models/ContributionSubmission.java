package com.zystems.plantdex.models;

import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ContributionSubmission {
    private int id;
    private String scientificName;
    private String commonName;
    private String remarks;
    private String locations;
    private String fileName;
    private byte[] fileData;

    public ContributionSubmission() {
    }

    public ContributionSubmission(int id, String scientificName, String commonName, String remarks, String locations, String fileName) {
        this.id = id;
        this.scientificName = scientificName;
        this.commonName = commonName;
        this.remarks = remarks;
        this.locations = locations;
        this.fileName = fileName;

        Log.d(UploadedImageFile.class.getSimpleName(), fileName);
        this.fileName = fileName.substring(fileName.lastIndexOf("/")+1);
        Log.d(UploadedImageFile.class.getSimpleName(), this.fileName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try{
                this.fileData = Files.readAllBytes(Paths.get(fileName));
            }catch (IOException ex){
                Log.d(UploadedImageFile.class.getSimpleName(), "IOException : " + ex.getMessage());
            }
        }else
            Log.d(UploadedImageFile.class.getSimpleName(), "Phone Version Unsupported");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
