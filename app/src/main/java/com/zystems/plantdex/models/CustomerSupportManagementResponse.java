package com.zystems.plantdex.models;

public class CustomerSupportManagementResponse {
    private String message;
    private boolean isSuccessful;

    public CustomerSupportManagementResponse() {
    }

    public CustomerSupportManagementResponse(String message, boolean isSuccessful) {
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
