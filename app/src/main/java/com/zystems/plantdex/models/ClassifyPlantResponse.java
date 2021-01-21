package com.zystems.plantdex.models;

import java.io.Serializable;
import java.util.List;

public class ClassifyPlantResponse {
    private String message;
    private boolean isSuccessful;
    private List<String> errors;
    private List<PlantClassificationResult> plantClassificationResults;

    public ClassifyPlantResponse() {
    }

    public ClassifyPlantResponse(String message, boolean isSuccessful, List<String> errors, List<PlantClassificationResult> plantClassificationResults) {
        this.message = message;
        this.isSuccessful = isSuccessful;
        this.errors = errors;
        this.plantClassificationResults = plantClassificationResults;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<PlantClassificationResult> getPlantClassificationResults() {
        return plantClassificationResults;
    }

    public void setPlantClassificationResults(List<PlantClassificationResult> plantClassificationResults) {
        this.plantClassificationResults = plantClassificationResults;
    }
}
