package com.zystems.plantdex.models;

public class PlantLocation extends AuditableEntity {

    public double Latitude, Longitude;
    public String LocationName;

    public PlantLocation() {
    }


    public PlantLocation(double latitude, double longitude, String locationName) {
        Latitude = latitude;
        Longitude = longitude;
        LocationName = locationName;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }
}
