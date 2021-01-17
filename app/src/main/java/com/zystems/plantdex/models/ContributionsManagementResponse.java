package com.zystems.plantdex.models;

public class ContributionsManagementResponse {
    private String message;
    private boolean isSuccessful;

    public ContributionsManagementResponse() {
    }

    public ContributionsManagementResponse(String message, boolean isSuccessful) {
        this.message = message;
        this.isSuccessful = isSuccessful;
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
}
