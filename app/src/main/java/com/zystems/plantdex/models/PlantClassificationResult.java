package com.zystems.plantdex.models;

import java.io.Serializable;

public class PlantClassificationResult  {
    private Plant plant;
    private double percentConfidence;

    public PlantClassificationResult(Plant plant, double percentConfidence) {
        this.plant = plant;
        this.percentConfidence = percentConfidence;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public double getPercentConfidence() {
        return percentConfidence;
    }

    public void setPercentConfidence(double percentConfidence) {
        this.percentConfidence = percentConfidence;
    }
}
