package com.zystems.plantdex.models;

import com.zystems.plantdex.models.Plant;

import java.util.List;

public class PlantsManagementResponse {
    public String message;
    public boolean isSuccessful;
    public List<String> errors;
    public List<Plant> plants;

    public PlantsManagementResponse() {
    }

    public PlantsManagementResponse(String message, boolean isSuccessful, List<String> errors, List<Plant> plants) {
        this.message = message;
        this.isSuccessful = isSuccessful;
        this.errors = errors;
        this.plants = plants;
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

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }
}
