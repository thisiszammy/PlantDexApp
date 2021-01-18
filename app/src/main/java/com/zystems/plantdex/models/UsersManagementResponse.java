package com.zystems.plantdex.models;

import java.util.List;

public class UsersManagementResponse {
    private String message;
    private boolean isSuccessful;
    private String _Id;
    private List<String> errors;

    public UsersManagementResponse() {
    }

    public UsersManagementResponse(String message, boolean isSuccessful, String _Id, List<String> errors) {
        this.message = message;
        this.isSuccessful = isSuccessful;
        this._Id = _Id;
        this.errors = errors;
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

    public String get_Id() {
        return _Id;
    }

    public void set_Id(String _Id) {
        this._Id = _Id;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
