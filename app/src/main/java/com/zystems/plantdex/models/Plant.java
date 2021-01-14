package com.zystems.plantdex.models;

public class Plant extends AuditableEntity{
    public int Id;
    public String CommonName;
    public String ScientificName;
    public String ShortDescription;
    public String Description;
    public String Attributes;
    public String Locations;
    public String _Id;


    public Plant() {
    }

    public Plant(int id, String commonName, String scientificName, String shortDescription, String description, String attributes, String locations, String _Id) {
        Id = id;
        CommonName = commonName;
        ScientificName = scientificName;
        ShortDescription = shortDescription;
        Description = description;
        Attributes = attributes;
        Locations = locations;
        this._Id = _Id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCommonName() {
        return CommonName;
    }

    public void setCommonName(String commonName) {
        CommonName = commonName;
    }

    public String getScientificName() {
        return ScientificName;
    }

    public void setScientificName(String scientificName) {
        ScientificName = scientificName;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAttributes() {
        return Attributes;
    }

    public void setAttributes(String attributes) {
        Attributes = attributes;
    }

    public String getLocations() {
        return Locations;
    }

    public void setLocations(String locations) {
        Locations = locations;
    }

    public String get_Id() {
        return _Id;
    }

    public void set_Id(String _Id) {
        this._Id = _Id;
    }
}
