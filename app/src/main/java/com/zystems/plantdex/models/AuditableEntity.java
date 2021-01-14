package com.zystems.plantdex.models;

import java.util.Date;

public class AuditableEntity {
    public String CreatedBy;
    public Date CreatedOn;
    public String LastModifiedBy;
    public Date LastModifiedOn;
    public String PreviousValue;

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
    }

    public String getLastModifiedBy() {
        return LastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        LastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedOn() {
        return LastModifiedOn;
    }

    public void setLastModifiedOn(Date lastModifiedOn) {
        LastModifiedOn = lastModifiedOn;
    }

    public String getPreviousValue() {
        return PreviousValue;
    }

    public void setPreviousValue(String previousValue) {
        PreviousValue = previousValue;
    }
}
