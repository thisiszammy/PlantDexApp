package com.zystems.plantdex.models;

public class PlantLocation extends AuditableEntity {

    public String Latitude;
    public String Longitude;
    public double Intensity;

    public PlantLocation() {
    }

    public PlantLocation(String latitude, String longitude, double intensity) {
        Latitude = latitude;
        Longitude = longitude;
        Intensity = intensity;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public double getIntensity() {
        return Intensity;
    }

    public void setIntensity(double intensity) {
        Intensity = intensity;
    }
}
