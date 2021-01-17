package com.zystems.plantdex.models;

import java.util.Date;

public class PlantLocation extends AuditableEntity {

    private double latitude, longitude;
    private String locationName;

    public PlantLocation() {
    }

    public PlantLocation(double latitude, double longitude, String locationName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
    }

    public PlantLocation(String createdBy, Date createdOn, String lastModifiedBy, Date lastModifiedOn, String previousValue, double latitude, double longitude, String locationName) {
        super(createdBy, createdOn, lastModifiedBy, lastModifiedOn, previousValue);
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
