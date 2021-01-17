package com.zystems.plantdex.models;

import java.util.Date;

public class PlantAttribute extends AuditableEntity{
    private int id;
    private String attributeName;
    private String attributeValue;

    public PlantAttribute() {
    }

    public PlantAttribute(int id, String attributeName, String attributeValue) {
        this.id = id;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public PlantAttribute(String createdBy, Date createdOn, String lastModifiedBy, Date lastModifiedOn, String previousValue, int id, String attributeName, String attributeValue) {
        super(createdBy, createdOn, lastModifiedBy, lastModifiedOn, previousValue);
        this.id = id;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}
