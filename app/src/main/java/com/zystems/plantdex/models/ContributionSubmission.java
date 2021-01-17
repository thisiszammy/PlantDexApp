package com.zystems.plantdex.models;

import java.util.List;

public class ContributionSubmission {
    private int id;
    private String scientificName;
    private String commonName;
    private String remarks;
    private String locations;

    public ContributionSubmission() {
    }

    public ContributionSubmission(int id, String scientificName, String commonName, String remarks, String locations) {
        this.id = id;
        this.scientificName = scientificName;
        this.commonName = commonName;
        this.remarks = remarks;
        this.locations = locations;
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
}
