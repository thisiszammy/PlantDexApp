package com.zystems.plantdex.viewmodels;

public class RemoteConfigResponse {
    public String message;
    public boolean isSuccessful;
    public String version;

    public RemoteConfigResponse() {
    }

    public RemoteConfigResponse(String message, boolean isSuccessful, String version) {
        this.message = message;
        this.isSuccessful = isSuccessful;
        this.version = version;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
