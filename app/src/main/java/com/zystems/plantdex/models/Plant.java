package com.zystems.plantdex.models;

import java.util.Date;

public class Plant extends AuditableEntity{
    private int id;
    private String commonName;
    private String scientificName;
    private String shortDescription;
    private String description;
    private String attributes;
    private String locations;
    private String _Id;
    private String plantImage;

    public Plant() {
    }

    public Plant(int id, String commonName, String scientificName, String shortDescription, String description, String attributes, String locations, String _Id, String plantImage) {
        this.id = id;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.shortDescription = shortDescription;
        this.description = description;
        this.attributes = attributes;
        this.locations = locations;
        this._Id = _Id;
        this.plantImage = plantImage;
    }

    public Plant(String createdBy, Date createdOn, String lastModifiedBy, Date lastModifiedOn, String previousValue, int id, String commonName, String scientificName, String shortDescription, String description, String attributes, String locations, String _Id, String plantImage) {
        super(createdBy, createdOn, lastModifiedBy, lastModifiedOn, previousValue);
        this.id = id;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.shortDescription = shortDescription;
        this.description = description;
        this.attributes = attributes;
        this.locations = locations;
        this._Id = _Id;
        this.plantImage = plantImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String get_Id() {
        return _Id;
    }

    public void set_Id(String _Id) {
        this._Id = _Id;
    }

    public String getPlantImage() {
        return plantImage;
    }

    public void setPlantImage(String plantImage) {
        this.plantImage = plantImage;
    }
}
