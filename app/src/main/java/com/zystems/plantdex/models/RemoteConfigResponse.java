package com.zystems.plantdex.models;

public class RemoteConfigResponse {
    private String message;
    private boolean isSuccessful;
    private String version;

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
