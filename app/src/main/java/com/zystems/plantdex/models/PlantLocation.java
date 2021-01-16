package com.zystems.plantdex.models;

public class PlantLocation extends AuditableEntity {

    public String Latitude;
    public String Longitude;
    public String LocationName;

    public PlantLocation() {
    }

    public PlantLocation(String latitude, String longitude, String locationName) {
        Latitude = latitude;
        Longitude = longitude;
        LocationName = locationName;
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

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }
}
