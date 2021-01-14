package com.zystems.plantdex.models;

public class PlantAttribute extends AuditableEntity{
    public int Id;
    public String AttributeName;
    public String AttributeValue;

    public PlantAttribute() {
    }

    public PlantAttribute(int id, String attributeName, String attributeValue) {
        Id = id;
        AttributeName = attributeName;
        AttributeValue = attributeValue;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAttributeName() {
        return AttributeName;
    }

    public void setAttributeName(String attributeName) {
        AttributeName = attributeName;
    }

    public String getAttributeValue() {
        return AttributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        AttributeValue = attributeValue;
    }
}
