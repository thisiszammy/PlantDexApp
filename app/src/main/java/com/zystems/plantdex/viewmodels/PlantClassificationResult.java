package com.zystems.plantdex.viewmodels;

public class PlantClassificationResult {
    private int id;
    private String scientificName;
    private String commonName;
    private String percentConfidence;

    public PlantClassificationResult(int id, String scientificName, String commonName, String percentConfidence) {
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

    public String getPercentConfidence() {
        return percentConfidence;
    }

    public void setPercentConfidence(String percentConfidence) {
        this.percentConfidence = percentConfidence;
    }
}
