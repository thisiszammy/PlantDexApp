package com.zystems.plantdex.viewmodels;

public class PlantClassificationResult {
    private int id;
    private String scientificName;
    private String commonName;
    private double percentConfidence;

    public PlantClassificationResult(int id, String scientificName, String commonName, double percentConfidence) {
        this.id = id;
        this.scientificName = scientificName;
        this.commonName = commonName;
        this.percentConfidence = percentConfidence;
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

    public double getPercentConfidence() {
        return percentConfidence;
    }

    public void setPercentConfidence(double percentConfidence) {
        this.percentConfidence = percentConfidence;
    }
}
